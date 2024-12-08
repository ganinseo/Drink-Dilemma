package com.example.DrinkDilemma.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menu")
@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String menuName;
    private String price;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
}