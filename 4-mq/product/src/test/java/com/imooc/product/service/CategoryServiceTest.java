package com.imooc.product.service;

import com.imooc.product.data.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    void findByCategoryTypeIn() {
        List<Integer> categoryTypeList = new ArrayList<>();
        categoryTypeList.add(1);
        categoryTypeList.add(2);
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(categoryTypeList);
        Assert.assertTrue(list.size() > 0);
    }
}