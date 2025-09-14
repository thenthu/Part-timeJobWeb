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
@Table(name = "major")
@NamedQueries({
    @NamedQuery(name = "Major.findAll", query = "SELECT m FROM Major m"),
    @NamedQuery(name = "Major.findByIMajorId", query = "SELECT m FROM Major m WHERE m.iMajorId = :iMajorId"),
    @NamedQuery(name = "Major.findBySMajorName", query = "SELECT m FROM Major m WHERE m.sMajorName = :sMajorName")})
public class Major implements Serializable {

    @Size(max = 100)
    @Column(name = "sMajorName")
    private String sMajorName;
    @OneToMany(mappedBy = "iMajorId")
    private Set<Resume> resumeSet;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iMajorId")
    private Integer iMajorId;
    @OneToMany(mappedBy = "iMajorId")
    private Set<Job> jobSet;

    public Major() {
    }

    public Major(Integer iMajorId) {
        this.iMajorId = iMajorId;
    }

    public Integer getIMajorId() {
        return iMajorId;
    }

    public void setIMajorId(Integer iMajorId) {
        this.iMajorId = iMajorId;
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
        hash += (iMajorId != null ? iMajorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Major)) {
            return false;
        }
        Major other = (Major) object;
        if ((this.iMajorId == null && other.iMajorId != null) || (this.iMajorId != null && !this.iMajorId.equals(other.iMajorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Major[ iMajorId=" + iMajorId + " ]";
    }

    public String getSMajorName() {
        return sMajorName;
    }

    public void setSMajorName(String sMajorName) {
        this.sMajorName = sMajorName;
    }

    public Set<Resume> getResumeSet() {
        return resumeSet;
    }

    public void setResumeSet(Set<Resume> resumeSet) {
        this.resumeSet = resumeSet;
    }
    
}
