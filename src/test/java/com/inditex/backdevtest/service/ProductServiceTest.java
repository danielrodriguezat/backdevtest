package com.inditex.backdevtest.service;

import com.inditex.backdevtest.client.MocksClient;
import com.inditex.backdevtest.model.ProductDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    MocksClient mocksClient;

    @InjectMocks
    ProductServiceImpl productsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSimilarProductsOK() {
        doReturn(Arrays.asList("3","4")).when(mocksClient).getSimilarIds("2");
        doReturn(getProductDetail("3")).when(mocksClient).getProductById("3");
        doReturn(getProductDetail("4")).when(mocksClient).getProductById("4");

        List<ProductDetailDto> relatedProducts = productsService.getSimilarProducts("2");

        assertEquals(2, relatedProducts.size());
        verify(mocksClient, times(1)).getSimilarIds("2");
        verify(mocksClient, times(1)).getProductById("3");
        verify(mocksClient, times(1)).getProductById("4");
        verifyNoMoreInteractions(mocksClient);
    }

    @Test
    void testGetSimilarProductsEmptySimilar() {
        doReturn(Collections.emptyList()).when(mocksClient).getSimilarIds("2");
        List<ProductDetailDto> relatedProducts = productsService.getSimilarProducts("2");
        assertNull(relatedProducts);
        verify(mocksClient, times(1)).getSimilarIds("2");
        verifyNoMoreInteractions(mocksClient);
    }

    @Test
    void testGetSimilarProductsFailDetailsAPI() {
        doReturn(Arrays.asList("3","4")).when(mocksClient).getSimilarIds("2");
        doReturn(null).when(mocksClient).getProductById("3");
        doReturn(null).when(mocksClient).getProductById("4");

        List<ProductDetailDto> relatedProducts = productsService.getSimilarProducts("2");

        assertNull(relatedProducts);
        verify(mocksClient, times(1)).getSimilarIds("2");
        verify(mocksClient, times(1)).getProductById("3");
        verify(mocksClient, times(1)).getProductById("4");
        verifyNoMoreInteractions(mocksClient);
    }

    @Test
    void testGetSimilarProducstFailOneProduct() {
        doReturn(Arrays.asList("3","4")).when(mocksClient).getSimilarIds("2");
        doReturn(null).when(mocksClient).getProductById("3");
        doReturn(getProductDetail("4")).when(mocksClient).getProductById("4");

        List<ProductDetailDto> relatedProducts = productsService.getSimilarProducts("2");

        assertEquals(1, relatedProducts.size());
        assertEquals("4", relatedProducts.get(0).getId());
        verify(mocksClient, times(1)).getSimilarIds("2");
        verify(mocksClient, times(1)).getProductById("3");
        verify(mocksClient, times(1)).getProductById("4");
        verifyNoMoreInteractions(mocksClient);
    }

    private ProductDetailDto getProductDetail(String id) {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId(id);
        productDetailDto.setPrice(BigDecimal.ZERO);
        productDetailDto.setAvailability(true);
        productDetailDto.setName("Product");
        return productDetailDto;
    }
}
