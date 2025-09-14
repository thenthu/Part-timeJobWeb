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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author this pc
 */
@Entity
@Table(name = "notification")
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
    @NamedQuery(name = "Notification.findByINotifId", query = "SELECT n FROM Notification n WHERE n.iNotifId = :iNotifId"),
    @NamedQuery(name = "Notification.findByBRead", query = "SELECT n FROM Notification n WHERE n.bRead = :bRead"),
    @NamedQuery(name = "Notification.findByDCreatedAt", query = "SELECT n FROM Notification n WHERE n.dCreatedAt = :dCreatedAt")})
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iNotifId")
    private Integer iNotifId;
    @Lob
    @Size(max = 65535)
    @Column(name = "sContent")
    private String sContent;
    @Column(name = "bRead")
    private Boolean bRead;
    @Column(name = "dCreatedAt")
    private LocalDate dCreatedAt;
    @JoinColumn(name = "iAccountId", referencedColumnName = "iAccountId")
    @ManyToOne
    private Account iAccountId;
    
    @PrePersist
    protected void onCreate() {
        if (dCreatedAt == null) {
            setDCreatedAt(LocalDate.now());
        }
        if (bRead == null) {
            bRead = false;
        }
    }

    public Notification() {
    }

    public Notification(Integer iNotifId) {
        this.iNotifId = iNotifId;
    }

    public Integer getINotifId() {
        return iNotifId;
    }

    public void setINotifId(Integer iNotifId) {
        this.iNotifId = iNotifId;
    }

    public String getSContent() {
        return sContent;
    }

    public void setSContent(String sContent) {
        this.sContent = sContent;
    }

    public Boolean getBRead() {
        return bRead;
    }

    public void setBRead(Boolean bRead) {
        this.bRead = bRead;
    }

    public LocalDate getDCreatedAt() {
        return dCreatedAt;
    }

    public void setDCreatedAt(LocalDate dCreatedAt) {
        this.dCreatedAt = dCreatedAt;
    }

    public Account getIAccountId() {
        return iAccountId;
    }

    public void setIAccountId(Account iAccountId) {
        this.iAccountId = iAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iNotifId != null ? iNotifId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.iNotifId == null && other.iNotifId != null) || (this.iNotifId != null && !this.iNotifId.equals(other.iNotifId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Notification[ iNotifId=" + iNotifId + " ]";
    }
    
}
