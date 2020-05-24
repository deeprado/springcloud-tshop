package com.imooc.order;


import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


@SpringBootTest
class MqSenderTest extends OrderApplicationTests {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    void sendDefault() {
        amqpTemplate.convertAndSend("myQueue", "just tet" + new Date());
    }

    @Test
    void sendAuto() {
        amqpTemplate.convertAndSend("myQueue1", "just tet" + new Date());
    }

    @Test
    void sendBind() {
        amqpTemplate.convertAndSend("myQueueBinding", "just tet" + new Date());
    }

    @Test
    void sendCompute() {
        amqpTemplate.convertAndSend("computerOrder", "computerOrder " + new Date());
    }

    @Test
    void sendFruit() {
        amqpTemplate.convertAndSend("fruitOrder", "fruitOrder " + new Date());
    }
}
