/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.JoblevelDTO;
import com.thenthu.mapper.JoblevelMapper;
import com.thenthu.pojo.Joblevel;
import com.thenthu.repositories.LevelRepository;
import com.thenthu.services.LevelService;
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
public class LevelServiceImpl implements LevelService {

    @Autowired
    private LevelRepository leRepo;
    @Autowired
    private JoblevelMapper jobLevelMapper;

    @Override
    public List<JoblevelDTO> getJoblevels(Map<String, String> params) {
        List<Joblevel> joblevels = this.leRepo.getJoblevels(params);
        return joblevels.stream()
                .map(jobLevelMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public JoblevelDTO getLevelById(int id) {
        Joblevel level = leRepo.getLevelById(id);
        if (level == null) {
            return null;
        }
        return jobLevelMapper.toDTO(level);
    }

    @Override
    public void addOrUpdateLevel(JoblevelDTO j) {
        Joblevel entity = jobLevelMapper.toEntity(j);
        leRepo.addOrUpdateLevel(entity);
    }

    @Override
    public void deleteLevel(int id) {
        leRepo.deleteLevel(id);
    }
}
