package soen487.foodmarket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import soen487.foodmarket.models.CommonReturnType;
import soen487.foodmarket.models.ProductModel;
import soen487.foodmarket.service.ProductService;
import soen487.foodmarket.utils.ParamValidator;
import soen487.foodmarket.viewobjects.ProductCategoryVo;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dish")
@Slf4j
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public CommonReturnType findById(@RequestParam String productId) {
        ProductModel productModel = productService.findById(productId);
        return CommonReturnType.create(productModel);
    }

    @GetMapping("/all")
    public CommonReturnType listAllForSale() { // 分类列出所有在售商品
        List<ProductCategoryVo> productCategoryVoList = productService.listAllForSale();
        return CommonReturnType.create(productCategoryVoList);
    }

    @PostMapping
    public CommonReturnType createProduct(@Valid @RequestBody ProductModel productModel,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ParamValidator.paramError(bindingResult);
        }
        ProductModel product = productService.createProduct(productModel);
        return CommonReturnType.create(product);
    }

    @PutMapping
    public CommonReturnType updateProduct(@Valid @RequestBody ProductModel productModel,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ParamValidator.paramError(bindingResult);
        }
        ProductModel updateProduct = productService.updateProduct(productModel);
        return CommonReturnType.create(updateProduct);
    }

    @GetMapping("/listBySeller")
    public CommonReturnType listByOwner(@RequestParam Integer ownerId) {
        // TODO 不安全，任何人都可以猜id拿到list
        List<ProductModel> productModelList = productService.findAllByOwner(ownerId);
        return CommonReturnType.create(productModelList);
    }

    @PutMapping("/soldOut")
    public CommonReturnType soldOut(@RequestParam String productId) {
        ProductModel productModel = productService.soldOut(productId);
        return CommonReturnType.create(productModel);
    }

    @PutMapping("/forSale")
    public CommonReturnType forSale(@RequestParam String productId) {
        ProductModel productModel = productService.forSale(productId);
        return CommonReturnType.create(productModel);
    }

}
