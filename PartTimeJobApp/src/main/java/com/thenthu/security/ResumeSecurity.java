/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.security;

import com.thenthu.dto.AccountDTO;
import com.thenthu.dto.CandidateDTO;
import com.thenthu.dto.ResumeDTO;
import com.thenthu.services.CandidateService;
import com.thenthu.services.ProfileService;
import com.thenthu.services.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author this pc
 */
@Component("resumeSecurity")
public class ResumeSecurity {
    @Autowired
    private ResumeService resumeService;
    @Autowired
    private ProfileService profileService;
    
    public boolean isOwner(int resumeId, String principal) {
        ResumeDTO resume = resumeService.getResumeById(resumeId);
        String username = principal;
        
        AccountDTO currentUser = profileService.getProfile(username);
        if(!(currentUser instanceof CandidateDTO)) {
            return false;
        }
        CandidateDTO candidate = (CandidateDTO) currentUser;
        return resume.getCandidate().getCandidateId().equals(candidate.getCandidateId());
    }
}
