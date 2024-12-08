package com.example.DrinkDilemma.repository;

import com.example.DrinkDilemma.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findByStoreNameContainingIgnoreCase(String storeName);
    List<Cafe> findByAddressContainingIgnoreCase(String address);

    @Query("SELECT c FROM Cafe c LEFT JOIN c.reviews r GROUP BY c ORDER BY COUNT(r) DESC")
    List<Cafe> findAllOrderByReviewCountDesc();
    List<Cafe> findAllByOrderByUpdatedAtDesc();

    @Query("SELECT DISTINCT c FROM Cafe c LEFT JOIN FETCH c.reviews")
    List<Cafe> findAllDistinct();
}
