/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.MajorDTO;
import com.thenthu.mapper.MajorMapper;
import com.thenthu.pojo.Major;
import com.thenthu.repositories.MajorRepository;
import com.thenthu.services.MajorService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorRepository majRepo;
    @Autowired
    private MajorMapper majorMapper;

    @Override
    public List<MajorDTO> getMajors(Map<String, String> params) {
        List<Major> majors = this.majRepo.getMajors(params);
        return majors.stream()
                .map(majorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MajorDTO getMajorById(int id) {
        Major major = majRepo.getMajorById(id);
        if (major == null) {
            return null;
        }
        return majorMapper.toDTO(major);
    }

    @Override
    public void addOrUpdateMajor(MajorDTO dto) {
        Major entity = majorMapper.toEntity(dto);
        majRepo.addOrUpdateMajor(entity);
    }

    @Override
    public void deleteMajor(int id) {
        majRepo.deleteMajor(id);
    }
}
