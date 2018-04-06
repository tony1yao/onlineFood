package com.tony.service;

import com.tony.dto.OrderDTO;
import org.hibernate.criterion.Order;

public interface BuyerService {

    //Query one order.
    OrderDTO findOrderOne(String openid, String orderId);
    //Cancel the order.
    OrderDTO cancelOrder(String openid, String orderId);
}
