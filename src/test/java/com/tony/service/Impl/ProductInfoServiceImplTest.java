package com.tony.service.Impl;

import com.tony.dataobject.ProductInfo;
import com.tony.enums.ProductStatusEnum;
import com.tony.service.ProductInfoService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
    @Autowired
    ProductInfoService productInfoService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("1234");
        TestCase.assertSame("1234",productInfo.getProductId());
    }

    @Test
    public void findAllInStock() {
        List<ProductInfo> result = productInfoService.findAllInStock();
        TestCase.assertNotNull(result);
    }

    @Test
    public void findAll() {
        PageRequest pageRequest =  new PageRequest(0,2);
        Page<ProductInfo> result = productInfoService.findAll(pageRequest);
        TestCase.assertNotNull(result);

    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1235");
        productInfo.setProductName("Chicken Pizza");
        productInfo.setProductPrice(new BigDecimal(5));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("This is also a very good pizza");
        productInfo.setProductStatus(ProductStatusEnum.IN_STOCK.getCode());
        productInfo.setCategoryType(3);
        productInfo.setProductIcon("yyyyy.jpg");
        ProductInfo result = productInfoService.save(productInfo);
        TestCase.assertNotNull(result);
    }
}