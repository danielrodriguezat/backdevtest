package com.inditex.backdevtest.service;

import com.inditex.backdevtest.client.MocksClient;
import com.inditex.backdevtest.model.ProductDetailDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final MocksClient mocksClient;

    @Override
    public List<ProductDetailDto> getSimilarProducts(String productId) {

        List<String> similarIds = mocksClient.getSimilarIds(productId);

        List<ProductDetailDto> productDetails = similarIds.stream()
                    .map(mocksClient::getProductById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(productDetails)) {
            productDetails = null;
        }

        return productDetails;
    }
}
