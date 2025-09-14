/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.AccountDTO;
import com.thenthu.dto.CandidateDTO;
import com.thenthu.dto.EmployerDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author this pc
 */
public interface ProfileService {
    AccountDTO getProfile(String username);
    CandidateDTO editCandidateProfile(CandidateDTO c, String username, MultipartFile avatar);
    EmployerDTO editEmployerProfile(EmployerDTO e, String username, MultipartFile avatar, MultipartFile workEnvImg1, MultipartFile workEnvImg2, MultipartFile workEnvImg3);
}
