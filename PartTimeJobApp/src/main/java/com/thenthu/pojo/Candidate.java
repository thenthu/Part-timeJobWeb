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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "candidate")
@NamedQueries({
    @NamedQuery(name = "Candidate.findAll", query = "SELECT c FROM Candidate c"),
    @NamedQuery(name = "Candidate.findByICandidateId", query = "SELECT c FROM Candidate c WHERE c.iCandidateId = :iCandidateId"),
    @NamedQuery(name = "Candidate.findBySFullName", query = "SELECT c FROM Candidate c WHERE c.sFullName = :sFullName"),
    @NamedQuery(name = "Candidate.findBySPhone", query = "SELECT c FROM Candidate c WHERE c.sPhone = :sPhone"),
    @NamedQuery(name = "Candidate.findBySGender", query = "SELECT c FROM Candidate c WHERE c.sGender = :sGender"),
    @NamedQuery(name = "Candidate.findByDBirthDate", query = "SELECT c FROM Candidate c WHERE c.dBirthDate = :dBirthDate"),
    @NamedQuery(name = "Candidate.findBySAvatar", query = "SELECT c FROM Candidate c WHERE c.sAvatar = :sAvatar"),
    @NamedQuery(name = "Candidate.findBySAddress", query = "SELECT c FROM Candidate c WHERE c.sAddress = :sAddress")})
public class Candidate implements Serializable {

    @Size(max = 100)
    @Column(name = "sFullName")
    private String sFullName;
    @Size(max = 20)
    @Column(name = "sPhone")
    private String sPhone;
    @Size(max = 10)
    @Column(name = "sGender")
    private String sGender;
    @Size(max = 255)
    @Column(name = "sAvatar")
    private String sAvatar;
    @Size(max = 255)
    @Column(name = "sAddress")
    private String sAddress;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iCandidateId")
    private Integer iCandidateId;
    @Column(name = "dBirthDate")
    private LocalDate dBirthDate;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "iAccountId")
    private Account account;
    @OneToMany(mappedBy = "iCandidateId")
    private Set<Resume> resumeSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
    private Set<Application> applicationSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
    private Set<Followcompany> followcompanySet;

    public Candidate() {
    }

    public Candidate(Integer iCandidateId) {
        this.iCandidateId = iCandidateId;
    }

    public Integer getICandidateId() {
        return iCandidateId;
    }

    public void setICandidateId(Integer iCandidateId) {
        this.iCandidateId = iCandidateId;
    }

    public LocalDate getDBirthDate() {
        return dBirthDate;
    }

    public void setDBirthDate(LocalDate dBirthDate) {
        this.dBirthDate = dBirthDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Resume> getResumeSet() {
        return resumeSet;
    }

    public void setResumeSet(Set<Resume> resumeSet) {
        this.resumeSet = resumeSet;
    }

    public Set<Application> getApplicationSet() {
        return applicationSet;
    }

    public void setApplicationSet(Set<Application> applicationSet) {
        this.applicationSet = applicationSet;
    }

    public Set<Followcompany> getFollowcompanySet() {
        return followcompanySet;
    }

    public void setFollowcompanySet(Set<Followcompany> followcompanySet) {
        this.followcompanySet = followcompanySet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iCandidateId != null ? iCandidateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Candidate)) {
            return false;
        }
        Candidate other = (Candidate) object;
        if ((this.iCandidateId == null && other.iCandidateId != null) || (this.iCandidateId != null && !this.iCandidateId.equals(other.iCandidateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Candidate[ iCandidateId=" + iCandidateId + " ]";
    }

    public String getSFullName() {
        return sFullName;
    }

    public void setSFullName(String sFullName) {
        this.sFullName = sFullName;
    }

    public String getSPhone() {
        return sPhone;
    }

    public void setSPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    public String getSGender() {
        return sGender;
    }

    public void setSGender(String sGender) {
        this.sGender = sGender;
    }

    public String getSAvatar() {
        return sAvatar;
    }

    public void setSAvatar(String sAvatar) {
        this.sAvatar = sAvatar;
    }

    public String getSAddress() {
        return sAddress;
    }

    public void setSAddress(String sAddress) {
        this.sAddress = sAddress;
    }

}
