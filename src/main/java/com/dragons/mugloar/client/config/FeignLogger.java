package com.dragons.mugloar.client.config;

import feign.Logger;
import feign.Request;
import feign.Response;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FeignLogger extends Logger {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(FeignLogger.class);

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        if (logLevel.ordinal() >= Level.FULL.ordinal()) {
            logger.debug("Request: {} {}", request.httpMethod(), request.url());
        }
        if (request.body() != null && logLevel.ordinal() >= Level.FULL.ordinal()) {
            logger.debug("Body: {}", new String(request.body(), request.charset()));
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String bodyData = null;

        if (response.body() != null) {
            bodyData = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
            logger.debug("Response: {} {} - Body: {}", response.status(), response.reason(), bodyData);
        } else {
            logger.debug("Response: {} {}", response.status(), response.reason());
        }

        return response.toBuilder().body(bodyData.getBytes(StandardCharsets.UTF_8)).build();
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        logger.debug(String.format(methodTag(configKey) + format, args));
    }
}
