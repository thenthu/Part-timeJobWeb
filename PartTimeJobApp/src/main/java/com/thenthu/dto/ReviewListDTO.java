/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.dto;

import java.util.List;

/**
 *
 * @author this pc
 */
public class ReviewListDTO {
    private ReviewDTO myReview;
    private List<ReviewDTO> reviews;
    
    public ReviewListDTO(ReviewDTO myReview, List<ReviewDTO> reviews) {
        this.myReview = myReview;
        this.reviews = reviews;
    }

    public ReviewDTO getMyReview() {
        return myReview;
    }

    public void setMyReview(ReviewDTO myReview) {
        this.myReview = myReview;
    }

    public List<ReviewDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewDTO> reviews) {
        this.reviews = reviews;
    }
    
    
}
