/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.ResumeDTO;
import java.util.List;

/**
 *
 * @author this pc
 */
public interface ResumeService {
    List<ResumeDTO> getResumesByCandidateId(int candidateId);
    ResumeDTO getResumeById(int id);
    void deleteResume(int id);
    void addOrUpdateResume(ResumeDTO dto);
}
