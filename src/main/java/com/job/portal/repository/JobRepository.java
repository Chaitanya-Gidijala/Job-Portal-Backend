package com.job.portal.repository;

import com.job.portal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    long count();
    List<Job> findByJobTypeOrderByCreatedDateDescCreatedTimeDesc(String jobType);

    @Query("SELECT j FROM Job j ORDER BY j.createdDate DESC, j.createdTime DESC")
    List<Job> findAllByOrderByCreatedDateAndCreatedTimeDesc();

    Optional<Job> findByJobTitle(String jobTitle);}


