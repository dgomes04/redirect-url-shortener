package com.zeeq.redirectUrlShortener.services;

import com.zeeq.redirectUrlShortener.models.UrlData;

public interface S3StorageService {
    UrlData getUrlData(String shortUrlCode);
}
