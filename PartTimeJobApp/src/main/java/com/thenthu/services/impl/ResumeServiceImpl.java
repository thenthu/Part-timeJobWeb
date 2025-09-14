/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.ResumeDTO;
import com.thenthu.mapper.ResumeMapper;
import com.thenthu.pojo.Resume;
import com.thenthu.repositories.ResumeRepository;
import com.thenthu.services.ResumeService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository reRepo;
    @Autowired
    private ResumeMapper resumeMapper;
    
    @Override
    public List<ResumeDTO> getResumesByCandidateId(int candidateId) {
        List<Resume> resumes = reRepo.getResumesByCandidateId(candidateId);
        return resumes.stream()
                .map(resumeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ResumeDTO getResumeById(int id) {
        Resume resume = reRepo.getResumeById(id);
        return resumeMapper.toDTO(resume);
    }

    @Override
    public void deleteResume(int id) {
        reRepo.deleteResume(id);
    }

    @Override
    public void addOrUpdateResume(ResumeDTO dto) {
        Resume resume = resumeMapper.toEntity(dto);
        this.reRepo.addOrUpdateResume(resume);
    }
    
}
