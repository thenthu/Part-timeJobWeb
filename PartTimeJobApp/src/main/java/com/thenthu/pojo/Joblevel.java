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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author this pc
 */
@Entity
@Table(name = "joblevel")
@NamedQueries({
    @NamedQuery(name = "Joblevel.findAll", query = "SELECT j FROM Joblevel j"),
    @NamedQuery(name = "Joblevel.findByILevelId", query = "SELECT j FROM Joblevel j WHERE j.iLevelId = :iLevelId"),
    @NamedQuery(name = "Joblevel.findBySLevelName", query = "SELECT j FROM Joblevel j WHERE j.sLevelName = :sLevelName")})
public class Joblevel implements Serializable {

    @Size(max = 100)
    @Column(name = "sLevelName")
    private String sLevelName;
    @OneToMany(mappedBy = "iLevelId")
    private Set<Resume> resumeSet;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iLevelId")
    private Integer iLevelId;
    @OneToMany(mappedBy = "iLevelId")
    private Set<Job> jobSet;

    public Joblevel() {
    }

    public Joblevel(Integer iLevelId) {
        this.iLevelId = iLevelId;
    }

    public Integer getILevelId() {
        return iLevelId;
    }

    public void setILevelId(Integer iLevelId) {
        this.iLevelId = iLevelId;
    }


    public Set<Job> getJobSet() {
        return jobSet;
    }

    public void setJobSet(Set<Job> jobSet) {
        this.jobSet = jobSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iLevelId != null ? iLevelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joblevel)) {
            return false;
        }
        Joblevel other = (Joblevel) object;
        if ((this.iLevelId == null && other.iLevelId != null) || (this.iLevelId != null && !this.iLevelId.equals(other.iLevelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Joblevel[ iLevelId=" + iLevelId + " ]";
    }

    public String getSLevelName() {
        return sLevelName;
    }

    public void setSLevelName(String sLevelName) {
        this.sLevelName = sLevelName;
    }

    public Set<Resume> getResumeSet() {
        return resumeSet;
    }

    public void setResumeSet(Set<Resume> resumeSet) {
        this.resumeSet = resumeSet;
    }
    
}
