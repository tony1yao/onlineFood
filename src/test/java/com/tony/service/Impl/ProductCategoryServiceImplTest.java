package com.tony.service.Impl;

import com.tony.dataobject.ProductCategory;
import junit.framework.TestCase;
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
public class ProductCategoryServiceImplTest {
    @Autowired
    ProductCategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        TestCase.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        TestCase.assertNotSame(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1,2,3,4));
        TestCase.assertNotSame(0,productCategoryList.size());
    }

    @Test
    @Transactional
    public void save() {
        ProductCategory productCategory = new ProductCategory("American food", 7);
        ProductCategory result = categoryService.save(productCategory);
        TestCase.assertEquals(result.getCategoryType(), new Integer(7));
    }
}