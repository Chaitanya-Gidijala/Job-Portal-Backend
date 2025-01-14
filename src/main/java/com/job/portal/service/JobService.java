package com.job.portal.service;

import com.job.portal.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> getAllJobs();
    JobDTO getJobById(Long id);
    long getJobCount();
    JobDTO saveJob(JobDTO job);
    List<JobDTO> getJobsByType(String jobType);
    List<JobDTO> getLatestJobs();

    JobDTO getJobByTitle(String jobTitle);
}

