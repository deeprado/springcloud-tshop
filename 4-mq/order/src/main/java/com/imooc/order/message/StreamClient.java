package com.imooc.order.message;

import org.springframework.messaging.SubscribableChannel;

public interface StreamClient {


//    @Input("myMessage")
    SubscribableChannel input();

}
