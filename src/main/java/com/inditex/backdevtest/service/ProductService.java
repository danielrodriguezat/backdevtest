package com.inditex.backdevtest.service;

import com.inditex.backdevtest.model.ProductDetailDto;

import java.util.List;

public interface ProductService {

    /**
     * Obtain a list of detailed similar products of a specific product by ID
     * @param productId Product identifier
     * @return List of related products
     */
    List<ProductDetailDto> getSimilarProducts(String productId);
}
