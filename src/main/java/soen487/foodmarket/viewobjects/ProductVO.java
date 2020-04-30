package soen487.foodmarket.viewobjects;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVO {

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer productStock;

    private String productDescription;

    private String productImage;
}
