package com.tony.service;

import com.tony.dataobject.OrderMaster;
import com.tony.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    /* Create an order*/
    OrderDTO create(OrderDTO orderDTO);

    /* Query an order*/
    OrderDTO findOne(String orderId);

    /* Query a list of order*/
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /* Cancel an order*/
    OrderDTO cancel(OrderDTO orderDTO);

    /* Finish an order */
    OrderDTO finish(OrderDTO orderDTO);

    /* Pay an order */
    OrderDTO paid(OrderDTO orderDTO);

    /* Query a list of order of all users*/
    Page<OrderDTO> findList(Pageable pageable);
}
