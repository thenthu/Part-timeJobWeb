/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author this pc
 */
@Entity
@Table(name = "application")
@NamedQueries({
    @NamedQuery(name = "Application.findAll", query = "SELECT a FROM Application a"),
    @NamedQuery(name = "Application.findByICandidateId", query = "SELECT a FROM Application a WHERE a.applicationPK.iCandidateId = :iCandidateId"),
    @NamedQuery(name = "Application.findByIJobPostId", query = "SELECT a FROM Application a WHERE a.applicationPK.iJobPostId = :iJobPostId"),
    @NamedQuery(name = "Application.findByDApplyDate", query = "SELECT a FROM Application a WHERE a.dApplyDate = :dApplyDate"),
    @NamedQuery(name = "Application.findBySStatus", query = "SELECT a FROM Application a WHERE a.sStatus = :sStatus")})
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ApplicationPK applicationPK;
    @Column(name = "dApplyDate")
    private LocalDate dApplyDate;
    @Size(max = 50)
    @Column(name = "sStatus")
    private String sStatus;
    @JoinColumn(name = "iCandidateId", referencedColumnName = "iCandidateId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Candidate candidate;
    @JoinColumn(name = "iJobPostId", referencedColumnName = "iJobPostId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Job job;
    @JoinColumn(name = "iResumeId", referencedColumnName = "iResumeId")
    @ManyToOne
    private Resume iResumeId;

    @PrePersist
    protected void onCreate() {
        if (dApplyDate == null) {
            setDApplyDate(LocalDate.now());
        }
        if (sStatus == null) {
            sStatus = "Đã nộp";
        }
    }
    
    public Application() {
    }

    public Application(ApplicationPK applicationPK) {
        this.applicationPK = applicationPK;
    }

    public Application(int iCandidateId, int iJobPostId) {
        this.applicationPK = new ApplicationPK(iCandidateId, iJobPostId);
    }

    public ApplicationPK getApplicationPK() {
        return applicationPK;
    }

    public void setApplicationPK(ApplicationPK applicationPK) {
        this.applicationPK = applicationPK;
    }

    public LocalDate getDApplyDate() {
        return dApplyDate;
    }

    public void setDApplyDate(LocalDate dApplyDate) {
        this.dApplyDate = dApplyDate;
    }

    public String getSStatus() {
        return sStatus;
    }

    public void setSStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Resume getIResumeId() {
        return iResumeId;
    }

    public void setIResumeId(Resume iResumeId) {
        this.iResumeId = iResumeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applicationPK != null ? applicationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.applicationPK == null && other.applicationPK != null) || (this.applicationPK != null && !this.applicationPK.equals(other.applicationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Application[ applicationPK=" + applicationPK + " ]";
    }
    
}
