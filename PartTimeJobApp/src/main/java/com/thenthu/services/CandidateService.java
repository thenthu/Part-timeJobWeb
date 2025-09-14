/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.CandidateAdminDTO;
import com.thenthu.dto.CandidateDTO;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author this pc
 */
public interface CandidateService {
    List<CandidateAdminDTO> getCandidate(Map<String, String> params);
    CandidateAdminDTO getCandidateByIdAdmin(int id);
    void addOrUpdateCandidate(CandidateAdminDTO c);
    void deleteCandidate(int id);
    
    List<CandidateDTO> getCandidatesByUsernames(List<String> usernames);
    List<CandidateDTO> getCandidates(Map<String, String> params);
    CandidateDTO getCandidateById(int id);
    CandidateDTO addCandidate(CandidateDTO c, String username, MultipartFile avatar);
}
