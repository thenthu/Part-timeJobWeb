/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.thenthu.dto.CandidateAdminDTO;
import com.thenthu.dto.CandidateDTO;
import com.thenthu.mapper.CandidateMapper;
import com.thenthu.pojo.Account;
import com.thenthu.pojo.Candidate;
import com.thenthu.repositories.AccountRepository;
import com.thenthu.repositories.CandidateRepository;
import com.thenthu.services.CandidateService;
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
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CandidateMapper candidateMapper;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public List<CandidateAdminDTO> getCandidate(Map<String, String> params) {
        List<Candidate> candidates = candidateRepository.getCandidate(params);
        return candidates.stream()
                .map(candidateMapper::toAdminDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CandidateAdminDTO getCandidateByIdAdmin(int id) {
        Candidate candidate = candidateRepository.getCandidateId(id);
        if (candidate == null) {
            return null;
        }
        return candidateMapper.toAdminDTO(candidate);
    }

    @Override
    public void addOrUpdateCandidate(CandidateAdminDTO c) {
        try {
            if (c.getAvatarFile() != null && !c.getAvatarFile().isEmpty()) {
                Map res = cloudinary.uploader().upload(
                        c.getAvatarFile().getBytes(),
                        ObjectUtils.asMap("resource_type", "auto", "folder", "springweb"));
                c.setAvatar(res.get("secure_url").toString());
            }
        } catch (IOException ex) {
            Logger.getLogger(EmployerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        Candidate candidate = candidateMapper.toAdminEntity(c);
        candidateRepository.addOrUpdateCandidate(candidate);
    }

    @Override
    public void deleteCandidate(int id) {
        candidateRepository.deleteCandidate(id);
    }

    @Override
    public CandidateDTO getCandidateById(int id) {
        Candidate candidate = candidateRepository.getCandidateId(id);
        if (candidate == null) {
            return null;
        }
        return candidateMapper.toDTO(candidate);
    }

    @Override
    public List<CandidateDTO> getCandidatesByUsernames(List<String> usernames) {
        List<Candidate> candidates = candidateRepository.getCandidatesByAccounts(usernames);
        return candidates.stream()
                .map(candidateMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CandidateDTO> getCandidates(Map<String, String> params) {
        List<Candidate> candidates = candidateRepository.getCandidate(params);
        return candidates.stream()
                .map(candidateMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public CandidateDTO addCandidate(CandidateDTO c, String username, MultipartFile avatar) {
        Candidate candidate = candidateRepository.getCandidateByAccount(username);
        Account acc = accountRepository.getAccountByUsername(username);

        if (candidate == null) {
            candidate = new Candidate();
            candidate.setAccount(acc);
        }
        candidate.setSFullName(c.getFullName());
        candidate.setDBirthDate(c.getBirthDate());
        candidate.setSGender(c.getGender());
        candidate.setSPhone(c.getPhone());
        candidate.setSAddress(c.getAddress());

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

        CandidateDTO result = candidateMapper.toDTO(candidate);
        return result;
    }
}
