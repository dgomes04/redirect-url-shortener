package com.zeeq.redirectUrlShortener.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeeq.redirectUrlShortener.models.UrlData;
import com.zeeq.redirectUrlShortener.services.RedirectUrlShortenerService;
import com.zeeq.redirectUrlShortener.services.S3StorageService;
import com.zeeq.redirectUrlShortener.validators.S3RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class RedirectUrlShortenerServiceImpl implements RedirectUrlShortenerService {
    private static final Logger log = LogManager.getLogger(RedirectUrlShortenerServiceImpl.class);
    private final S3StorageService s3StorageService;

    public RedirectUrlShortenerServiceImpl(S3StorageService s3StorageService) {
        this.s3StorageService = s3StorageService;
    }

    @Override
    public Map<String, Object> handleCreateRequest(Map<String, Object> input) {
        var rawPath = input.get("rawPath").toString();
        var shortUrlCode = S3RequestValidator.validateShorUrlCode(rawPath);

        var urlData = s3StorageService.getUrlData(shortUrlCode);

        var currentTimeInMillis = System.currentTimeMillis() / 1000;

        if(currentTimeInMillis > urlData.expirationTime())
            return createErrorResponse();
        return createSuccessResponse(urlData);
    }

    private Map<String, Object> createSuccessResponse(UrlData urlData){
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 302);
        response.put("headers", createSuccessResponseHeaders(urlData));
        return response;
    }
    private Map<String, String> createSuccessResponseHeaders(UrlData urlData){
        Map<String, String> headers = new HashMap<>();
        headers.put("Location", urlData.originalUrl());
        return headers;
    }
    private Map<String, Object> createErrorResponse(){
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 401);
        response.put("body", "This url has expired");
        return  response;
    }
}
