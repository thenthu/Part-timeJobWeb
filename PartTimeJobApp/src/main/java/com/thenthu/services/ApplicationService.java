/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.ApplicationDTO;
import java.util.List;

/**
 *
 * @author this pc
 */
public interface ApplicationService {
    List<ApplicationDTO> getApplicationsByCandidateId(int candidateId);
    List<ApplicationDTO> getApplicationsByJobId(int jobId);
    ApplicationDTO getApplicationByCandidateAndJob(int candidateId, int jobId);
    
    ApplicationDTO applyToJob(ApplicationDTO application);
    boolean applicationExits(int candidateId, int jobId);
    ApplicationDTO updateApplication(ApplicationDTO app);
}
