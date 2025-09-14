/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Application;
import java.util.List;

/**
 *
 * @author this pc
 */
public interface ApplicationRepository {
    List<Application> getApplicationsByCandidateId(int candidateId);
    List<Application> getApplicationsByJobId(int jobId);
    Application getApplicationByCandidateAndJob(int candidateId, int jobId);
    Application applyToJob(Application a);
    boolean applicationExits(int candidateId, int jobId);
    Application updateApplication(Application app);
}
