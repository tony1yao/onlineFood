package com.tony.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {

    @NotEmpty(message = "name is mandatory")
    private  String name;

    @NotEmpty(message = "phone number is mandatory")
    private  String phone;

    @NotEmpty(message = "address is mandatory")
    private  String address;

    @NotEmpty(message = "openid is mandatory")
    private  String openid;

    @NotEmpty(message = "items is mandatory, it represents the items in the shopping cart.")
    private  String items;

}
