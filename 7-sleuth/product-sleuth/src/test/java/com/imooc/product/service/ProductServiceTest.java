package com.imooc.product.service;

import com.imooc.product.data.ProductInfo;
import com.imooc.product.dto.CartDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> list = productService.findUpAll();
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void findList() throws Exception {
        List<ProductInfo> list = productService.findByProductIdIn(Arrays.asList("157875196366160022", "157875227953464068"));
        Assert.assertTrue(list.size() > 0);
    }

    @Test
    public void decreaseStock() throws Exception {
        List<CartDTO> cartDTOS = new ArrayList<>();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductId("157875196366160022");
        cartDTO.setProductQuantity(1);
        cartDTOS.add(cartDTO);

        productService.decreaseStock(cartDTOS);

    }

}