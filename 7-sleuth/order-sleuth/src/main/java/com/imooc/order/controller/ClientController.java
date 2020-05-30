package com.imooc.order.controller;


import com.imooc.order.client.ProductClient;
import com.imooc.order.data.ProductInfo;
import com.imooc.order.dto.CartDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/client")
@Slf4j
public class ClientController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg")
    public String getProductMsg() {
        RestTemplate restTemplate  = new RestTemplate();
        // 第一种 直接写死url
//        return restTemplate.getForObject("http://localhost:8081/server/msg", String.class);

        // 第二种 通过 loadBalancerClient 获取服务地址 ，在使用 restTemplate
        ServiceInstance serviceInstance =  loadBalancerClient.choose("PRODUCT");

        String url =  String.format("http://%s:%s/server/msg",  serviceInstance.getHost(), serviceInstance.getPort());
        log.info("url = {}", url);

        String response =  restTemplate.getForObject(url, String.class);

        // 第三种 利用 @LoadBalanced 可在resttemplate 使用服务名
//        String response =  restTemplate.getForObject("http://PRODUCT/server/msg", String.class);

        // 第四种
//        String response = productClient.productMsg();

        log.info("response = {}", response);
        return response;
    }

    @GetMapping("/getProductList")
    public String getProductList() {
        List<String> productIdList = new ArrayList<>();
        productIdList.add("157875196366160022");
        List<ProductInfo> list = productClient.listForOrder(productIdList);
        log.info("response = {}", list);
        return "ok";
    }

    @GetMapping("/decreaseStock")
    public String decreaseStock() {
        List<CartDTO> cartDTOS = new ArrayList<>();
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductId("157875196366160022");
        cartDTO.setProductQuantity(2);

        CartDTO cartDTO1 = new CartDTO();
        cartDTO1.setProductId("157875227953464068");
        cartDTO1.setProductQuantity(2);

        cartDTOS.add(cartDTO);
        cartDTOS.add(cartDTO1);

        productClient.decreaseStock(cartDTOS);

        log.info("response = {}", cartDTOS);
        return "ok";
    }
}
