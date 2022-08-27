package com.inditex.backdevtest.client;

import com.inditex.backdevtest.model.ProductDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MocksClientTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    MocksClient mocksClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(mocksClient, "baseURL", "http://localhost:3001/product");
        ReflectionTestUtils.setField(mocksClient, "similarIdsPath", "similarids");
    }

    @Test
    void testSimilarIdsOK() {
        String[] testResult = {"2","3"};
        doReturn(testResult).when(restTemplate).
                getForObject(UriEncoder.encode("http://localhost:3001/product/1/similarids"), String[].class);

        List<String> similarIds = mocksClient.getSimilarIds("1");
        assertEquals(2, similarIds.size());
        assertEquals(Arrays.asList(testResult), similarIds);

        verify(restTemplate, times(1)).
                getForObject(UriEncoder.encode("http://localhost:3001/product/1/similarids"), String[].class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testSimilarIdsError() {
        doThrow(new RestClientException("Error 404")).when(restTemplate).
                getForObject(UriEncoder.encode("http://localhost:3001/product/1/similarids"), String[].class);

        List<String> similarIds = mocksClient.getSimilarIds("1");
        assertEquals(0, similarIds.size());
        verify(restTemplate, times(1)).
                getForObject(UriEncoder.encode("http://localhost:3001/product/1/similarids"), String[].class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testGetProductById() {
        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setId("1");
        productDetailDto.setAvailability(false);
        productDetailDto.setPrice(BigDecimal.ONE);
        productDetailDto.setName("product");

        doReturn(productDetailDto).when(restTemplate).
                getForObject(UriEncoder.encode("http://localhost:3001/product/1"), ProductDetailDto.class);


        ProductDetailDto responseProduct = mocksClient.getProductById("1");

        assertEquals(responseProduct, productDetailDto);
        verify(restTemplate, times(1)).
                getForObject(UriEncoder.encode("http://localhost:3001/product/1"), ProductDetailDto.class);
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    void testGetProductByIdError() {
        doThrow(new RestClientException("Error 500")).when(restTemplate).
                getForObject(UriEncoder.encode("http://localhost:3001/product/1"), ProductDetailDto.class);

        ProductDetailDto responseProduct = mocksClient.getProductById("1");

        assertNull(responseProduct);
        verify(restTemplate, times(1)).
                getForObject(UriEncoder.encode("http://localhost:3001/product/1"), ProductDetailDto.class);
        verifyNoMoreInteractions(restTemplate);
    }
}
