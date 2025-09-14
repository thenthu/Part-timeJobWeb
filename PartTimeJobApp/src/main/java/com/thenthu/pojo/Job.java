/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author this pc
 */
@Entity
@Table(name = "job")
@NamedQueries({
    @NamedQuery(name = "Job.findAll", query = "SELECT j FROM Job j"),
    @NamedQuery(name = "Job.findByIJobPostId", query = "SELECT j FROM Job j WHERE j.iJobPostId = :iJobPostId"),
    @NamedQuery(name = "Job.findBySJobTitle", query = "SELECT j FROM Job j WHERE j.sJobTitle = :sJobTitle"),
    @NamedQuery(name = "Job.findBySWorkAddress", query = "SELECT j FROM Job j WHERE j.sWorkAddress = :sWorkAddress"),
    @NamedQuery(name = "Job.findByIMinSalary", query = "SELECT j FROM Job j WHERE j.iMinSalary = :iMinSalary"),
    @NamedQuery(name = "Job.findByIMaxSalary", query = "SELECT j FROM Job j WHERE j.iMaxSalary = :iMaxSalary"),
    @NamedQuery(name = "Job.findByIQuantity", query = "SELECT j FROM Job j WHERE j.iQuantity = :iQuantity"),
    @NamedQuery(name = "Job.findBySWorkTime", query = "SELECT j FROM Job j WHERE j.sWorkTime = :sWorkTime"),
    @NamedQuery(name = "Job.findByDPostedDate", query = "SELECT j FROM Job j WHERE j.dPostedDate = :dPostedDate"),
    @NamedQuery(name = "Job.findByDDeadline", query = "SELECT j FROM Job j WHERE j.dDeadline = :dDeadline"),
    @NamedQuery(name = "Job.findByBStatus", query = "SELECT j FROM Job j WHERE j.bStatus = :bStatus")})
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iJobPostId")
    private Integer iJobPostId;
    @Size(max = 100)
    @Column(name = "sJobTitle")
    private String sJobTitle;
    @Size(max = 255)
    @Column(name = "sWorkAddress")
    private String sWorkAddress;
    @Column(name = "iMinSalary")
    private Integer iMinSalary;
    @Column(name = "iMaxSalary")
    private Integer iMaxSalary;
    @Column(name = "iQuantity")
    private Integer iQuantity;
    @Lob
    @Size(max = 65535)
    @Column(name = "sDescription")
    private String sDescription;
    @Lob
    @Size(max = 65535)
    @Column(name = "sCandidateRequirement")
    private String sCandidateRequirement;
    @Lob
    @Size(max = 65535)
    @Column(name = "sRelatedSkills")
    private String sRelatedSkills;
    @Lob
    @Size(max = 65535)
    @Column(name = "sBenefits")
    private String sBenefits;
    @Size(max = 100)
    @Column(name = "sWorkTime")
    private String sWorkTime;
    @Column(name = "dPostedDate")
    private LocalDate dPostedDate;
    @Column(name = "dDeadline")
    private LocalDate dDeadline;
    @Column(name = "bStatus")
    private Boolean bStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "job")
    private Set<Application> applicationSet;
    @JoinColumn(name = "iEmployerId", referencedColumnName = "iEmployerId")
    @ManyToOne
    private Employer iEmployerId;
    @JoinColumn(name = "iLevelId", referencedColumnName = "iLevelId")
    @ManyToOne
    private Joblevel iLevelId;
    @JoinColumn(name = "iLocationId", referencedColumnName = "iLocationId")
    @ManyToOne
    private Location iLocationId;
    @JoinColumn(name = "iMajorId", referencedColumnName = "iMajorId")
    @ManyToOne
    private Major iMajorId;

    @PrePersist
    protected void onCreate() {
        if (dPostedDate == null) {
            setDPostedDate(LocalDate.now());
        }
        if (bStatus == null) {
            bStatus = true;
        }
    }
    
    public Job() {
    }

    public Job(Integer iJobPostId) {
        this.iJobPostId = iJobPostId;
    }

    public Integer getIJobPostId() {
        return iJobPostId;
    }

    public void setIJobPostId(Integer iJobPostId) {
        this.iJobPostId = iJobPostId;
    }

    public String getSJobTitle() {
        return sJobTitle;
    }

    public void setSJobTitle(String sJobTitle) {
        this.sJobTitle = sJobTitle;
    }

    public String getSWorkAddress() {
        return sWorkAddress;
    }

    public void setSWorkAddress(String sWorkAddress) {
        this.sWorkAddress = sWorkAddress;
    }

    public Integer getIMinSalary() {
        return iMinSalary;
    }

    public void setIMinSalary(Integer iMinSalary) {
        this.iMinSalary = iMinSalary;
    }

    public Integer getIMaxSalary() {
        return iMaxSalary;
    }

    public void setIMaxSalary(Integer iMaxSalary) {
        this.iMaxSalary = iMaxSalary;
    }

    public Integer getIQuantity() {
        return iQuantity;
    }

    public void setIQuantity(Integer iQuantity) {
        this.iQuantity = iQuantity;
    }

    public String getSDescription() {
        return sDescription;
    }

    public void setSDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getSCandidateRequirement() {
        return sCandidateRequirement;
    }

    public void setSCandidateRequirement(String sCandidateRequirement) {
        this.sCandidateRequirement = sCandidateRequirement;
    }

    public String getSRelatedSkills() {
        return sRelatedSkills;
    }

    public void setSRelatedSkills(String sRelatedSkills) {
        this.sRelatedSkills = sRelatedSkills;
    }

    public String getSBenefits() {
        return sBenefits;
    }

    public void setSBenefits(String sBenefits) {
        this.sBenefits = sBenefits;
    }

    public String getSWorkTime() {
        return sWorkTime;
    }

    public void setSWorkTime(String sWorkTime) {
        this.sWorkTime = sWorkTime;
    }

    public LocalDate getDPostedDate() {
        return dPostedDate;
    }

    public void setDPostedDate(LocalDate dPostedDate) {
        this.dPostedDate = dPostedDate;
    }

    public LocalDate getDDeadline() {
        return dDeadline;
    }

    public void setDDeadline(LocalDate dDeadline) {
        this.dDeadline = dDeadline;
    }

    public Boolean getBStatus() {
        return bStatus;
    }

    public void setBStatus(Boolean bStatus) {
        this.bStatus = bStatus;
    }

    public Set<Application> getApplicationSet() {
        return applicationSet;
    }

    public void setApplicationSet(Set<Application> applicationSet) {
        this.applicationSet = applicationSet;
    }

    public Employer getIEmployerId() {
        return iEmployerId;
    }

    public void setIEmployerId(Employer iEmployerId) {
        this.iEmployerId = iEmployerId;
    }

    public Joblevel getILevelId() {
        return iLevelId;
    }

    public void setILevelId(Joblevel iLevelId) {
        this.iLevelId = iLevelId;
    }

    public Location getILocationId() {
        return iLocationId;
    }

    public void setILocationId(Location iLocationId) {
        this.iLocationId = iLocationId;
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
        hash += (iJobPostId != null ? iJobPostId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Job)) {
            return false;
        }
        Job other = (Job) object;
        if ((this.iJobPostId == null && other.iJobPostId != null) || (this.iJobPostId != null && !this.iJobPostId.equals(other.iJobPostId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Job[ iJobPostId=" + iJobPostId + " ]";
    }
    
}
