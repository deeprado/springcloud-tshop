package com.imooc.product.service;

import com.imooc.product.data.ProductCategory;

import java.util.List;

/**
 * 商品类别
 */
public interface CategoryService {

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
