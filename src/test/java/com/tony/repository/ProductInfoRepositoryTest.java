package com.tony.repository;

import com.tony.dataobject.ProductInfo;
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
public class ProductInfoRepositoryTest {

    @Autowired
    ProductInfoRepository repository;
    @Test
    public void findByProductStatus() {
        List<ProductInfo> result = repository.findByProductStatus(1);
        TestCase.assertNotSame(0,result.size());
    }

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1234");
        productInfo.setProductName("Beef Pizza");
        productInfo.setProductPrice(new BigDecimal(3.5));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("This is a very good pizza");
        productInfo.setProductStatus(1);
        productInfo.setCategoryType(3);
        productInfo.setProductIcon("xxxxx.jpg");

        ProductInfo result = repository.save(productInfo);
        TestCase.assertNotNull(result);
    }
}