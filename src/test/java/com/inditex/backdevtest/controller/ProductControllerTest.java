package com.inditex.backdevtest.controller;

import com.inditex.backdevtest.model.ProductDetailDto;
import com.inditex.backdevtest.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductSimilarOK() {
        doReturn(Collections.singletonList(new ProductDetailDto())).when(productService).getSimilarProducts("3");

        ResponseEntity<List<ProductDetailDto>> response = productController.getProductSimilar("3");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Collections.singletonList(new ProductDetailDto()), response.getBody());
        verify(productService, times(1)).getSimilarProducts("3");
        verifyNoMoreInteractions(productService);
    }

    @Test
    void getProductSimilarNotFound() {
        doReturn(null).when(productService).getSimilarProducts("3");

        ResponseEntity<List<ProductDetailDto>> response = productController.getProductSimilar("3");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(productService, times(1)).getSimilarProducts("3");
        verifyNoMoreInteractions(productService);
    }
}
