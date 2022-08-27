package com.inditex.backdevtest.controller;

import com.inditex.backdevtest.api.ProductApi;
import com.inditex.backdevtest.model.ProductDetailDto;
import com.inditex.backdevtest.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class ProductController implements ProductApi {

    private final ProductService productService;

    /**
     * Controller for get mapping /product/{productId}/similar
     * @param productId Product id to search related products
     * @return List of details of related products
     */
    @Override
    public ResponseEntity<List<ProductDetailDto>> getProductSimilar(String productId) {
        return Optional.ofNullable(productService.getSimilarProducts(productId))
                .map(similarProducts -> ResponseEntity.ok().body(similarProducts))
                .orElseGet( () -> ResponseEntity.notFound().build());
    }
}
