package com.example.DrinkDilemma.repository;

import com.example.DrinkDilemma.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByMenuNameContainingIgnoreCase(String menuName);
    List<Menu> findByCafeIdx(long cafeIdx);
}
