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
public class ApplicationDTO {
    private CandidateDTO candidate;
    private JobDTO jobPost;
    private ResumeDTO resume;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate applyDate;
    private String status;

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }

    public JobDTO getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobDTO jobPost) {
        this.jobPost = jobPost;
    }

    public ResumeDTO getResume() {
        return resume;
    }

    public void setResume(ResumeDTO resume) {
        this.resume = resume;
    }

    public LocalDate getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(LocalDate applyDate) {
        this.applyDate = applyDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
