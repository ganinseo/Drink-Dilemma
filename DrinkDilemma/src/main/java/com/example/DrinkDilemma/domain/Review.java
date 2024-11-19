package com.example.DrinkDilemma.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String userName;
    private String comment;
    private String rating;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
}
