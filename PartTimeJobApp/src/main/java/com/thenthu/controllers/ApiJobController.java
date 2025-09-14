/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.JobDTO;
import com.thenthu.services.JobService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author this pc
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiJobController {
    @Autowired
    private JobService jobService;
    
    @GetMapping("/jobs")
    public ResponseEntity<?> list(@RequestParam Map<String, String> params) {
        List<JobDTO> jobs = this.jobService.getJobs(params);
        int totalPages = this.jobService.getTotalPages(params);
        
        Map<String, Object> result = new HashMap<>();
        result.put("jobs", jobs);
        result.put("totalPages", totalPages);
        
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("/job/{jobId}")
    public ResponseEntity<JobDTO> retrieve(@PathVariable("jobId") int jobId) {
        return new ResponseEntity<>(this.jobService.getJobById(jobId), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/job/{jobId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletejob(@PathVariable("jobId") int id) {
        jobService.deleteJob(id);
    }
}
