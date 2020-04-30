package soen487.foodmarket.models;

import lombok.Data;

@Data
public class Cart {

    private String productId;

    private Integer productQuantity;

    public Cart() {
    }

    public Cart(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
