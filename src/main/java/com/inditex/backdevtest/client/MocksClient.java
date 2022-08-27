package com.inditex.backdevtest.client;

import com.inditex.backdevtest.model.ProductDetailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.yaml.snakeyaml.util.UriEncoder;

import java.util.Arrays;
import java.util.List;

@Service
public class MocksClient {

    private final RestTemplate restTemplate;

    private final String baseURL;

    private final String similarIdsPath;

    private static final Logger log = LoggerFactory.getLogger(MocksClient.class);

    /**
     * All arguments constructor
     * @param baseURL Mocks API base URL
     * @param similarIdsPath Similar IDS API path
     * @param restTemplate REST Template
     */
    public MocksClient (@Value("${rest.clients.mocks.baseURL}") String baseURL,
                        @Value("${rest.clients.mocks.similarIdsPath}") String similarIdsPath,
                        RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.baseURL = baseURL;
        this.similarIdsPath = similarIdsPath;
    }

    /**
     * Get a list of similar IDs given one product ID.
     * @param productId Product identifier
     * @return List of similar products IDs. In case of API error, return empty array
     */
    public List<String> getSimilarIds(String productId) {
        String[] similarIds = new String[0];
        try {
            similarIds = restTemplate.getForObject(
                    UriEncoder.encode(String.format("%s/%s/%s", baseURL, productId, similarIdsPath)), String[].class);
        } catch (RestClientException exception) {
            log.error("Error obtaining similar IDs for product with id: {}", productId);
        }
        return Arrays.asList(similarIds);
    }

    /**
     * Obtains product details by product id
     * @param productId Product identifier
     * @return Product details, null in case of error
     */
    public ProductDetailDto getProductById(String productId) {
        ProductDetailDto productDetailDto = null;
        try {
            productDetailDto = restTemplate.getForObject(
                    UriEncoder.encode(String.format("%s/%s", baseURL, productId)), ProductDetailDto.class);
        } catch (RestClientException exception) {
            log.error("Error obtaining product details for product with id: {}", productId);
        }
        return productDetailDto;
    }

}
