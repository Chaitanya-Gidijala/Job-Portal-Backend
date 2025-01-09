package com.job.portal.controller;

import com.job.portal.dto.JobDTO;
import com.job.portal.entity.Job;
import com.job.portal.service.JobService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private ModelMapper modelMapper;

    // Endpoint to get all jobs
    @GetMapping
    public List<JobDTO> getJobs() {
        return jobService.getAllJobs();
    }
    @GetMapping("/type/{jobType}")
    public List<JobDTO> getJobsByType(@PathVariable String jobType) {
        return jobService.getJobsByType(jobType);
    }
    // Endpoint to get a specific job by ID
    @GetMapping("/{id}")
    public JobDTO getJob(@PathVariable Long id) {
        return jobService.getJobById(id);
    }

    // Endpoint to get the job count
    @GetMapping("/count")
    public long getJobCount() {
        return jobService.getJobCount();  // This returns the count of jobs
    }

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@RequestBody JobDTO jobDTO) {
        // Save the job
        JobDTO savedJob = jobService.saveJob(jobDTO);
        // Return the saved job as a JobDTO (this will be converted to JSON)
        return ResponseEntity.ok(savedJob);
    }


}
