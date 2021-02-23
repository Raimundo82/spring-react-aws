package com.raisw.springreactaws.filestore;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import com.raisw.springreactaws.exceptions.AppException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import static com.raisw.springreactaws.exceptions.ErrorMessage.FAILED_TO_DOWNLOAD_IMAGE;
import static com.raisw.springreactaws.exceptions.ErrorMessage.FAILED_TO_STORE_FILE_S3;

@Service
@AllArgsConstructor
public class FileStore {

    private final AmazonS3 s3;

    public void save(String path,
                     String filename,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty())
                map.forEach(metadata::addUserMetadata);
        });

        try {

            s3.putObject(
                    path,
                    filename,
                    inputStream,
                    metadata
            );

        } catch (AmazonServiceException e) {
            throw new AppException(FAILED_TO_STORE_FILE_S3, e.getErrorMessage());
        }

    }

    public byte[] download(String path, String key) {
        try {
            return IOUtils.toByteArray(s3.getObject(path, key).getObjectContent());
        } catch (AmazonServiceException | IOException e) {
            throw new AppException(FAILED_TO_DOWNLOAD_IMAGE,e.getLocalizedMessage());
        }
    }
}
