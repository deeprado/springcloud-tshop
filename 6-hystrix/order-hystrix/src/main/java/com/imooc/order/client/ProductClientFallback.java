package com.imooc.order.client;

import com.imooc.order.data.ProductInfo;
import com.imooc.order.dto.CartDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductClientFallback implements ProductClient {

    @Override
    public List<ProductInfo> listForOrder(List<String> productIdList) {
        return null;
    }

    @Override
    public String productMsg() {
        return "feign product msg fallback";
    }

    @Override
    public void decreaseStock(List<CartDTO> cartDTOList) {

    }

    @Override
    public String getTimeout() {
        return "feign timeout timeout fallback";
    }

    public String defaultFallback() {
        return "xxx";
    }
}
