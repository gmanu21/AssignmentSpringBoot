package com.mn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mn.entity.FileMetadata;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, Long> {

	  List<FileMetadata> findByBucketName(String bucketName);
}
