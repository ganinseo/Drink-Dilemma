package com.example.DrinkDilemma.service;

import com.example.DrinkDilemma.dto.ReviewDTO;

import java.util.List;

public interface ReviewService {
    List<ReviewDTO> getReviewsByCafeId(long cafeId);
    void saveReview(ReviewDTO reviewDTO);
    void deleteReview(long reviewId, long cafeId);
    void editReview(ReviewDTO reviewDTO);
    ReviewDTO findById(long id);
}