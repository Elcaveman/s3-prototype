package com.proto.s3.manager.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "s3")
@Getter
@Setter
public class S3BucketConfig {
    Map<String, Map<String,String>> buckets;
}
