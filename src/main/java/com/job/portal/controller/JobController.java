package com.job.portal.controller;

import com.job.portal.dto.JobDTO;
import com.job.portal.service.JobService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<JobDTO>> getJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        if (jobs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/type/{jobType}")
    public ResponseEntity<List<JobDTO>> getJobsByType(@PathVariable String jobType) {
        List<JobDTO> jobs = jobService.getJobsByType(jobType);
        if (jobs == null || jobs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jobs);
    }

    // Endpoint to get the latest jobs sorted by the most recent posting time
    @GetMapping("/latest")
    public ResponseEntity<List<JobDTO>> getLatestJobs() {
        List<JobDTO> jobs = jobService.getLatestJobs();
        if (jobs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(jobs);
    }

    // Endpoint to get a specific job by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJob(@PathVariable Long id) {
        JobDTO jobDTO = jobService.getJobById(id);
        if (jobDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jobDTO);  // Return HTTP 200 with the job details
    }

    @GetMapping("/title/{jobTitle}")
    public ResponseEntity<JobDTO> getJobByTitle(@PathVariable String jobTitle) {
        // Replace hyphens with spaces before passing to the service
        jobTitle = jobTitle.replace('-', ' ');

        JobDTO jobDTO = jobService.getJobByTitle(jobTitle);  // Fetch the job by job title
        if (jobDTO == null) {
            return ResponseEntity.notFound().build();  // Return 404 if job not found
        }
        return ResponseEntity.ok(jobDTO);  // Return job details with HTTP 200
    }


    // Endpoint to get the job count
    @GetMapping("/count")
    public ResponseEntity<Long> getJobCount() {
        long count = jobService.getJobCount();
        return ResponseEntity.ok(count);
    }

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@Valid @RequestBody JobDTO jobDTO) {
        JobDTO savedJob = jobService.saveJob(jobDTO);
        return ResponseEntity.status(201).body(savedJob);
    }
}
