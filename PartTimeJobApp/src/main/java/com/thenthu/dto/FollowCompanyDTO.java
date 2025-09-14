/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

/**
 *
 * @author this pc
 */
public class FollowCompanyDTO {
    private CandidateDTO candidate;
    private EmployerDTO employer;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate followedAt;

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }

    public EmployerDTO getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDTO employer) {
        this.employer = employer;
    }

    public LocalDate getFollowedAt() {
        return followedAt;
    }

    public void setFollowedAt(LocalDate followedAt) {
        this.followedAt = followedAt;
    }
    
}
