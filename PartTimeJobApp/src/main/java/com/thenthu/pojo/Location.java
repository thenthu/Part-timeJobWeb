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
@Table(name = "location")
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l"),
    @NamedQuery(name = "Location.findByILocationId", query = "SELECT l FROM Location l WHERE l.iLocationId = :iLocationId"),
    @NamedQuery(name = "Location.findBySLocationName", query = "SELECT l FROM Location l WHERE l.sLocationName = :sLocationName")})
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iLocationId")
    private Integer iLocationId;
    @Size(max = 100)
    @Column(name = "sLocationName")
    private String sLocationName;
    @OneToMany(mappedBy = "iLocationId")
    private Set<Job> jobSet;

    public Location() {
    }

    public Location(Integer iLocationId) {
        this.iLocationId = iLocationId;
    }

    public Integer getILocationId() {
        return iLocationId;
    }

    public void setILocationId(Integer iLocationId) {
        this.iLocationId = iLocationId;
    }

    public String getSLocationName() {
        return sLocationName;
    }

    public void setSLocationName(String sLocationName) {
        this.sLocationName = sLocationName;
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
        hash += (iLocationId != null ? iLocationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.iLocationId == null && other.iLocationId != null) || (this.iLocationId != null && !this.iLocationId.equals(other.iLocationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Location[ iLocationId=" + iLocationId + " ]";
    }
    
}
