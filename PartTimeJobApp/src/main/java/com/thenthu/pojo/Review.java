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
@Table(name = "review")
@NamedQueries({
    @NamedQuery(name = "Review.findAll", query = "SELECT r FROM Review r"),
    @NamedQuery(name = "Review.findByIReviewId", query = "SELECT r FROM Review r WHERE r.iReviewId = :iReviewId"),
    @NamedQuery(name = "Review.findByIRating", query = "SELECT r FROM Review r WHERE r.iRating = :iRating"),
    @NamedQuery(name = "Review.findByDCreatedAt", query = "SELECT r FROM Review r WHERE r.dCreatedAt = :dCreatedAt")})
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iReviewId")
    private Integer iReviewId;
    @Column(name = "iRating")
    private Integer iRating;
    @Lob
    @Size(max = 65535)
    @Column(name = "sComment")
    private String sComment;
    @Column(name = "dCreatedAt")
    private LocalDate dCreatedAt;
    @JoinColumn(name = "iFromAccountId", referencedColumnName = "iAccountId")
    @ManyToOne
    private Account iFromAccountId;
    @JoinColumn(name = "iToAccountId", referencedColumnName = "iAccountId")
    @ManyToOne
    private Account iToAccountId;

    @PrePersist
    protected void onCreate() {
        if (dCreatedAt == null) {
            setDCreatedAt(LocalDate.now());
        }
    }

    public Review() {
    }

    public Review(Integer iReviewId) {
        this.iReviewId = iReviewId;
    }

    public Integer getIReviewId() {
        return iReviewId;
    }

    public void setIReviewId(Integer iReviewId) {
        this.iReviewId = iReviewId;
    }

    public Integer getIRating() {
        return iRating;
    }

    public void setIRating(Integer iRating) {
        this.iRating = iRating;
    }

    public String getSComment() {
        return sComment;
    }

    public void setSComment(String sComment) {
        this.sComment = sComment;
    }

    public LocalDate getDCreatedAt() {
        return dCreatedAt;
    }

    public void setDCreatedAt(LocalDate dCreatedAt) {
        this.dCreatedAt = dCreatedAt;
    }

    public Account getIFromAccountId() {
        return iFromAccountId;
    }

    public void setIFromAccountId(Account iFromAccountId) {
        this.iFromAccountId = iFromAccountId;
    }

    public Account getIToAccountId() {
        return iToAccountId;
    }

    public void setIToAccountId(Account iToAccountId) {
        this.iToAccountId = iToAccountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iReviewId != null ? iReviewId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Review)) {
            return false;
        }
        Review other = (Review) object;
        if ((this.iReviewId == null && other.iReviewId != null) || (this.iReviewId != null && !this.iReviewId.equals(other.iReviewId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.thenthu.configs.Review[ iReviewId=" + iReviewId + " ]";
    }

}
