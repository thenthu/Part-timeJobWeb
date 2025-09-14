/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.ReviewDTO;
import com.thenthu.dto.ReviewListDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface ReviewService {
    List<ReviewDTO> getReviewsByUser(String username);
    List<ReviewDTO> getReviewsForUser(Map<String, String> params, String username);
    ReviewDTO getReviewById(int id);
    
    ReviewListDTO getReviews(Map<String, String> params, String username, String principal);
    ReviewDTO addOrUpdateReview(ReviewDTO r, String from, String to);
    void deleteReview(int id);
}
