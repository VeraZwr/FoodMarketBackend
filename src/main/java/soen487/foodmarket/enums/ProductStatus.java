package soen487.foodmarket.enums;

public enum  ProductStatus {
    FOR_SALE(0, "在架"),
    SOLD_OUT(1, "下架"),
    ;

    private Integer code;

    private String message;

    ProductStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
