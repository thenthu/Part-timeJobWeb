/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Candidate;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface CandidateRepository {
    List<Candidate> getCandidate(Map<String, String> params);
    Candidate getCandidateId(int id);
    void addOrUpdateCandidate(Candidate c);
    void deleteCandidate(int id);
    Candidate getCandidateByAccount(String username);
    List<Candidate> getCandidatesByAccounts(List<String> usernames);
}
