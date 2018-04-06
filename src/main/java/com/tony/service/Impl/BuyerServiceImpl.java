package com.tony.service.Impl;

import com.tony.dto.OrderDTO;
import com.tony.enums.ExceptionEnum;
import com.tony.exception.SellException;
import com.tony.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null){
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[query order], openid does not match, openid={}, orderid={}", openid,orderId);
            throw new SellException(ExceptionEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = findOrderOne(openid,orderId);
        if(orderDTO == null){
            log.error("[query order], cannot find the order based on orderId, orderId={}", orderId);
            throw new SellException(ExceptionEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }
}
