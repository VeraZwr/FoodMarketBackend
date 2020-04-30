package soen487.foodmarket.viewobjects;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemVO {

    private String itemId;

    private String orderId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private String productDescription;

    private String productImage;

    private Integer quantity;

    private String unit;

}
