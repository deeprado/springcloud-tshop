package com.imooc.product.repository;

import com.imooc.product.data.ProductInfo;
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
class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    void findByProductStatus() {
        List<ProductInfo> list = productInfoRepository.findByProductStatus(0);
        System.out.println(list);
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    void findByProductIdIn() {
        List<String> productIdList = new ArrayList<>();
        productIdList.add("157875196366160022");
        productIdList.add("157875227953464068");

        List<ProductInfo> list = productInfoRepository.findByProductIdIn(productIdList);
        System.out.println(list);
        Assert.assertTrue(list.size() > 0);
    }
}