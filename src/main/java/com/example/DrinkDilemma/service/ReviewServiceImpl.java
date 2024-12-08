package com.example.DrinkDilemma.service;

import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.domain.Review;
import com.example.DrinkDilemma.dto.ReviewDTO;
import com.example.DrinkDilemma.repository.CafeRepository;
import com.example.DrinkDilemma.repository.ReviewRepository;
import com.example.DrinkDilemma.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;

    @Override
    public List<ReviewDTO> getReviewsByCafeId(long cafeId) {
        return reviewRepository.findAllByCafe_Idx(cafeId).stream()
                .map(review -> ReviewDTO.builder()
                        .idx(review.getIdx())
                        .userName(review.getUserName())
                        .comment(review.getComment())
                        .rating(review.getRating())
                        .cafeId(review.getCafe().getIdx())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void saveReview(ReviewDTO reviewDTO) {
        Cafe cafe = cafeRepository.findById(reviewDTO.getCafeId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Cafe ID"));

        Review review = Review.builder()
                .userName(reviewDTO.getUserName())
                .comment(reviewDTO.getComment())
                .rating(reviewDTO.getRating())
                .cafe(cafe)
                .build();

        reviewRepository.save(review);

        // 평균 평점 계산 및 저장
        List<Review> reviews = reviewRepository.findAllByCafe_Idx(cafe.getIdx());
        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        cafe.setRating(averageRating);
        cafeRepository.save(cafe);
    }

    @Override
    public void deleteReview(long reviewId, long cafeId) {
        reviewRepository.deleteById(reviewId);

        // 리뷰 삭제 후 평균 평점 갱신
        List<Review> reviews = reviewRepository.findAllByCafe_Idx(cafeId);
        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Cafe ID"));
        cafe.setRating(averageRating);
        cafeRepository.save(cafe);
    }

    @Override
    public ReviewDTO findById(long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Review ID"));
        return ReviewDTO.builder()
                .idx(review.getIdx())
                .userName(review.getUserName())
                .comment(review.getComment())
                .rating(review.getRating())
                .cafeId(review.getCafe().getIdx())
                .build();
    }

    @Override
    public void editReview(ReviewDTO reviewDTO) {
        // 기존 리뷰를 가져옴
        Review review = reviewRepository.findById(reviewDTO.getIdx())
                .orElseThrow(() -> new IllegalArgumentException("Review not found with ID: " + reviewDTO.getIdx()));

        // 필드 값 업데이트
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        reviewRepository.save(review);

        // 리뷰가 속한 카페의 평균 평점 업데이트
        List<Review> reviews = reviewRepository.findAllByCafe_Idx(reviewDTO.getCafeId());
        double averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        Cafe cafe = cafeRepository.findById(reviewDTO.getCafeId())
                .orElseThrow(() -> new IllegalArgumentException("Cafe not found with ID: " + reviewDTO.getCafeId()));
        cafe.setRating(averageRating);
        cafeRepository.save(cafe);
    }
}
