/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.ReviewDTO;
import com.thenthu.dto.ReviewListDTO;
import com.thenthu.mapper.ReviewMapper;
import com.thenthu.pojo.Account;
import com.thenthu.pojo.Review;
import com.thenthu.repositories.AccountRepository;
import com.thenthu.repositories.ReviewRepository;
import com.thenthu.services.ReviewService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private AccountRepository accountRepo;

    @Override
    public List<ReviewDTO> getReviewsByUser(String username) {
        List<Review> reviews = reviewRepo.getReviewsWrittenByUser(username);
        return reviews.stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsForUser(Map<String, String> params, String username) {
        List<Review> reviews = reviewRepo.getReviewsForUser(params, username);
        return reviews.stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO getReviewById(int id) {
        Review r = this.reviewRepo.getReviewById(id);
        return reviewMapper.toDTO(r);
    }

    @Override
    public ReviewDTO addOrUpdateReview(ReviewDTO r, String from, String to) {
        if(from.equals(to)) {
            throw new RuntimeException();
        }
        
        Account fromAcc = accountRepo.getAccountByUsername(from);
        Account toAcc = accountRepo.getAccountByUsername(to);

        Review review;
        if (r.getReviewId() != null) {
            review = reviewRepo.getReviewById(r.getReviewId());
            if (review == null) {
                throw new RuntimeException("Không tìm thấy review để cập nhật!");
            }
        } else {
            Review existed = reviewRepo.findByFromAccountAndToAccount(from, to);
            if (existed != null) {
                throw new RuntimeException();
            }
            review = new Review();
            review.setIFromAccountId(fromAcc);
            review.setIToAccountId(toAcc);
        }
        if (r.getRating() != null) {
            review.setIRating(r.getRating());
        }
        if (r.getComment() != null) {
            review.setSComment(r.getComment());
        }

        reviewRepo.addOrUpdateReview(review);

        return reviewMapper.toDTO(review);
    }

    @Override
    public void deleteReview(int id) {
        if (reviewRepo.getReviewById(id) != null) {
            this.reviewRepo.deleteReview(id);
        }
    }

    @Override
    public ReviewListDTO getReviews(Map<String, String> params, String username, String principal) {
        List<Review> reviews = reviewRepo.getReviewsForUser(params, username);

        Review myReview = null;
        List<Review> otherReviews = new ArrayList<>();

        for (Review review : reviews) {
            if (principal != null && review.getIFromAccountId().getSUsername().equals(principal)) {
                myReview = review;
            } else {
                otherReviews.add(review);
            }
        }

        ReviewDTO myReviewDTO = myReview != null ? reviewMapper.toDTO(myReview) : null;
        List<ReviewDTO> otherReviewDTOs = otherReviews.stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());

        return new ReviewListDTO(myReviewDTO, otherReviewDTOs);
    }
}
