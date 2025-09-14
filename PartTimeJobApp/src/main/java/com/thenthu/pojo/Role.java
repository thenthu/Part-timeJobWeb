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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author this pc
 */
@Entity
@Table(name = "role")
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
    @NamedQuery(name = "Role.findByIRoleId", query = "SELECT r FROM Role r WHERE r.iRoleId = :iRoleId"),
    @NamedQuery(name = "Role.findBySRoleName", query = "SELECT r FROM Role r WHERE r.sRoleName = :sRoleName")})
public class Role implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "sRoleName")
    private String sRoleName;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iRoleId")
    private Integer iRoleId;
    @OneToMany(mappedBy = "iRoleId")
    private Set<Account> accountSet;

    public Role() {
    }

    public Role(Integer iRoleId) {
        this.iRoleId = iRoleId;
    }

    public Role(Integer iRoleId, String sRoleName) {
        this.iRoleId = iRoleId;
        this.sRoleName = sRoleName;
    }

    public Integer getIRoleId() {
        return iRoleId;
    }

    public void setIRoleId(Integer iRoleId) {
        this.iRoleId = iRoleId;
    }


    public Set<Account> getAccountSet() {
        return accountSet;
    }

    public void setAccountSet(Set<Account> accountSet) {
        this.accountSet = accountSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iRoleId != null ? iRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.iRoleId == null && other.iRoleId != null) || (this.iRoleId != null && !this.iRoleId.equals(other.iRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Role[ iRoleId=" + iRoleId + " ]";
    }

    public String getSRoleName() {
        return sRoleName;
    }

    public void setSRoleName(String sRoleName) {
        this.sRoleName = sRoleName;
    }
    
}
