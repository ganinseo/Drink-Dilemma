package com.example.DrinkDilemma.repository;

import com.example.DrinkDilemma.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByCafe_Idx(long cafeId);
}
