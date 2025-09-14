/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.pojo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author this pc
 */
@Embeddable
public class ApplicationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "iCandidateId")
    private int iCandidateId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iJobPostId")
    private int iJobPostId;

    public ApplicationPK() {
    }

    public ApplicationPK(int iCandidateId, int iJobPostId) {
        this.iCandidateId = iCandidateId;
        this.iJobPostId = iJobPostId;
    }

    public int getICandidateId() {
        return iCandidateId;
    }

    public void setICandidateId(int iCandidateId) {
        this.iCandidateId = iCandidateId;
    }

    public int getIJobPostId() {
        return iJobPostId;
    }

    public void setIJobPostId(int iJobPostId) {
        this.iJobPostId = iJobPostId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iCandidateId;
        hash += (int) iJobPostId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApplicationPK)) {
            return false;
        }
        ApplicationPK other = (ApplicationPK) object;
        if (this.iCandidateId != other.iCandidateId) {
            return false;
        }
        if (this.iJobPostId != other.iJobPostId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.ApplicationPK[ iCandidateId=" + iCandidateId + ", iJobPostId=" + iJobPostId + " ]";
    }
    
}
