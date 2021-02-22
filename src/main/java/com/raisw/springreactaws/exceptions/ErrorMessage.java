package com.raisw.springreactaws.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorMessage {

    FAILED_TO_STORE_FILE_S3("Failed to store file to s3 : %s"),
    IMAGE_FILE_IS_EMPTY("Image file %s is empty"),
    FILE_IS_NOT_IMAGE("File %s is not an image"),
    USER_PROFILE_NOT_FOUND("UserProfile with id %d not found"),
    FAILED_TO_DOWNLOAD_IMAGE("Failed to download image : %s"),
    USERNAME_ALREADY_EXISTS("Username %s is already registered"),
    USER_NOT_REGISTERED("User with username %s not registered. Try again"),
    USERNAME_INVALID("Username [%s] is invalid");


    public final String label;

}
