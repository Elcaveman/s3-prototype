package com.proto.s3.manager;

import com.proto.s3.manager.infrastructure.config.S3BucketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class S3ManagerApplication {
	@Autowired
	S3BucketConfig bucketConfig;

	public static void main(String[] args) {

		var context = SpringApplication.run(S3ManagerApplication.class, args);
	}

}
