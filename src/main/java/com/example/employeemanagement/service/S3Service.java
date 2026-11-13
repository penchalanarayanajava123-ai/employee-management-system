
package com.example.employeemanagement.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(final MultipartFile multipartFile) {

        try {
            String fileName = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                    .format(new Date())
                    + "_" + UUID.randomUUID()
                    + "_" + multipartFile.getOriginalFilename();

            String key = fileName;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(multipartFile.getContentType())
                    .contentLength(multipartFile.getSize())
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(
                            multipartFile.getInputStream(),
                            multipartFile.getSize()
                    )
            );

            // Return S3 file URL only
            return s3Client.utilities()
                    .getUrl(builder -> builder
                            .bucket(bucketName)
                            .key(key))
                    .toString();

        } catch (Exception ex) {
            throw new RuntimeException("File upload failed", ex);
        }
    }
}
