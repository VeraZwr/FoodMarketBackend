package soen487.foodmarket.models;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductModel {

    private String productId;

    @NotNull(message = "Product name cannot be empty")
    private String productName;

    @NotNull(message = "Product price cannot be empty")
    private BigDecimal productPrice;

    @NotNull(message = "Product stock cannot be empty")
    private Integer productStock;

    private String productDescription;

    private String productImage;

    @NotNull(message = "Product status cannot be empty")
    private Integer productStatus;

    @NotNull(message = "Product category cannot be empty")
    private Integer category;

    @NotNull(message = "Product owner cannot be empty")
    private Integer productOwnerId;
}
