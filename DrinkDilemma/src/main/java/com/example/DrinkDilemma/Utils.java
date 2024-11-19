package com.example.DrinkDilemma;

import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.domain.Menu;
import com.example.DrinkDilemma.dto.CafeDTO;
import com.example.DrinkDilemma.dto.MenuDTO;

public class Utils {
    public static CafeDTO toDTO(Cafe entity) {
        return CafeDTO.builder()
                .idx(entity.getIdx())
                .storeName(entity.getStoreName())
                .address(entity.getAddress())
                .content(entity.getContent())
                .bestMenu(entity.getBestMenu())
                .time(entity.getTime())
                .build();
    }
    public static Cafe toEntity(CafeDTO dto) {
        return Cafe.builder()
                .idx(dto.getIdx())
                .storeName(dto.getStoreName())
                .address(dto.getAddress())
                .content(dto.getContent())
                .bestMenu(dto.getBestMenu())
                .time(dto.getTime())
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
