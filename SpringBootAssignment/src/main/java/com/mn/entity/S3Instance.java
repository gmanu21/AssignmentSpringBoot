package com.mn.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class S3Instance {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String bucketName;

	public S3Instance() {
	}


	public S3Instance(String bucketName) {
		this.bucketName = bucketName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	@Override
	public String toString() {
		return "S3Instance [id=" + id + ", bucketName=" + bucketName + "]";
	}
}
