package com.mn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mn.entity.S3Instance;

public interface S3InstanceRepository extends JpaRepository<S3Instance, Long> {

}
