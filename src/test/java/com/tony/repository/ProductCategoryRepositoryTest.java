package com.tony.repository;

import com.tony.dataobject.ProductCategory;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        log.info(repository.findById(1).get().toString());
    }

    @Test
    @Transactional
    public void saveTest(){
        ProductCategory category = new ProductCategory("Korean food", 3);
        ProductCategory result = repository.save(category);
        TestCase.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList( 1, 2,3,4);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        log.info(result.toString());
        TestCase.assertNotSame(0, result.size());
    }
}