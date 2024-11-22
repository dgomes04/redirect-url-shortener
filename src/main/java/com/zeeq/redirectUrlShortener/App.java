package com.zeeq.redirectUrlShortener;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeeq.redirectUrlShortener.services.RedirectUrlShortenerService;
import com.zeeq.redirectUrlShortener.services.impl.RedirectUrlShortenerServiceImpl;
import com.zeeq.redirectUrlShortener.services.impl.S3StorageServiceImpl;

import java.util.Map;

public class App implements RequestHandler<Map<String, Object>, Map<String, Object>> {
    private final RedirectUrlShortenerService urlRedirectService = new RedirectUrlShortenerServiceImpl(
            new S3StorageServiceImpl(new ObjectMapper())
    );

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        return urlRedirectService.handleCreateRequest(input);
    }
}
