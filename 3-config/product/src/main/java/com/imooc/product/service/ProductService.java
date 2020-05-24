package com.imooc.product.service;


import com.imooc.product.data.ProductInfo;
import com.imooc.product.dto.CartDTO;

import java.util.List;

public interface ProductService {
    /**
     * 查询所有在架商品
     */
    List<ProductInfo> findUpAll();

    List<ProductInfo> findByProductIdIn(List<String> productIdList);

    /**
     * 扣库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
