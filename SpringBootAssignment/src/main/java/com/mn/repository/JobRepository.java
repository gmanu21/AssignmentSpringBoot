package com.mn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mn.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}



