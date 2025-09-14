package com.thenthu.security;

import com.thenthu.dto.ReviewDTO;
import com.thenthu.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author this pc
 */
@Component("reviewSecurity")
public class ReviewSecurity {
    @Autowired
    private ReviewService reviewService;
    
    public boolean isOwner(int id, String principal) {
        ReviewDTO rv = reviewService.getReviewById(id);
        String username = principal;
        
        return rv.getFromAccount().getUsername().equals(username);
    }
}
