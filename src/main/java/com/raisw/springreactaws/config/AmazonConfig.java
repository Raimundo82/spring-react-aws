package com.raisw.springreactaws.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() {
        AWSCredentialsProvider awsCredentials = new ProfileCredentialsProvider();
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentials)
                .withRegion("eu-west-3")
                .build();
    }
}



