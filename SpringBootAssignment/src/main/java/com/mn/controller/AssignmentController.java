package com.mn.controller;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mn.entity.EC2Instance;
import com.mn.entity.Job;
import com.mn.entity.S3Instance;
import com.mn.repository.EC2InstanceRepository;
import com.mn.repository.JobRepository;
import com.mn.repository.S3InstanceRepository;
import com.mn.service.AwsService;

@RestController
@RequestMapping("/api")
public class AssignmentController {
    @Autowired
    private AwsService awsService;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private S3InstanceRepository s3InstanceRepository;
    @Autowired
    private EC2InstanceRepository ec2InstanceRepository;

    @PostMapping("/discoverServices")
    public Long discoverServices(@RequestBody List<String> services) {
        Job job = new Job();
        job.setStatus("IN_PROGRESS");
        jobRepository.save(job);

        if (services.contains("EC2")) {
        	 CompletableFuture.runAsync(()->awsService.discoverEC2Instances(job.getId()));
        }
        if (services.contains("S3")) {
        	CompletableFuture.runAsync(()->awsService.discoverS3Buckets(job.getId()));
        }
        return job.getId();
    }

    @GetMapping("/getJobResult/{jobId}")
    public String getJobResult(@PathVariable Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow();
        return job.getStatus();
    }

    @GetMapping("/getDiscoveryResult/{service}")
    public List<String> getDiscoveryResult(@PathVariable String service) {
        if ("S3".equalsIgnoreCase(service)) {
            return s3InstanceRepository.findAll().stream().map(S3Instance::getBucketName).collect(Collectors.toList());
        } else if ("EC2".equalsIgnoreCase(service)) {
           return ec2InstanceRepository.findAll().stream().map(EC2Instance::getInstanceId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    } 

    @PostMapping("/getS3BucketObjects")
    public Long getS3BucketObjects(@RequestParam String bucketName) {
        return awsService.S3BucketFileNames(bucketName);
    }

    @GetMapping("/getS3BucketObjectCount/{bucketName}")
    public Long getS3BucketObjectCount(@PathVariable String bucketName) {
        return awsService.S3BucketObjectCount(bucketName);
    }

    @GetMapping("/getS3BucketObjectLike")
    public List<String> getS3BucketObjectLike(@RequestParam String bucketName, @RequestParam String pattern) {
        return awsService.getFileCountByPattern(bucketName, pattern);
    }
}

