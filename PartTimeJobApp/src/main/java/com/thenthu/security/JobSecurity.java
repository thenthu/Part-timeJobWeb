/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.security;

import com.thenthu.dto.AccountDTO;
import com.thenthu.dto.EmployerDTO;
import com.thenthu.dto.JobDTO;
import com.thenthu.services.JobService;
import com.thenthu.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author this pc
 */
@Component("jobSecurity")
public class JobSecurity {
    @Autowired
    private JobService jobService;
    @Autowired
    private ProfileService profileService;
    
    public boolean isOwner(int jobId, String principal) {
        JobDTO job = jobService.getJobById(jobId);
        String username = principal;
        
        AccountDTO currentUser = profileService.getProfile(username);
        if(!(currentUser instanceof EmployerDTO)) {
            return false;
        }
        EmployerDTO currentEmployer = (EmployerDTO) currentUser;
        
        return job.getEmployer().getEmployerId().equals(currentEmployer.getEmployerId());
    }
}
