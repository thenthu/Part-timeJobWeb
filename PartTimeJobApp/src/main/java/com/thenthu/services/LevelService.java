/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.JoblevelDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface LevelService {
    List<JoblevelDTO> getJoblevels(Map<String, String> params);
    JoblevelDTO getLevelById(int id);
    void addOrUpdateLevel(JoblevelDTO j);
    void deleteLevel(int id);
}
