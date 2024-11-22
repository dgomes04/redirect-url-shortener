package com.zeeq.redirectUrlShortener.validators;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class S3RequestValidator {
    private static final Logger log = LoggerFactory.getLogger(S3RequestValidator.class);

    public static String validateShorUrlCode(String rawPath) {
        String shortUrlCode = rawPath.replace("/", "");
        if (shortUrlCode.isEmpty()) {
            log.info("Invalid input: 'shortUrlCode' is required.");
            throw new IllegalArgumentException("Invalid input: 'shortUrlCode' is required.");
        }
        return shortUrlCode;
    }
}
