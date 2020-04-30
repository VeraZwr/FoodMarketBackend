package soen487.foodmarket.error;

/**
 * @author: Jingchao Zhang
 * @createdate: 2019/05/04
 **/

public enum EmBusinessError implements CommonError {

    // Generic error, starts with 10001
    PARAMETER_VALIDATION_ERROR(10001, "Parameter error"),
    UNKNOWN_ERROR(10002, "Unknown error"),

    // 20000, User information related error
    USER_NOT_EXIST(20001,"User does not exist."),
    USER_LOGIN_FAIL(20002,"Username or password is incorrect. Please try again."),
    USER_NOT_LOGIN(20003,"User is not login"),
    USER_NAME_EXISTS(20004, "username already exists."),
    PASSWORD_INCORRECT(20005, "Password incorrect."),
    USER_INFO_INCORRECT(20006, "You cannot change username or password here."),
    USER_ID_NULL(20007, "user id must not be null"),

    // 30000, product error
    PRODUCT_EXISTS(30001, "Product already exists."),
    PRODUCT_NOT_EXIST(30002, "Product does not exist."),
    PRODUCT_STOCK_NEGATIVE(30003, "Stock cannot be negative"),
    PRODUCT_STATUS_ERROR(30004, "product status error"),

    // 40000, category error
    CATEGORY_NOT_EXIST(40001, "Category does not exist."),

    // 50000, order error
    ORDER_NOT_EXIST(50001, "Order does not exist."),
    ORDER_STATUS_ERROR(50002, "Order status error."),
    PAY_STATUS_ERROR(50003, "Pay status error."),
    ;


    private int errCode;
    private String errMsg;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
