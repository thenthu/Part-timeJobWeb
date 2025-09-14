/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

/**
 *
 * @author this pc
 */
public class ReviewDTO {
    private Integer reviewId;
    private Integer rating;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    private AccountDTO fromAccount;
    private AccountDTO toAccount;

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public AccountDTO getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(AccountDTO fromAccount) {
        this.fromAccount = fromAccount;
    }

    public AccountDTO getToAccount() {
        return toAccount;
    }

    public void setToAccount(AccountDTO toAccount) {
        this.toAccount = toAccount;
    }
    
}
