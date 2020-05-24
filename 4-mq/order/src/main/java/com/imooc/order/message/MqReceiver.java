package com.imooc.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 */
@Slf4j
@Component
public class MqReceiver {

    // 1.  @RabbitListener 注册消费者，queues 不会自动创建队列
//    @RabbitListener(queues = "myQueue")
//    public void process(String msg) {
//        log.info("receive msg = {}", msg);
//    }

    // 第二种   queuesToDeclare 会自动创建队列
//    @RabbitListener(queuesToDeclare = @Queue("myQueueAuto"))
//    public void process(String msg) {
//        log.info("receive msg = {}", msg);
//    }

    // 第三种 exchange 和  queue 绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueueBinding"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String msg) {
        log.info("receive msg = {}", msg);
    }

    /**
     * 第三种  业务示例
     * 数码供应商
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key="computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String msg) {
        log.info("computer receiver msg = {}", msg);
    }

    /**
     *  第三种  业务示例
     *  水果供应商
     * @param msg
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key="fruit",
            value = @Queue("fruitOrder")
    ))
    public void processFruid(String msg) {
        log.info("fruit receive msg2 = {}", msg);
    }

}
