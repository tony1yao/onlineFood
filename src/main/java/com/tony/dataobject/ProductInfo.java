package com.tony.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class ProductInfo {

    @Id
    private String productId;

    private String productName;

    private BigDecimal productPrice;

    //quantity in stock.
    private  Integer productStock;

    private  String productDescription;

    //url for the icon.
    private  String productIcon;

    //0 in stock, 1 out of stock.
    private  Integer productStatus;

    private  Integer categoryType;
}
