package com.example.DrinkDilemma.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cafe")
@ToString
@Getter
@Setter // 모든 필드에 대해 setter 자동 생성
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cafe {
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt; // 엔티티 생성 시 자동 설정

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; // 엔티티 업데이트 시 자동 설정

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;

    private String storeName;
    private String address;
    private String content;
    private String bestMenu;

    @ElementCollection
    @CollectionTable(name = "cafe_hours", joinColumns = @JoinColumn(name = "cafe_id"))
    @Column(name = "hours")
    private List<String> time = new ArrayList<>(); // "10:00 - 22:00"과 같은 포맷을 저장

    private double rating;

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now(); // 생성 시 `createdAt` 설정
        this.updatedAt = LocalDateTime.now(); // 생성 시 `updatedAt`도 초기화
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now(); // 업데이트 시 `updatedAt` 자동 설정
    }
}
