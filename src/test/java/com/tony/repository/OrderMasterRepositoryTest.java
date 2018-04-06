package com.tony.repository;

import com.tony.dataobject.OrderMaster;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private  OrderMasterRepository repository;

    private final String OPENID = "110111";
    @Test
    public void findByBuyerOpenid() {
        PageRequest pageRequest = new PageRequest(0,3);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, pageRequest);
        System.out.println(result.getTotalElements());
    }

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerOpenid("110111");
        orderMaster.setOrderId("123457");
        orderMaster.setBuyerName("May");
        orderMaster.setBuyerPhone("18639595583");
        orderMaster.setBuyerAddress("Hangzhou");
        orderMaster.setOrderAmount(new BigDecimal(16.6));
        OrderMaster result = repository.save(orderMaster);
        TestCase.assertNotNull(result);
    }
}