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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author this pc
 */
@Entity
@Table(name = "followcompany")
@NamedQueries({
    @NamedQuery(name = "Followcompany.findAll", query = "SELECT f FROM Followcompany f"),
    @NamedQuery(name = "Followcompany.findByICandidateId", query = "SELECT f FROM Followcompany f WHERE f.followcompanyPK.iCandidateId = :iCandidateId"),
    @NamedQuery(name = "Followcompany.findByIEmployerId", query = "SELECT f FROM Followcompany f WHERE f.followcompanyPK.iEmployerId = :iEmployerId"),
    @NamedQuery(name = "Followcompany.findByDFollowedAt", query = "SELECT f FROM Followcompany f WHERE f.dFollowedAt = :dFollowedAt")})
public class Followcompany implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FollowcompanyPK followcompanyPK;
    @Column(name = "dFollowedAt")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dFollowedAt;
    @JoinColumn(name = "iCandidateId", referencedColumnName = "iCandidateId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Candidate candidate;
    @JoinColumn(name = "iEmployerId", referencedColumnName = "iEmployerId", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Employer employer;
    
    @PrePersist
    protected void onCreate() {
        if (dFollowedAt == null) {
            setDFollowedAt(new Date());
        }
    }

    public Followcompany() {
    }

    public Followcompany(FollowcompanyPK followcompanyPK) {
        this.followcompanyPK = followcompanyPK;
    }

    public Followcompany(int iCandidateId, int iEmployerId) {
        this.followcompanyPK = new FollowcompanyPK(iCandidateId, iEmployerId);
    }

    public FollowcompanyPK getFollowcompanyPK() {
        return followcompanyPK;
    }

    public void setFollowcompanyPK(FollowcompanyPK followcompanyPK) {
        this.followcompanyPK = followcompanyPK;
    }

    public Date getDFollowedAt() {
        return dFollowedAt;
    }

    public void setDFollowedAt(Date dFollowedAt) {
        this.dFollowedAt = dFollowedAt;
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
        hash += (followcompanyPK != null ? followcompanyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Followcompany)) {
            return false;
        }
        Followcompany other = (Followcompany) object;
        if ((this.followcompanyPK == null && other.followcompanyPK != null) || (this.followcompanyPK != null && !this.followcompanyPK.equals(other.followcompanyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Followcompany[ followcompanyPK=" + followcompanyPK + " ]";
    }
    
}
