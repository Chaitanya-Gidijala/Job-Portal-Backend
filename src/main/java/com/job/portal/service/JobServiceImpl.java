package com.job.portal.service;

import com.job.portal.dto.JobDTO;
import com.job.portal.entity.Job;
import com.job.portal.repository.JobRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ModelMapper modelMapper;  // Inject ModelMapper

    @Override
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))  // Use ModelMapper to convert Job to JobDTO
                .collect(Collectors.toList());
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if (job != null) {
            return modelMapper.map(job, JobDTO.class);  // Convert Job entity to JobDTO
        }
        return null;
    }

    @Override
    public long getJobCount() {
        return jobRepository.count();  // Return the job count from the repository
    }

    @Override
    public JobDTO saveJob(JobDTO jobDto) {
        Job jobEntity = modelMapper.map(jobDto, Job.class);  // Convert JobDTO to Job entity
        Job job = jobRepository.save(jobEntity);
        return modelMapper.map(job, JobDTO.class);  // Convert Job entity back to JobDTO
    }

    @Override
    public List<JobDTO> getJobsByType(String jobType) {
        List<Job> jobs = jobRepository.findByJobType(jobType);
        if (!jobs.isEmpty()) {
            return jobs.stream()
                    .map(job -> modelMapper.map(job, JobDTO.class))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
