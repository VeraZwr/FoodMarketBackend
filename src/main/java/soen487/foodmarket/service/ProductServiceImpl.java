package soen487.foodmarket.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soen487.foodmarket.dataobject.Category;
import soen487.foodmarket.dataobject.ProductInfo;
import soen487.foodmarket.enums.ProductStatus;
import soen487.foodmarket.error.BusinessException;
import soen487.foodmarket.error.EmBusinessError;
import soen487.foodmarket.models.Cart;
import soen487.foodmarket.models.ProductModel;
import soen487.foodmarket.repository.CategoryRepository;
import soen487.foodmarket.repository.ProductRepository;
import soen487.foodmarket.repository.UserRepository;
import soen487.foodmarket.utils.KeyUtil;
import soen487.foodmarket.viewobjects.ProductCategoryVo;
import soen487.foodmarket.viewobjects.ProductVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              UserRepository userRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ProductCategoryVo> listAllForSale() {
        List<ProductInfo> productList = productRepository.findByProductStatus(ProductStatus.FOR_SALE.getCode());
        List<Category> categories = categoryRepository.findAll();
        Category[] categoryArray = convertCategoryList2Array(categories);
        Map<Integer, ProductCategoryVo> map = new HashMap<>();
        for (ProductInfo productInfo : productList) {
            Integer categoryId = productInfo.getCategory();
            ProductCategoryVo categoryVo = map.getOrDefault(categoryId, new ProductCategoryVo());
            List<ProductVO> productVOList = categoryVo.getProductVOList();
            if (productVOList == null) {
                Category category = categoryArray[categoryId];
                categoryVo.setTypeName(category.getTypeName());
                categoryVo.setTypeNumber(category.getTypeNumber());
                productVOList = new ArrayList<>();
            }
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(productInfo, productVO);
            productVOList.add(productVO);
            categoryVo.setProductVOList(productVOList);
            map.put(categoryId, categoryVo);
        }
        return new ArrayList<>(map.values());
    }

    private Category[] convertCategoryList2Array(List<Category> categories) {
        int maxId = 0;
        for (Category category : categories) {
            maxId = Math.max(category.getId(), maxId);
        }
        Category[] categoryArray = new Category[maxId + 1];
        for (Category category : categories) {
            categoryArray[category.getId()] = category;
        }
        return categoryArray;
    }

    @Override
    @Transactional
    public ProductModel createProduct(ProductModel productModel) {
        String productId = KeyUtil.genUniqueKey();
        ProductInfo byId = productRepository.findById(productId).orElse(null);
        if (byId != null) {
            log.error("[Create Product] Product with this id already exists. product={}", byId);
            throw new BusinessException(EmBusinessError.PRODUCT_EXISTS);
        }
        productModel.setProductId(productId);
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productModel, productInfo);
        ProductInfo save = productRepository.save(productInfo);
        productModel.setProductId(save.getProductId());
        return productModel;
    }

    @Override
    @Transactional
    public ProductModel updateProduct(ProductModel productModel) {
        ProductInfo byId = productRepository.findById(productModel.getProductId()).orElse(null);
        if (byId == null) {
            log.error("[Update Product] Product does not exist. productId={}", productModel.getProductId());
            throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
        }
        if (productModel.getProductStock() < 0) {
            log.error("[Update Product] Product stock cannot be negative. stock={}", productModel.getProductStock());
            throw new BusinessException(EmBusinessError.PRODUCT_STOCK_NEGATIVE);
        }
        Integer status = productModel.getProductStatus();
        if (status != 0 && status != 1) {
            log.error("[Update Product] status error. status={}", productModel.getProductStatus());
            throw new BusinessException(EmBusinessError.PRODUCT_STATUS_ERROR);
        }
        if (!categoryRepository.existsById(productModel.getCategory())) {
            log.error("[Update Product] Category does not exist. categoryId={}", productModel.getCategory());
            throw new BusinessException(EmBusinessError.CATEGORY_NOT_EXIST);
        }
        if (!userRepository.existsById(productModel.getProductOwnerId())) {
            log.error("[Update Product] User does not exist. userIde={}", productModel.getProductOwnerId());
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(productModel, byId);
        productRepository.save(byId);
        return productModel;
    }

    @Override
    public List<ProductModel> findAllByOwner(Integer ownerId) {
        List<ProductInfo> productList = productRepository.findAllByProductOwnerId(ownerId);
        List<ProductModel> productModelList = new ArrayList<>();
        for (ProductInfo productInfo : productList) {
            ProductModel productModel = new ProductModel();
            BeanUtils.copyProperties(productInfo, productModel);
            productModelList.add(productModel);
        }
        return productModelList;
    }

    @Override
    @Transactional
    public ProductModel soldOut(String productId) {
        // TODO 不安全，所有人都可以改，应该加用户身份验证
        ProductInfo productInfo = productRepository.findById(productId).orElse(null);
        if (productInfo == null) {
            log.error("[Product Sold Out] Product does not exist. productId={}", productId);
            throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
        }
        if (!productInfo.getProductStatus().equals(ProductStatus.FOR_SALE.getCode())) {
            log.error("[Product Sold Out] Product status error. status={}", productInfo.getProductStatus());
            throw new BusinessException(EmBusinessError.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatus.SOLD_OUT.getCode());
        ProductInfo save = productRepository.save(productInfo);
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(save, productModel);
        return productModel;
    }

    @Override
    @Transactional
    public ProductModel forSale(String productId) {
        // TODO 不安全，所有人都可以改，应该加用户身份验证
        ProductInfo productInfo = productRepository.findById(productId).orElse(null);
        if (productInfo == null) {
            log.error("[Product Sold Out] Product does not exist. productId={}", productId);
            throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
        }
        if (!productInfo.getProductStatus().equals(ProductStatus.SOLD_OUT.getCode())) {
            log.error("[Product Sold Out] Product status error. status={}", productInfo.getProductStatus());
            throw new BusinessException(EmBusinessError.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(ProductStatus.FOR_SALE.getCode());
        ProductInfo save = productRepository.save(productInfo);
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(save, productModel);
        return productModel;
    }

    @Override
    @Transactional
    public void increaseStock(List<Cart> cartList) {
        for (Cart cart : cartList) {
            ProductInfo productInfo = productRepository.findById(cart.getProductId()).orElse(null);
            if (productInfo == null) {
                log.error("[Increase Stock] Product does not exist. productId={}", cart.getProductId());
                throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() + cart.getProductQuantity();
            productInfo.setProductStock(result);
            productRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<Cart> cartList) {
        for (Cart cart : cartList) {
            ProductInfo productInfo = productRepository.findById(cart.getProductId()).orElse(null);
            if (productInfo == null) {
                log.error("[Decrease Stock] Product does not exist. productId={}", cart.getProductId());
                throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - cart.getProductQuantity();
            if (result < 0) {
                log.error("[Decrease Stock] No enough Product. stock={}", productInfo.getProductStock());
                throw new BusinessException(EmBusinessError.PRODUCT_STOCK_NEGATIVE);
            }
            productInfo.setProductStock(result);
            productRepository.save(productInfo);
        }
    }

    @Override
    public ProductModel findById(String productId) {
        ProductInfo productInfo = productRepository.findById(productId).orElse(null);
        if (productInfo == null) {
            log.error("[Find Product] Product does not exist. productId={}", productId);
            throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
        }
        ProductModel productModel = new ProductModel();
        BeanUtils.copyProperties(productInfo, productModel);
        return productModel;
    }
}
