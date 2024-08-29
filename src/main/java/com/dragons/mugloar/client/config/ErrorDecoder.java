package com.dragons.mugloar.client.config;

import feign.Response;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class ErrorDecoder implements feign.codec.ErrorDecoder {

    public static final String RETRY_ERROR = "No ad by this ID exists";
    private static final Logger logger = LoggerFactory.getLogger(ErrorDecoder.class);
    private final feign.codec.ErrorDecoder defaultErrorDecoder = new feign.codec.ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody;
        if (response.body() != null) {
            try {
                responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
                if (response.status() == 400 && responseBody.contains(RETRY_ERROR)) {
                    return new RetryableException(
                            response.status(),
                            "400 Bad Request - Retrying",
                            response.request().httpMethod(),
                            new Date(),
                            response.request()
                    );
                }
            } catch (IOException e) {
                logger.warn("Failed to read response body", e);
            }
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
