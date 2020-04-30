package soen487.foodmarket.enums;

public enum OrderStatus {
    NEW(0, "新订单"),
    FINISH(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;

    private String massage;

    OrderStatus(Integer code, String massage) {
        this.code = code;
        this.massage = massage;
    }

    public Integer getCode() {
        return code;
    }

    public String getMassage() {
        return massage;
    }
}
