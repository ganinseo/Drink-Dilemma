package com.example.DrinkDilemma.service;

import com.example.DrinkDilemma.Utils;
import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.domain.Menu;
import com.example.DrinkDilemma.dto.MenuDTO;
import com.example.DrinkDilemma.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public void saveMenu(String menuName, String price, Cafe cafe) {
        Menu menu = Menu.builder()
                .menuName(menuName)
                .price(price)
                .cafe(cafe)
                .build();
        menuRepository.save(menu);
    }

    @Override
    public List<MenuDTO> findByCafeId(long cafeId) {
        return menuRepository.findByCafeIdx(cafeId).stream()
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }
}