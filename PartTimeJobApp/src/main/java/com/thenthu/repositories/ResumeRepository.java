/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Resume;
import java.util.List;

/**
 *
 * @author this pc
 */
public interface ResumeRepository {
    List<Resume> getResumesByCandidateId(int candidateId);
    Resume getResumeById(int id);
    void deleteResume(int id);
    void addOrUpdateResume(Resume r);
}
