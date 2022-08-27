package com.inditex.backdevtest.handler;

import com.inditex.backdevtest.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class MockErrorHandler implements ResponseErrorHandler {

    /**
     * Check if there is an error response from the Mock API
     * @param httpResponse API HTTP response
     * @return true in case of a status code != 200, false in other case
     * @throws IOException Exception getting status code from response
     */
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode() != HttpStatus.OK;
    }

    /**
     * Method to handle the response if there is an error
     * @param httpResponse API HTTP response
     * @throws IOException Exception getting status code from response
     */
    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        throw new NotFoundException(String.format("Client error code: %s, message: %s",
                httpResponse.getStatusCode().value(), httpResponse.getStatusText()));
    }
}
