package de.mywald.dyndns.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import de.mywald.dyndns.S3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
class S3RepositoryImpl implements S3Repository {

    private static final int PART_SIZE = 5 * 1024 * 1024;

    private final String bucketName;

    private final AmazonS3 s3Client;

    @Autowired
    public S3RepositoryImpl(@Value("${dyndns.s3.access-key}") String accessKey,
                            @Value("${dyndns.s3.secret-key}") String secretKey,
                            @Value("${dyndns.s3.bucket-name}") String bucketName,
                            @Value("${dyndns.s3.region}") String region) {
        this.bucketName = bucketName;

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

    }

    @Override
    public void upload(String keyName, String content) {
        String id = init(keyName);

        try {
            List<PartETag> results = upload(keyName, content, id);
            complete(keyName, id, results);
        } catch (Exception ex) {
            abort(keyName, id);
        }
    }

    private String init(String keyName) {
        return s3Client.initiateMultipartUpload(new InitiateMultipartUploadRequest(bucketName, keyName))
                .getUploadId();
    }

    private List<PartETag> upload(String keyName, String content, String uploadId) throws Exception {
        byte[] file = content.getBytes();
        int contentLength = file.length;

        List<PartETag> results = new ArrayList<>();
        int filePosition = 0;
        for (int i = 1; filePosition < contentLength; i++) {
            results.add(uploadSinglePart(keyName, uploadId, file, contentLength, filePosition, i));
            filePosition += PART_SIZE;
        }
        return results;
    }

    private PartETag uploadSinglePart(String keyName, String uploadId, byte[] file, int contentLength, int filePosition, int partNumber) {
        int partSize = Math.min(PART_SIZE, (contentLength - filePosition));
        ByteArrayInputStream partInputStream = new ByteArrayInputStream(file, filePosition, partSize);

        UploadPartRequest uploadRequest = new UploadPartRequest()
                .withBucketName(bucketName).withKey(keyName)
                .withUploadId(uploadId).withPartNumber(partNumber)
                .withInputStream(partInputStream)
                .withPartSize(partSize);

        return s3Client.uploadPart(uploadRequest).getPartETag();
    }

    private void complete(String keyName, String uploadId, List<PartETag> results) {
        s3Client.completeMultipartUpload(new CompleteMultipartUploadRequest(bucketName, keyName, uploadId, results));
    }

    private void abort(String keyName, String uploadId) {
        s3Client.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, keyName, uploadId));
    }


}
