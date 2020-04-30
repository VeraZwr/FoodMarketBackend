package soen487.foodmarket.service;

import soen487.foodmarket.models.Cart;
import soen487.foodmarket.models.ProductModel;
import soen487.foodmarket.viewobjects.ProductCategoryVo;

import java.util.List;

public interface ProductService {

    List<ProductCategoryVo> listAllForSale();

    ProductModel createProduct(ProductModel productModel);

    ProductModel updateProduct(ProductModel productModel);

    List<ProductModel> findAllByOwner(Integer ownerId);

    ProductModel soldOut(String productId);

    ProductModel forSale(String productId);

    void increaseStock(List<Cart> cartList);

    void decreaseStock(List<Cart> cartList);

    ProductModel findById(String productId);
}
