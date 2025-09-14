/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.MajorDTO;
import com.thenthu.pojo.Major;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface MajorService {
    List<MajorDTO> getMajors(Map<String, String> params);
    MajorDTO getMajorById(int id);
    void addOrUpdateMajor(MajorDTO dto);
    void deleteMajor(int id);
}
