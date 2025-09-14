/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Joblevel;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface LevelRepository {
    List<Joblevel> getJoblevels(Map<String, String> params);
    Joblevel getLevelById(int id);
    void addOrUpdateLevel(Joblevel l);
    void deleteLevel(int id);
}
