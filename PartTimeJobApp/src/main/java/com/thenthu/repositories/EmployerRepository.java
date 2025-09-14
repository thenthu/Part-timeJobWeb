/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Employer;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface EmployerRepository {
    List<Employer> getEmployer(Map<String, String> params);
    Employer getEmployerById(int id);
    void addOrUpdateEmployer(Employer e);
    void deleteEmployer(int id);
    Employer getEmployerByAccount(String username);
    List<Employer> getEmployersByAccounts(List<String> usernames);
}
