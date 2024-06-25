package com.mn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mn.entity.EC2Instance;

public interface EC2InstanceRepository extends JpaRepository<EC2Instance, String> {
	
}
