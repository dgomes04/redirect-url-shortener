package com.zeeq.redirectUrlShortener.models;

import lombok.Builder;

@Builder
public record UrlData(String originalUrl, Long expirationTime) {
}
