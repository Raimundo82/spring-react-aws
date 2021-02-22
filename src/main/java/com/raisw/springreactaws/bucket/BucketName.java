package com.raisw.springreactaws.bucket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {

    PROFILE_IMAGE("raisw-image-upload-123");

    private final String bucketName;

}
