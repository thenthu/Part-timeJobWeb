/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thenthu.dto.EmployerAdminDTO;
import com.thenthu.dto.EmployerDTO;
import com.thenthu.mapper.EmployerMapper;
import com.thenthu.pojo.Account;
import com.thenthu.pojo.Employer;
import com.thenthu.repositories.AccountRepository;
import com.thenthu.repositories.EmployerRepository;
import com.thenthu.services.EmployerService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author this pc
 */
@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepo;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EmployerMapper employerMapper;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<EmployerAdminDTO> getEmployer(Map<String, String> params) {
        List<Employer> employers = employerRepo.getEmployer(params);
        return employers.stream()
                .map(employerMapper::toEmployerAdminDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployerAdminDTO getEmployerByIdAdmin(int id) {
        Employer employer = employerRepo.getEmployerById(id);
        return employerMapper.toEmployerAdminDTO(employer);
    }

    @Override
    public void addOrUpdateEmployer(EmployerAdminDTO dto) {
        try {

            if (dto.getAvatarFile() != null && !dto.getAvatarFile().isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        dto.getAvatarFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                dto.setAvatar(res.get("secure_url").toString());
            }

            if (dto.getWorkEnvImg1File() != null && !dto.getWorkEnvImg1File().isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        dto.getWorkEnvImg1File().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                dto.setWorkEnvImg1(res.get("secure_url").toString());
            }
            if (dto.getWorkEnvImg2File() != null && !dto.getWorkEnvImg2File().isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        dto.getWorkEnvImg2File().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                dto.setWorkEnvImg2(res.get("secure_url").toString());
            }
            if (dto.getWorkEnvImg3File() != null && !dto.getWorkEnvImg3File().isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        dto.getWorkEnvImg3File().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                dto.setWorkEnvImg3(res.get("secure_url").toString());
            }

            if (dto.getVerifyDocFile() != null && !dto.getVerifyDocFile().isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        dto.getVerifyDocFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                dto.setVerifyDoc(res.get("secure_url").toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(EmployerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        Employer employer = employerMapper.toEmployerEntity(dto);
        employerRepo.addOrUpdateEmployer(employer);
    }

    @Override
    public void deleteEmployer(int id) {
        employerRepo.deleteEmployer(id);
    }
    
    @Override
    public List<EmployerDTO> getEmployersByUsernames(List<String> usernames) {
        List<Employer> employers = employerRepo.getEmployersByAccounts(usernames);
        return employers.stream()
                .map(employerMapper::toEmployerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployerDTO> getEmployers(Map<String, String> params) {
        List<Employer> employers = employerRepo.getEmployer(params);
        return employers.stream()
                .map(employerMapper::toEmployerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployerDTO getEmployerById(int id) {
        Employer employer = employerRepo.getEmployerById(id);
        return employerMapper.toEmployerDTO(employer);
    }

    @Override
    public EmployerDTO addEmployer(EmployerDTO e, String username, MultipartFile avatar, MultipartFile workEnvImg1, MultipartFile workEnvImg2, MultipartFile workEnvImg3, MultipartFile verifyDoc) {
        Employer employer = employerRepo.getEmployerByAccount(username);
        Account acc = accountRepository.getAccountByUsername(username);

        if (employer == null) {
            employer = new Employer();
            employer.setAccount(acc);
        }
        employer.setSCompanyName(e.getCompanyName());
        employer.setSTaxCode(e.getTaxCode());
        employer.setSRepresentativeName(e.getRepresentativeName());
        employer.setSRepresentativeTitle(e.getRepresentativeTitle());

        try {

            if (avatar != null && !avatar.isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                employer.setSAvatar(res.get("secure_url").toString());
            }
            if (workEnvImg1 != null && !workEnvImg1.isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        workEnvImg1.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                employer.setSWorkEnvImg1(res.get("secure_url").toString());
            }
            if (workEnvImg2 != null && !workEnvImg2.isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        workEnvImg2.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                employer.setSWorkEnvImg2(res.get("secure_url").toString());
            }
            if (workEnvImg3 != null && !workEnvImg3.isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        workEnvImg3.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                employer.setSWorkEnvImg3(res.get("secure_url").toString());
            }
            if (verifyDoc != null && !verifyDoc.isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        verifyDoc.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                employer.setSVerifyDoc(res.get("secure_url").toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(EmployerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        employerRepo.addOrUpdateEmployer(employer);

        EmployerDTO result = employerMapper.toEmployerDTO(employer);
        return result;
    }
}
