/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Review;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface ReviewRepository {
    List<Review> getReviewsWrittenByUser(String username);
    List<Review> getReviewsForUser(Map<String, String> params, String username);
    Review getReviewById(int id);
    
    Review findByFromAccountAndToAccount(String fromAcc, String toAcc);
    Review addOrUpdateReview(Review r);
    void deleteReview(int id);
}
