package com.imooc.order.client;

import com.imooc.order.data.ProductInfo;
import com.imooc.order.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "product", fallback = ProductClientFallback.class)
public interface ProductClient {

    /**
     * 测试Feign
     * @return
     */
    @GetMapping("/server/msg")
    String productMsg();

    /**
     * 测试Feign 超时
     * @return
     */
    @GetMapping("/hystrix/timeout")
    String getTimeout();

    /**
     * 获取订单商品
     * @param productIdList
     * @return
     */
    @PostMapping("/product/listForOrder")
    List<ProductInfo> listForOrder(List<String> productIdList);


    /**
     * 减库存
     * @param cartDTOList
     */
    @PostMapping("/product/decreaseStock")
    void decreaseStock(List<CartDTO> cartDTOList);
}
