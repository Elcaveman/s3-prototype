package com.proto.s3.manager.infrastructure.models;

import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.regions.Region;

@Data
@Builder
public class S3Bucket {
    private String accessKey;
    private String secretKey;
    private String name;
    private Region region;
    private String endpoint;
}
