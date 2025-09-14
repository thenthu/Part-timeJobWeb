/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.dto;

/**
 *
 * @author this pc
 */
public class ResumeDTO {
    private Integer resumeId;
    private String resumeName;
    private MajorDTO major;
    private JoblevelDTO level;
    private String careerObjective;
    private String experience;
    private String skills;
    private String education;
    private String softSkills;
    private String awards;
    private CandidateDTO candidate;

    public Integer getResumeId() {
        return resumeId;
    }

    public void setResumeId(Integer resumeId) {
        this.resumeId = resumeId;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public MajorDTO getMajor() {
        return major;
    }

    public void setMajor(MajorDTO major) {
        this.major = major;
    }

    public JoblevelDTO getLevel() {
        return level;
    }

    public void setLevel(JoblevelDTO level) {
        this.level = level;
    }

    public String getCareerObjective() {
        return careerObjective;
    }

    public void setCareerObjective(String careerObjective) {
        this.careerObjective = careerObjective;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(String softSkills) {
        this.softSkills = softSkills;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public CandidateDTO getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateDTO candidate) {
        this.candidate = candidate;
    }
    
    
}
