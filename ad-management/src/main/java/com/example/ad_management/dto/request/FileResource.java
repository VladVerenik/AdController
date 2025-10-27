package com.example.ad_management.dto.request;

import org.springframework.core.io.Resource;

public record FileResource(Resource resource, String contentType) {}
