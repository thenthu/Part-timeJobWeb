/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.ReviewDTO;
import com.thenthu.pojo.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(
    componentModel = "spring",
    uses = { AccountMapper.class }
)
public interface ReviewMapper {
    @Mapping(source = "IReviewId", target = "reviewId")
    @Mapping(source = "IRating", target = "rating")
    @Mapping(source = "SComment", target = "comment")
    @Mapping(source = "DCreatedAt", target = "createdAt")
    @Mapping(source = "IFromAccountId", target = "fromAccount")
    @Mapping(source = "IToAccountId", target = "toAccount")
    ReviewDTO toDTO(Review review);

    // DTO -> Entity
    @Mapping(source = "reviewId", target = "IReviewId")
    @Mapping(source = "rating", target = "IRating")
    @Mapping(source = "comment", target = "SComment")
    @Mapping(source = "createdAt", target = "DCreatedAt")
    @Mapping(source = "fromAccount", target = "IFromAccountId")
    @Mapping(source = "toAccount", target = "IToAccountId")
    Review toEntity(ReviewDTO reviewDTO);
}
