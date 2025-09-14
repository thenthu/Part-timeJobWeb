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
public class FollowcompanyPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "iCandidateId")
    private int iCandidateId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iEmployerId")
    private int iEmployerId;

    public FollowcompanyPK() {
    }

    public FollowcompanyPK(int iCandidateId, int iEmployerId) {
        this.iCandidateId = iCandidateId;
        this.iEmployerId = iEmployerId;
    }

    public int getICandidateId() {
        return iCandidateId;
    }

    public void setICandidateId(int iCandidateId) {
        this.iCandidateId = iCandidateId;
    }

    public int getIEmployerId() {
        return iEmployerId;
    }

    public void setIEmployerId(int iEmployerId) {
        this.iEmployerId = iEmployerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) iCandidateId;
        hash += (int) iEmployerId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FollowcompanyPK)) {
            return false;
        }
        FollowcompanyPK other = (FollowcompanyPK) object;
        if (this.iCandidateId != other.iCandidateId) {
            return false;
        }
        if (this.iEmployerId != other.iEmployerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.FollowcompanyPK[ iCandidateId=" + iCandidateId + ", iEmployerId=" + iEmployerId + " ]";
    }
    
}
