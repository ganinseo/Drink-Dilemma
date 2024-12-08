package com.example.DrinkDilemma.service;

import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.dto.CafeDTO;

import java.util.List;

public interface CafeService {
    List<CafeDTO> findAll();
    CafeDTO findById(long idx);
    Cafe save(CafeDTO cafeDTO);
    void deleteById(long id);
    double calculateRating(long cafeId);
    List<CafeDTO> searchByStoreName(String storeName);
    List<CafeDTO> searchByMenuName(String menuName);
    List<CafeDTO> searchByAddress(String address);
    List<CafeDTO> findRandom();
    List<CafeDTO> findSortedByRatingDesc();
    List<CafeDTO> findSortedByRatingAsc();
    List<CafeDTO> findSortedByReviewCount();
    List<CafeDTO> findSortedByRecentUpdates();
    List<CafeDTO> findOpenNow();
}