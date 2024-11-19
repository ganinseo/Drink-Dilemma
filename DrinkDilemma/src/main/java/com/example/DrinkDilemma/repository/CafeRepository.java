package com.example.DrinkDilemma.repository;

import com.example.DrinkDilemma.domain.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {
    List<Cafe> findByStoreNameContainingIgnoreCase(String storeName);

    List<Cafe> findByAddressContainingIgnoreCase(String address);
}
