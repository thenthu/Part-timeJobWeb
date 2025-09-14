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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author this pc
 */
@Entity
@Table(name = "account")
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByIAccountId", query = "SELECT a FROM Account a WHERE a.iAccountId = :iAccountId"),
    @NamedQuery(name = "Account.findBySEmail", query = "SELECT a FROM Account a WHERE a.sEmail = :sEmail"),
    @NamedQuery(name = "Account.findBySUsername", query = "SELECT a FROM Account a WHERE a.sUsername = :sUsername"),
    @NamedQuery(name = "Account.findBySPassword", query = "SELECT a FROM Account a WHERE a.sPassword = :sPassword"),
    @NamedQuery(name = "Account.findByDCreatedAt", query = "SELECT a FROM Account a WHERE a.dCreatedAt = :dCreatedAt"),
    @NamedQuery(name = "Account.findByBStatus", query = "SELECT a FROM Account a WHERE a.bStatus = :bStatus")})
public class Account implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "sEmail")
    private String sEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "sPassword")
    private String sPassword;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "sUsername")
    private String sUsername;
    @OneToMany(mappedBy = "account")
    private Set<Candidate> candidateSet;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iAccountId")
    private Integer iAccountId;
    @Column(name = "dCreatedAt")
    private LocalDate dCreatedAt;
    @Column(name = "bStatus")
    private Boolean bStatus;
    @OneToMany(mappedBy = "iAccountId")
    private Set<Notification> notificationSet;
    @OneToMany(mappedBy = "iFromAccountId")
    private Set<Review> reviewSet;
    @OneToMany(mappedBy = "iToAccountId")
    private Set<Review> reviewSet1;
    @JoinColumn(name = "iRoleId", referencedColumnName = "iRoleId")
    @ManyToOne
    private Role iRoleId;
    @OneToOne(mappedBy = "account")
    private Candidate candidate;

    @OneToOne(mappedBy = "account")
    private Employer employer;

    @PrePersist
    protected void onCreate() {
        if (dCreatedAt == null) {
            setDCreatedAt(LocalDate.now());
        }
        if (bStatus == null)
            bStatus = true;
    }
    
    public Account() {
    }

    public Account(Integer iAccountId) {
        this.iAccountId = iAccountId;
    }

    public Account(Integer iAccountId, String sEmail, String sPassword) {
        this.iAccountId = iAccountId;
        this.sEmail = sEmail;
        this.sPassword = sPassword;
    }

    public Integer getIAccountId() {
        return iAccountId;
    }

    public void setIAccountId(Integer iAccountId) {
        this.iAccountId = iAccountId;
    }


    public LocalDate getDCreatedAt() {
        return dCreatedAt;
    }

    public void setDCreatedAt(LocalDate dCreatedAt) {
        this.dCreatedAt = dCreatedAt;
    }

    public Boolean getBStatus() {
        return bStatus;
    }

    public void setBStatus(Boolean bStatus) {
        this.bStatus = bStatus;
    }

    public Set<Notification> getNotificationSet() {
        return notificationSet;
    }

    public void setNotificationSet(Set<Notification> notificationSet) {
        this.notificationSet = notificationSet;
    }

    public Set<Review> getReviewSet() {
        return reviewSet;
    }

    public void setReviewSet(Set<Review> reviewSet) {
        this.reviewSet = reviewSet;
    }

    public Set<Review> getReviewSet1() {
        return reviewSet1;
    }

    public void setReviewSet1(Set<Review> reviewSet1) {
        this.reviewSet1 = reviewSet1;
    }

    public Role getIRoleId() {
        return iRoleId;
    }

    public void setIRoleId(Role iRoleId) {
        this.iRoleId = iRoleId;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iAccountId != null ? iAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.iAccountId == null && other.iAccountId != null) || (this.iAccountId != null && !this.iAccountId.equals(other.iAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Account[ iAccountId=" + iAccountId + " ]";
    }


    public Set<Candidate> getCandidateSet() {
        return candidateSet;
    }

    public void setCandidateSet(Set<Candidate> candidateSet) {
        this.candidateSet = candidateSet;
    }

    public String getSEmail() {
        return sEmail;
    }

    public void setSEmail(String sEmail) {
        this.sEmail = sEmail;
    }

    public String getSPassword() {
        return sPassword;
    }

    public void setSPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public String getSUsername() {
        return sUsername;
    }

    public void setSUsername(String sUsername) {
        this.sUsername = sUsername;
    }
    
}
