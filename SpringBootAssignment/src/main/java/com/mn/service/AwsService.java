package com.mn.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.mn.entity.EC2Instance;
import com.mn.entity.FileMetadata;
import com.mn.entity.Job;
import com.mn.entity.S3Instance;
import com.mn.repository.EC2InstanceRepository;
import com.mn.repository.FileMetadataRepository;
import com.mn.repository.JobRepository;
import com.mn.repository.S3InstanceRepository;

import jakarta.transaction.Transactional;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class AwsService {
	@Autowired
	private S3Client s3Client;
	@Autowired
	private Ec2Client ec2Client;
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private S3InstanceRepository s3InstanceRepository;
	@Autowired
	EC2InstanceRepository ec2InstanceRepository;
	@Autowired
	FileMetadataRepository fileMetadataRepository;

	@Async
	@Transactional
	public void discoverEC2Instances(Long jobId) {

		DescribeInstancesRequest request = DescribeInstancesRequest.builder().build();

		List<EC2Instance> instances = ec2Client.describeInstances(request).reservations().stream()
				.flatMap(reservation -> reservation.instances().stream())
				.map(instance -> new EC2Instance(instance.instanceId())).collect(Collectors.toList());

		ec2InstanceRepository.saveAll(instances);
		Job job = jobRepository.findById(jobId).get();
		job.setStatus("SUCCESS");
		jobRepository.save(job);
	}

	@Async
	@Transactional
	public void discoverS3Buckets(Long jobId) {

		ListBucketsResponse response = s3Client.listBuckets();

		List<S3Instance> s3Object = response.buckets().stream().map(bucket -> new S3Instance(bucket.name()))
				.collect(Collectors.toList());

		s3InstanceRepository.saveAll(s3Object);
		Job job = jobRepository.findById(jobId).orElseThrow();
		job.setStatus("SUCCESS");
		jobRepository.save(job);

	}

	public Long S3BucketFileNames(String bucketName) {

		ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).build();

		ListObjectsV2Response response = s3Client.listObjectsV2(request);
		List<S3Object> contents = response.contents();

		for (S3Object object : contents) {
			FileMetadata metadata = new FileMetadata();
			metadata.setBucketName(bucketName);
			metadata.setFileName(object.key());
			fileMetadataRepository.save(metadata);
		}
		Job job = new Job();
		job.setStatus("SUCCESS");
		jobRepository.save(job);
		return job.getId();
	}

	public Long S3BucketObjectCount(String bucketName) {

		List<FileMetadata> findByBucketName = fileMetadataRepository.findByBucketName(bucketName);

		return findByBucketName.stream().filter(metadata -> metadata.getBucketName().equals(bucketName)).count();
	}

	public List<String> getFileCountByPattern(String bucketName, String pattern) {
		List<FileMetadata> fileMetadataList = fileMetadataRepository.findByBucketName(bucketName);

		return fileMetadataList.stream()
	                .filter(metadata -> metadata.getFileName().contains(pattern))
	                .map(FileMetadata::getFileName) // Extract file name
	                .collect(Collectors.toList());
	}

}
