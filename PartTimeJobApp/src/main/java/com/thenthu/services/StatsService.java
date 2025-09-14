/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import java.util.List;

/**
 *
 * @author this pc
 */
public interface StatsService {
    List<Object[]> countJobsByMonth(String time, int year);
    List<Object[]> countCandidatesByMonth(String time, int year);
    List<Object[]> countEmployersByMonth(String time, int year);
}
