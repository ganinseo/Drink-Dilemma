package com.example.DrinkDilemma.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    private long idx;
    private String menuName;
    private String price;
}