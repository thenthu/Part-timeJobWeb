/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.repositories.StatsRepository;
import com.thenthu.services.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class StatsServiceImpl implements StatsService {
    @Autowired
    private StatsRepository statsRepo;

    @Override
    public List<Object[]> countJobsByMonth(String time, int year) {
        return this.statsRepo.countJobsByTime(time, year);
    }

    @Override
    public List<Object[]> countCandidatesByMonth(String time, int year) {
        return this.statsRepo.countCandidatesByTime(time, year);
    }

    @Override
    public List<Object[]> countEmployersByMonth(String time, int year) {
        return this.statsRepo.countEmployersByTime(time, year);
    }
    
}
