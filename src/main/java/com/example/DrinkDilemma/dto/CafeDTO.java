package com.example.DrinkDilemma.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CafeDTO {
    private long idx;
    private String storeName;
    private String address;
    private String content;
    private String bestMenu;
    private List<String> time;
    private double rating;
}