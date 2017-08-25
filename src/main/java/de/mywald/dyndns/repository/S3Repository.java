package de.mywald.dyndns.repository;

public interface S3Repository {
    void upload(String keyName, String content);
}
