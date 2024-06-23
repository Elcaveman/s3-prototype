package com.proto.s3.manager.infrastructure;

import com.proto.s3.manager.infrastructure.models.S3Bucket;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.utils.AttributeMap;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;

import java.net.URI;

import static software.amazon.awssdk.http.SdkHttpConfigurationOption.TCP_KEEPALIVE;

@Slf4j
public class S3ClientManager implements AutoCloseable{
    private final S3Bucket bucket;
    private final S3Client s3Client;

    public S3ClientManager(S3Bucket bucket, boolean enableStreaming){
        this.bucket = bucket;
        AwsBasicCredentials credentials = AwsBasicCredentials.create(bucket.getAccessKey(), bucket.getSecretKey());
        AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);
        SdkHttpClient s3HttpClient = (enableStreaming)? buildTCPKeepAliveHttpClient(): buildDefaultHttpClient();
        s3Client = S3Client.builder()
                .credentialsProvider(credentialsProvider)
                .httpClient(s3HttpClient)
                .endpointOverride(URI.create(bucket.getEndpoint()))
                .region(bucket.getRegion())
                .build();
        log.info("Connected to Bucket > {}; Streaming mode: {}",bucket.getName(), (enableStreaming)?"ON":"OFF" );
    }

    private SdkHttpClient buildTCPKeepAliveHttpClient() {
        // This Http client doesn't drop the connection if we stop consuming packages from it ( useful with streams )
        AttributeMap.Builder optionBuilder = AttributeMap.builder().put(TCP_KEEPALIVE, true );
        try( AttributeMap options = optionBuilder.build()){
            return UrlConnectionHttpClient.builder().buildWithDefaults(options);
        }
    }
    private SdkHttpClient buildDefaultHttpClient(){
        return UrlConnectionHttpClient.builder().build();
    }

    //TODO: simple => multipart upload

    //TODO: simple => stream upload

    //TODO: simple => stream download

    //TODO: simple => chunk download

    //TODO: simple => multi-threaded stream download

    //TODO: pre-signed => create & consume PUT/GET urls

    //TODO: pre-signed => create multipart PUT/GET urls

    //TODO: pre-signed => consume multipart PUT/GET urls
    @Override
    public void close() throws Exception {
        s3Client.close();
    }
}
