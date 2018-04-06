package com.tony.dto;

import lombok.Data;

/*
* Shopping cart.
* */
@Data
public class CartDTO {


    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId,Integer productQuantity){
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
