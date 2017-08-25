package de.mywald.dyndns;

public interface S3Repository {
    void upload(String keyName, String content);
}
