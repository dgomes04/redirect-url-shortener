package com.zeeq.redirectUrlShortener.services;

import java.util.Map;

public interface RedirectUrlShortenerService {
    Map<String, Object> handleCreateRequest(Map<String, Object> input);
}
