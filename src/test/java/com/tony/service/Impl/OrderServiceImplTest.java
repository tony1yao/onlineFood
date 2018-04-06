package com.tony.service.Impl;

import com.tony.dataobject.OrderDetail;
import com.tony.dto.OrderDTO;
import com.tony.enums.OrderStatusEnum;
import com.tony.enums.PayStatusEnum;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    private final String BUYER_OPENID = "1101110";
    private final String ORDER_ID = "1522887685034642470";

    @Autowired
    private OrderServiceImpl orderService;
    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("SouthBank");
        orderDTO.setBuyerName("Tony Yao");
        orderDTO.setBuyerPhone("15039485848");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();


        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("1234");
        orderDetail.setProductQuantity(5);

        OrderDetail orderDetail1 = new OrderDetail();
        orderDetail1.setProductId("1235");
        orderDetail1.setProductQuantity(10);

        orderDetailList.add(orderDetail);
        orderDetailList.add(orderDetail1);

        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);

        log.info("result of creating order is {}", result);
        TestCase.assertNotNull(result);

    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne(ORDER_ID);
        log.info("result of query for order with id 1522887685034642470 :{}",result);
        TestCase.assertSame(ORDER_ID, result.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,pageRequest);
        TestCase.assertNotSame(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        TestCase.assertSame(OrderStatusEnum.CANCELLED.getCode(),result.getOrderStatus());

    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        TestCase.assertSame(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        TestCase.assertSame(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }
}