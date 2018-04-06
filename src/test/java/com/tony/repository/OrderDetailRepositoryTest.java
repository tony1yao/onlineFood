package com.tony.repository;

import com.tony.dataobject.OrderDetail;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1111");
        orderDetail.setOrderId("123456");
        orderDetail.setProductIcon("xxx.jpg");
        orderDetail.setProductId("123123");
        orderDetail.setProductName("Pizza");
        orderDetail.setProductPrice(new BigDecimal(54));
        orderDetail.setProductQuantity(2);

        OrderDetail result = repository.save(orderDetail);

        TestCase.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {

        List<OrderDetail> orderDetailList = repository.findByOrderId("123456");
        TestCase.assertNotSame(0,orderDetailList.size());
    }
}