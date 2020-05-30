package com.imooc.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hystrix")
@Slf4j
public class HystrixController {

    @GetMapping("/timeout")
    public String testTimeout() {
        try {
            log.info("start timeout");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "timeout done";
    }

    @GetMapping("/breaker")
    public String testCircuitBreaker() {
        return "breaker done";
    }
}
