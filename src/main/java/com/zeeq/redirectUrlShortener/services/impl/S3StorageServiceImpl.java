package com.zeeq.redirectUrlShortener.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeeq.redirectUrlShortener.models.UrlData;
import com.zeeq.redirectUrlShortener.services.S3StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.InputStream;
import java.util.UUID;

import static com.zeeq.redirectUrlShortener.AppConstants.S3.S3_BUCKET;

public class S3StorageServiceImpl implements S3StorageService {
    private static final Logger log = LoggerFactory.getLogger(S3StorageServiceImpl.class);
    private final S3Client s3Client = S3Client.builder().build();
    private final ObjectMapper objectMapper;

    public S3StorageServiceImpl(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public UrlData getUrlData(String shortUrlCode) {
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(S3_BUCKET)
                .key(shortUrlCode + ".json")
                .build();

        try (InputStream s3ObjectStream = s3Client.getObject(request)) {
            return objectMapper.readValue(s3ObjectStream, UrlData.class);
        } catch (Exception e) {
            log.error("Error fetching or deserializing URL data: {}", e.getMessage());
            throw new RuntimeException("Error fetching or deserializing URL data: " + e.getMessage(), e);
        }
    }
}
