/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import java.util.List;

/**
 *
 * @author this pc
 */
public interface StatsRepository {
    List<Object[]> countJobsByTime(String time, int year);
    List<Object[]> countCandidatesByTime(String time, int year);
    List<Object[]> countEmployersByTime(String time, int year);
}
