package com.tony.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/*
* Detailed info of all products.
* */

@Data
public class ProductInfoVO {

    @JsonProperty("id")
    private  String productID;

    @JsonProperty("name")
    private  String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private  String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
