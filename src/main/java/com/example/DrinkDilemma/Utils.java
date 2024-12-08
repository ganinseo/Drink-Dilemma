package com.example.DrinkDilemma;

import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.domain.Menu;
import com.example.DrinkDilemma.dto.CafeDTO;
import com.example.DrinkDilemma.dto.MenuDTO;

public class Utils {
    public static CafeDTO toDTO(Cafe cafe) {
        return CafeDTO.builder()
                .idx(cafe.getIdx())
                .storeName(cafe.getStoreName())
                .address(cafe.getAddress())
                .content(cafe.getContent())
                .bestMenu(cafe.getBestMenu())
                .time(cafe.getTime())
                .rating(cafe.getRating()) // double 타입 유지
                .build();
    }

    public static Cafe toEntity(CafeDTO cafeDTO) {
        return Cafe.builder()
                .idx(cafeDTO.getIdx())
                .storeName(cafeDTO.getStoreName())
                .address(cafeDTO.getAddress())
                .content(cafeDTO.getContent())
                .bestMenu(cafeDTO.getBestMenu())
                .time(cafeDTO.getTime())
                .rating(cafeDTO.getRating()) // double 타입 유지
                .build();
    }

    public static MenuDTO toDTO(Menu entity) {
        return MenuDTO.builder()
                .idx(entity.getIdx())
                .menuName(entity.getMenuName())
                .price(entity.getPrice())
                .build();
    }


    public static Menu toEntity(MenuDTO dto) {
        return Menu.builder()
                .idx(dto.getIdx())
                .menuName(dto.getMenuName())
                .price(dto.getPrice())
                .build();
    }
}
