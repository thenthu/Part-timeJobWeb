/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.JobAdminDTO;
import com.thenthu.dto.JobDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface JobService {
    List<JobAdminDTO> getJob(Map<String, String> params);
    void addOrUpdateJob(JobAdminDTO dto);
    JobAdminDTO getJobByIdAdmin(int id);
    void deleteJob(int id);
    List<JobAdminDTO> getJobsByEmployerId(int employerId);
    
    List<JobDTO> getJobs(Map<String, String> params);
    JobDTO getJobById(int id);
    void addOrUpdateJob(JobDTO dto);
    List<JobDTO> getJobsForEmployer(Map<String, String> params);
    int getTotalPages(Map<String, String> params);
}
