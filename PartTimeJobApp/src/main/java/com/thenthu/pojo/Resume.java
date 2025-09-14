/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author this pc
 */
@Entity
@Table(name = "resume")
@NamedQueries({
    @NamedQuery(name = "Resume.findAll", query = "SELECT r FROM Resume r"),
    @NamedQuery(name = "Resume.findByIResumeId", query = "SELECT r FROM Resume r WHERE r.iResumeId = :iResumeId"),
    @NamedQuery(name = "Resume.findBySResumeName", query = "SELECT r FROM Resume r WHERE r.sResumeName = :sResumeName")})
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iResumeId")
    private Integer iResumeId;
    @Size(max = 100)
    @Column(name = "sResumeName")
    private String sResumeName;
    @Lob
    @Size(max = 65535)
    @Column(name = "sCareerObjective")
    private String sCareerObjective;
    @Lob
    @Size(max = 65535)
    @Column(name = "sExperience")
    private String sExperience;
    @Lob
    @Size(max = 65535)
    @Column(name = "sSkills")
    private String sSkills;
    @Lob
    @Size(max = 65535)
    @Column(name = "sEducation")
    private String sEducation;
    @Lob
    @Size(max = 65535)
    @Column(name = "sSoftSkills")
    private String sSoftSkills;
    @Lob
    @Size(max = 65535)
    @Column(name = "sAwards")
    private String sAwards;
    @JoinColumn(name = "iCandidateId", referencedColumnName = "iCandidateId")
    @ManyToOne
    private Candidate iCandidateId;
    @JoinColumn(name = "iLevelId", referencedColumnName = "iLevelId")
    @ManyToOne
    private Joblevel iLevelId;
    @JoinColumn(name = "iMajorId", referencedColumnName = "iMajorId")
    @ManyToOne
    private Major iMajorId;

    public Resume() {
    }

    public Resume(Integer iResumeId) {
        this.iResumeId = iResumeId;
    }

    public Integer getIResumeId() {
        return iResumeId;
    }

    public void setIResumeId(Integer iResumeId) {
        this.iResumeId = iResumeId;
    }

    public String getSResumeName() {
        return sResumeName;
    }

    public void setSResumeName(String sResumeName) {
        this.sResumeName = sResumeName;
    }

    public String getSCareerObjective() {
        return sCareerObjective;
    }

    public void setSCareerObjective(String sCareerObjective) {
        this.sCareerObjective = sCareerObjective;
    }

    public String getSExperience() {
        return sExperience;
    }

    public void setSExperience(String sExperience) {
        this.sExperience = sExperience;
    }

    public String getSSkills() {
        return sSkills;
    }

    public void setSSkills(String sSkills) {
        this.sSkills = sSkills;
    }

    public String getSEducation() {
        return sEducation;
    }

    public void setSEducation(String sEducation) {
        this.sEducation = sEducation;
    }

    public String getSSoftSkills() {
        return sSoftSkills;
    }

    public void setSSoftSkills(String sSoftSkills) {
        this.sSoftSkills = sSoftSkills;
    }

    public String getSAwards() {
        return sAwards;
    }

    public void setSAwards(String sAwards) {
        this.sAwards = sAwards;
    }

    public Candidate getICandidateId() {
        return iCandidateId;
    }

    public void setICandidateId(Candidate iCandidateId) {
        this.iCandidateId = iCandidateId;
    }

    public Joblevel getILevelId() {
        return iLevelId;
    }

    public void setILevelId(Joblevel iLevelId) {
        this.iLevelId = iLevelId;
    }

    public Major getIMajorId() {
        return iMajorId;
    }

    public void setIMajorId(Major iMajorId) {
        this.iMajorId = iMajorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iResumeId != null ? iResumeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resume)) {
            return false;
        }
        Resume other = (Resume) object;
        if ((this.iResumeId == null && other.iResumeId != null) || (this.iResumeId != null && !this.iResumeId.equals(other.iResumeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.pojo.Resume[ iResumeId=" + iResumeId + " ]";
    }
    
}
