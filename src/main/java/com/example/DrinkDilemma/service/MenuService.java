package com.example.DrinkDilemma.service;

import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.dto.MenuDTO;

import java.util.List;

public interface MenuService {
    void saveMenu(String menuName, String price, Cafe cafe);
    List<MenuDTO> findByCafeId(long cafeId);
}
