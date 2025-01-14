package com.job.portal.service;

import com.job.portal.dto.JobDTO;
import com.job.portal.entity.Job;
import com.job.portal.exception.JobNotFoundException;
import com.job.portal.repository.JobRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Cacheable(value = "jobs", key = "'all_jobs'")
    public List<JobDTO> getAllJobs() {
        log.debug("Fetching all jobs...");
        List<Job> jobs = jobRepository.findAll();
        log.info("Total jobs fetched: {}", jobs.size());
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "job", key = "#id")
    public JobDTO getJobById(Long id) {
        log.debug("Fetching job details for jobId: {}", id);
        // Fetching job entity by id
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException("Job not found for jobId: " + id));
        log.info("Job details fetched for jobId: {}", id);
        // Mapping Job entity to JobDTO without exposing the id
        return modelMapper.map(job, JobDTO.class);  // JobDTO will have all fields except 'id' if you don't include it
    }

    // Method to get a job by title
    public JobDTO getJobByTitle(String jobTitle) {
        Job job = jobRepository.findByJobTitle(jobTitle)
                .orElseThrow(() -> new JobNotFoundException("Job not found for title: " + jobTitle));
        return modelMapper.map(job, JobDTO.class);
    }

    @Override
    @Cacheable(value = "jobs", key = "#jobType")
    public List<JobDTO> getJobsByType(String jobType) {
        log.debug("Fetching jobs for jobType: {}", jobType);
        List<Job> jobs = jobRepository.findByJobTypeOrderByCreatedDateDescCreatedTimeDesc(jobType);
        if (!jobs.isEmpty()) {
            log.info("Jobs fetched for jobType: {}", jobType);
            return jobs.stream()
                    .map(job -> modelMapper.map(job, JobDTO.class))
                    .collect(Collectors.toList());
        }
        log.warn("No jobs found for jobType: {}", jobType);
        return null;
    }

    @Override
    @Cacheable(value = "jobs", key = "'latest_jobs'")
    public List<JobDTO> getLatestJobs() {
        log.debug("Fetching latest jobs...");
        List<Job> jobs = jobRepository.findAllByOrderByCreatedDateAndCreatedTimeDesc();
        if (jobs.isEmpty()) {
            log.warn("No latest jobs found");
        } else {
            log.info("Fetched {} latest jobs", jobs.size());
        }
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "jobs", allEntries = true)
    @Transactional
    public JobDTO saveJob(JobDTO jobDto) {
        log.debug("Saving job: {}", jobDto);
        Job jobEntity = modelMapper.map(jobDto, Job.class);
        Job job = jobRepository.save(jobEntity);
        log.info("Job saved with id: {}", job.getId());
        return modelMapper.map(job, JobDTO.class);
    }

    @Override
    public long getJobCount() {
        log.debug("Fetching total job count...");
        long count = jobRepository.count();
        log.info("Total job count: {}", count);
        return count;
    }
}
