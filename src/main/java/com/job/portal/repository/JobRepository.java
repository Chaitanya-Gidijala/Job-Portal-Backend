package com.job.portal.repository;

import com.job.portal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    long count();
    List<Job> findByJobType(String jobType);
}

