package com.imooc.order.controller;

import com.imooc.order.client.ProductClient;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 *
 */
@RestController
@DefaultProperties(defaultFallback = "defaultFallback")
@Slf4j
public class HystrixController {

    private final static String PRODUCT_LIST_API = "http://127.0.0.1:8081/product/listForOrder";
    private final static String PRODUCT_TIMEOUT_API = "http://127.0.0.1:8081/hystrix/timeout";
    private final static String PRODUCT_BREAKER_API = "http://127.0.0.1:8081/hystrix/breaker";

    @Autowired
    private ProductClient productClient;

    //超时配置
//	@HystrixCommand(commandProperties = {
//			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//	})

    @HystrixCommand
//    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(@RequestParam("number") Integer number) {
        if (number % 2 == 0) {
            return "success";
        }

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(PRODUCT_LIST_API,
                Arrays.asList("157875196366160022"),
                String.class);

//		throw new RuntimeException("发送异常了");
    }

    /**
     * 超时时间
     *
     * @return
     */
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "10000")
    })
    @GetMapping("/getTimeout")
    public String getTimeout() {
        log.info("start timeout");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(PRODUCT_TIMEOUT_API,
                String.class);
    }

    @HystrixCommand(fallbackMethod = "defaultFallback", commandKey = "userGetKey")
    @GetMapping("/getCommandKey")
    public String getCommandKey() {
        log.info("start getCommandKey");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(PRODUCT_TIMEOUT_API,
                String.class);
    }

    @GetMapping("/getFeignTimeout")
    public String getFeignTimeout() {
        log.info("start feign timeout");
        return productClient.getTimeout();
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),                //设置熔断
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),    //请求数达到后才计算
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"), //休眠时间窗
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),    //错误率
    })
    @GetMapping("/getCircuitBreaker")
    public String getCircuitBreaker(@RequestParam("number") Integer number) {
        log.info("start circuitBreaker");
        if (number % 2 == 0) {
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(PRODUCT_BREAKER_API,
                String.class);
    }

    // fallbackMethod 应和注解方法接收相同参数
    private String fallback(Integer number) {
        return "太拥挤了, 请稍后再试~~";
    }


    //  @DefaultProperties(defaultFallback 则不需要参数
    private String defaultFallback() {
        return "默认提示：太拥挤了, 请稍后再试~~";
    }
}
