package com.imooc.order.repository;

import com.imooc.order.data.OrderDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    void testSave() {

        OrderDetail od = new OrderDetail();
        od.setDetailId("123456");
        od.setOrderId("123456");
        od.setProductIcon("xxxx");
        od.setProductId("1");
        od.setProductName("xxxx");
        Date d = new Date();
        od.setCreateTime(d);
        od.setUpdateTime(d);
        od.setProductPrice(new BigDecimal(3.3));
        od.setProductQuantity(2);

        OrderDetail ods = orderDetailRepository.save(od);
        assertTrue(ods.getDetailId() != null);

    }
}