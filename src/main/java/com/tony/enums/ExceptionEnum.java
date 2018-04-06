package com.tony.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    PARAM_ERROR(1,"The input pararmeter is incorrect"),
    PRODUCT_NOT_EXIST(10,"This product does not exist!"),
    PRODUCT_STOCK_ERROR(11,"Error to the stock of the product"),
    ORDER_NOT_EXIST(12,"This order does not exist!"),
    ORDER_DETAIL_NOT_EXIST(13,"The order detail does not exist."),
    ORDER_STATUS_ERROR(14,"The status of the order is incorrect and cannot be cancelled."),
    ORDER_UPDATE_FAIL(15,"Failed to update order status."),
    ORDER_DETAIL_EMPTY(16,"There is not order detail info found."),
    ORDER_PAY_STATUS_ERROR(17,"Wrong pay status."),
    CART_EMPTY(18, "The shopping cart is empty."),
    ORDER_OWNER_ERROR(19,"This order does not belong to the current user.")
    ;
    private int code;

    private String msg;

    ExceptionEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
