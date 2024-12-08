package com.example.DrinkDilemma.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private long idx;
    private String userName;
    private String comment;
    private Double rating;
    private long cafeId;
}