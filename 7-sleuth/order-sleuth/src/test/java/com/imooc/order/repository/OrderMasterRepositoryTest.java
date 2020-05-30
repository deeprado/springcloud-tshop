package com.imooc.order.repository;

import com.imooc.order.data.OrderMaster;
import com.imooc.order.enums.OrderStatusEnum;
import com.imooc.order.enums.PayStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void testSave() {
        OrderMaster o = new OrderMaster();
        o.setOrderId("123456");
        o.setBuyerAddress("哈尔滨");
        o.setBuyerName("deeprado");
        o.setBuyerOpenid("aaaa");
        o.setBuyerPhone("13684511236");
        Date d = new Date();
        o.setCreateTime(d);
        o.setUpdateTime(d);
        o.setOrderAmount(new BigDecimal(2.5));
        o.setOrderStatus(OrderStatusEnum.NEW.getCode());
        o.setPayStatus(PayStatusEnum.WAIT.getCode());
        OrderMaster os = orderMasterRepository.save(o);
        assertTrue(os.getOrderId() != null);
    }

}