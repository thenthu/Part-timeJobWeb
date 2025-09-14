/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thenthu.dto.AccountDTO;
import com.thenthu.dto.CandidateDTO;
import com.thenthu.dto.EmployerDTO;
import com.thenthu.mapper.AccountMapper;
import com.thenthu.mapper.CandidateMapper;
import com.thenthu.mapper.EmployerMapper;
import com.thenthu.pojo.Account;
import com.thenthu.pojo.Candidate;
import com.thenthu.pojo.Employer;
import com.thenthu.repositories.AccountRepository;
import com.thenthu.repositories.CandidateRepository;
import com.thenthu.repositories.EmployerRepository;
import com.thenthu.services.ProfileService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author this pc
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private CandidateMapper candidateMapper;
    @Autowired
    private EmployerMapper employerMapper;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public AccountDTO getProfile(String username) {
        Account account = accountRepository.getAccountByUsername(username);
        String role = account.getIRoleId().getSRoleName();

        if (role.equals("ROLE_CANDIDATE")) {
            if (account.getCandidate() != null) {
                Candidate candidate = candidateRepository.getCandidateId(account.getCandidate().getICandidateId());
                return candidateMapper.toDTO(candidate);
            }
        } else {
            if (account.getEmployer() != null) {
                Employer employer = employerRepository.getEmployerById(account.getEmployer().getIEmployerId());
                return employerMapper.toEmployerDTO(employer);
            }
        }
        return accountMapper.toDTO(account);
    }

    @Override
    public CandidateDTO editCandidateProfile(CandidateDTO dto, String username, MultipartFile avatar) {
        Candidate candidate = candidateRepository.getCandidateByAccount(username);
        if (candidate == null) {
            throw new IllegalArgumentException("Không tìm thấy ứng viên");
        }

        Account account = candidate.getAccount();

        if (dto.getEmail() != null) {
            account.setSEmail(dto.getEmail());
        }
        if (dto.getUsername() != null) {
            account.setSUsername(dto.getUsername());
        }

        if (dto.getFullName() != null) {
            candidate.setSFullName(dto.getFullName());
        }

        if (dto.getPhone() != null) {
            candidate.setSPhone(dto.getPhone());
        }

        if (dto.getAddress() != null) {
            candidate.setSAddress(dto.getAddress());
        }
        if (dto.getBirthDate() != null) {
            candidate.setDBirthDate(dto.getBirthDate());
        }
        if (dto.getGender() != null) {
            candidate.setSGender(dto.getGender());
        }

        if (avatar != null && !avatar.isEmpty()) {
            try {
                Map res = cloudinary.uploader().upload(
                        avatar.getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                candidate.setSAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(CandidateServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        candidateRepository.addOrUpdateCandidate(candidate);

        return candidateMapper.toDTO(candidate);
    }

    @Override
    public EmployerDTO editEmployerProfile(EmployerDTO e, String username, MultipartFile avatar, MultipartFile workEnvImg1, MultipartFile workEnvImg2, MultipartFile workEnvImg3) {
        Employer employer = employerRepository.getEmployerByAccount(username);
        if (employer == null) {
            throw new IllegalArgumentException("Không tìm thấy nhà tuyển dụng");
        }

        if (e.getCompanyName() != null) {
            employer.setSCompanyName(e.getCompanyName());
        }
        if (e.getRepresentativeName() != null) {
            employer.setSRepresentativeName(e.getRepresentativeName());
        }
        if (e.getRepresentativeTitle() != null) {
            employer.setSRepresentativeTitle(e.getRepresentativeTitle());
        }
        if (e.getTaxCode() != null) {
            employer.setSTaxCode(e.getTaxCode());
        }
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
        } catch (IOException ex) {
            Logger.getLogger(EmployerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        employerRepository.addOrUpdateEmployer(employer);

        EmployerDTO dto = employerMapper.toEmployerDTO(employer);
        return dto;
    }
}
