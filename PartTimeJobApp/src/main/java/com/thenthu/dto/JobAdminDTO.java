/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.dto;

import java.time.LocalDate;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author this pc
 */
public class JobAdminDTO {
    private Integer jobId;
    private String jobTitle;
    private String workAddress;
    private Integer minSalary;
    private Integer maxSalary;
    private Integer quantity;
    private String description;
    private String candidateRequirement;
    private String relatedSkills;
    private String benefits;
    private String workTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate postedDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private Boolean status;

    private EmployerAdminDTO employer;
    private LocationDTO location;
    private MajorDTO major;
    private JoblevelDTO jobLevel;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCandidateRequirement() {
        return candidateRequirement;
    }

    public void setCandidateRequirement(String candidateRequirement) {
        this.candidateRequirement = candidateRequirement;
    }

    public String getRelatedSkills() {
        return relatedSkills;
    }

    public void setRelatedSkills(String relatedSkills) {
        this.relatedSkills = relatedSkills;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public EmployerAdminDTO getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerAdminDTO employer) {
        this.employer = employer;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public MajorDTO getMajor() {
        return major;
    }

    public void setMajor(MajorDTO major) {
        this.major = major;
    }

    public JoblevelDTO getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(JoblevelDTO jobLevel) {
        this.jobLevel = jobLevel;
    }
    
}
