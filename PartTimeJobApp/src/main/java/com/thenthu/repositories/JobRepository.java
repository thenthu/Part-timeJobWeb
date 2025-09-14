/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Job;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface JobRepository {
    List<Job> getJob(Map<String, String> params);
    Job getJobById(int id);
    void addOrUpdateJob(Job j);
    void deleteJob(int id);
    
    int getTotalPages(Map<String, String> params);
    List<Job> getJobsByEmployerId(int employerId);
}
