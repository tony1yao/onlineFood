package com.tony.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tony.dataobject.OrderDetail;
import com.tony.enums.OrderStatusEnum;
import com.tony.enums.PayStatusEnum;
import com.tony.utils.serializer.Data2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/*Data Transfer Object for orders.*/

@Data
public class OrderDTO {

    private  String orderId;

    private  String  buyerName;

    private  String buyerPhone;

    private  String buyerAddress;

    private  String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    private Integer payStatus = PayStatusEnum.UNPAID.getCode();

    @JsonSerialize(using = Data2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Data2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;
}
