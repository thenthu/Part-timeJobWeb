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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author this pc
 */
@Entity
@Table(name = "employer")
@NamedQueries({
    @NamedQuery(name = "Employer.findAll", query = "SELECT e FROM Employer e"),
    @NamedQuery(name = "Employer.findByIEmployerId", query = "SELECT e FROM Employer e WHERE e.iEmployerId = :iEmployerId"),
    @NamedQuery(name = "Employer.findBySCompanyName", query = "SELECT e FROM Employer e WHERE e.sCompanyName = :sCompanyName"),
    @NamedQuery(name = "Employer.findBySRepresentativeName", query = "SELECT e FROM Employer e WHERE e.sRepresentativeName = :sRepresentativeName"),
    @NamedQuery(name = "Employer.findBySRepresentativeTitle", query = "SELECT e FROM Employer e WHERE e.sRepresentativeTitle = :sRepresentativeTitle"),
    @NamedQuery(name = "Employer.findBySAvatar", query = "SELECT e FROM Employer e WHERE e.sAvatar = :sAvatar"),
    @NamedQuery(name = "Employer.findBySTaxCode", query = "SELECT e FROM Employer e WHERE e.sTaxCode = :sTaxCode"),
    @NamedQuery(name = "Employer.findBySWorkEnvImg1", query = "SELECT e FROM Employer e WHERE e.sWorkEnvImg1 = :sWorkEnvImg1"),
    @NamedQuery(name = "Employer.findBySWorkEnvImg2", query = "SELECT e FROM Employer e WHERE e.sWorkEnvImg2 = :sWorkEnvImg2"),
    @NamedQuery(name = "Employer.findBySWorkEnvImg3", query = "SELECT e FROM Employer e WHERE e.sWorkEnvImg3 = :sWorkEnvImg3"),
    @NamedQuery(name = "Employer.findByBVerified", query = "SELECT e FROM Employer e WHERE e.bVerified = :bVerified"),
    @NamedQuery(name = "Employer.findBySVerifyDoc", query = "SELECT e FROM Employer e WHERE e.sVerifyDoc = :sVerifyDoc")})
public class Employer implements Serializable {

    @Size(max = 100)
    @Column(name = "sCompanyName")
    private String sCompanyName;
    @Size(max = 100)
    @Column(name = "sRepresentativeName")
    private String sRepresentativeName;
    @Size(max = 50)
    @Column(name = "sRepresentativeTitle")
    private String sRepresentativeTitle;
    @Size(max = 255)
    @Column(name = "sAvatar")
    private String sAvatar;
    @Size(max = 20)
    @Column(name = "sTaxCode")
    private String sTaxCode;
    @Size(max = 255)
    @Column(name = "sWorkEnvImg1")
    private String sWorkEnvImg1;
    @Size(max = 255)
    @Column(name = "sWorkEnvImg2")
    private String sWorkEnvImg2;
    @Size(max = 255)
    @Column(name = "sWorkEnvImg3")
    private String sWorkEnvImg3;
    @Size(max = 255)
    @Column(name = "sVerifyDoc")
    private String sVerifyDoc;
    @OneToOne
    @JoinColumn(name = "iAccountId")
    private Account account;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iEmployerId")
    private Integer iEmployerId;
    @Column(name = "bVerified")
    private Boolean bVerified;
    @OneToMany(mappedBy = "iEmployerId")
    private Set<Job> jobSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employer")
    private Set<Followcompany> followcompanySet;

    @PrePersist
    protected void onCreate() {
        if (bVerified == null) {
            bVerified = false;
        }
    }
    
    public Employer() {
    }

    public Employer(Integer iEmployerId) {
        this.iEmployerId = iEmployerId;
    }

    public Integer getIEmployerId() {
        return iEmployerId;
    }

    public void setIEmployerId(Integer iEmployerId) {
        this.iEmployerId = iEmployerId;
    }

    public Boolean getBVerified() {
        return bVerified;
    }

    public void setBVerified(Boolean bVerified) {
        this.bVerified = bVerified;
    }

    public Set<Job> getJobSet() {
        return jobSet;
    }

    public void setJobSet(Set<Job> jobSet) {
        this.jobSet = jobSet;
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
        hash += (iEmployerId != null ? iEmployerId.hashCode() : 0);
        return hash;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employer)) {
            return false;
        }
        Employer other = (Employer) object;
        if ((this.iEmployerId == null && other.iEmployerId != null) || (this.iEmployerId != null && !this.iEmployerId.equals(other.iEmployerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Employer[ iEmployerId=" + iEmployerId + " ]";
    }

    public String getSCompanyName() {
        return sCompanyName;
    }

    public void setSCompanyName(String sCompanyName) {
        this.sCompanyName = sCompanyName;
    }

    public String getSRepresentativeName() {
        return sRepresentativeName;
    }

    public void setSRepresentativeName(String sRepresentativeName) {
        this.sRepresentativeName = sRepresentativeName;
    }

    public String getSRepresentativeTitle() {
        return sRepresentativeTitle;
    }

    public void setSRepresentativeTitle(String sRepresentativeTitle) {
        this.sRepresentativeTitle = sRepresentativeTitle;
    }

    public String getSAvatar() {
        return sAvatar;
    }

    public void setSAvatar(String sAvatar) {
        this.sAvatar = sAvatar;
    }

    public String getSTaxCode() {
        return sTaxCode;
    }

    public void setSTaxCode(String sTaxCode) {
        this.sTaxCode = sTaxCode;
    }

    public String getSWorkEnvImg1() {
        return sWorkEnvImg1;
    }

    public void setSWorkEnvImg1(String sWorkEnvImg1) {
        this.sWorkEnvImg1 = sWorkEnvImg1;
    }

    public String getSWorkEnvImg2() {
        return sWorkEnvImg2;
    }

    public void setSWorkEnvImg2(String sWorkEnvImg2) {
        this.sWorkEnvImg2 = sWorkEnvImg2;
    }

    public String getSWorkEnvImg3() {
        return sWorkEnvImg3;
    }

    public void setSWorkEnvImg3(String sWorkEnvImg3) {
        this.sWorkEnvImg3 = sWorkEnvImg3;
    }

    public String getSVerifyDoc() {
        return sVerifyDoc;
    }

    public void setSVerifyDoc(String sVerifyDoc) {
        this.sVerifyDoc = sVerifyDoc;
    }

}
