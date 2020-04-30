package soen487.foodmarket.viewobjects;

import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryVo {

    private String typeName;

    private Integer typeNumber;

    private List<ProductVO>  productVOList;
}
