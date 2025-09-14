/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Major;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface MajorRepository {
    List<Major> getMajors(Map<String, String> params);
    Major getMajorById(int id);
    void addOrUpdateMajor(Major m);
    void deleteMajor(int id);
}
