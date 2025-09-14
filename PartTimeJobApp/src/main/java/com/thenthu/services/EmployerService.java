/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.EmployerAdminDTO;
import com.thenthu.dto.EmployerDTO;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author this pc
 */
public interface EmployerService {
    List<EmployerAdminDTO> getEmployer(Map<String, String> params);
    EmployerAdminDTO getEmployerByIdAdmin(int id);
    void addOrUpdateEmployer(EmployerAdminDTO e);
    void deleteEmployer(int id);
    
    List<EmployerDTO> getEmployersByUsernames(List<String> usernames);
    List<EmployerDTO> getEmployers(Map<String, String> params);
    EmployerDTO getEmployerById(int id);
    EmployerDTO addEmployer(EmployerDTO e, String username, MultipartFile avatar, MultipartFile workEnvImg1, MultipartFile workEnvImg2, MultipartFile workEnvImg3, MultipartFile verifyDoc);
}
