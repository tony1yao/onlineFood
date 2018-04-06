package com.tony.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tony.dataobject.OrderDetail;
import com.tony.dto.OrderDTO;
import com.tony.enums.ExceptionEnum;
import com.tony.exception.SellException;
import com.tony.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerAddress(orderForm.getAddress());

        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();

        try{
            orderDetailList= gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("[Error when coverting data], cannot convert items to Json. string = {}", orderForm.getItems());
            throw new SellException(ExceptionEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
