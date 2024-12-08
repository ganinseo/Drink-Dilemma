package com.example.DrinkDilemma.service;

import com.example.DrinkDilemma.repository.CafeRepository;
import com.example.DrinkDilemma.repository.MenuRepository;
import com.example.DrinkDilemma.Utils;
import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.dto.CafeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.DrinkDilemma.domain.Review;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CafeServiceImpl implements CafeService {

    @Autowired
    private CafeRepository cafeRepository;

    @Autowired
    private MenuRepository menuRepository;


    @Override
    public List<CafeDTO> findAll() {
        return cafeRepository.findAllDistinct().stream()
                .map(Utils::toDTO)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public CafeDTO findById(long id) {
        return cafeRepository.findById(id)
                .map(Utils::toDTO)
                .orElse(null);
    }

    @Override
    public Cafe save(CafeDTO cafeDTO) {
        Cafe cafe = cafeRepository.findById(cafeDTO.getIdx())
                .orElse(Cafe.builder().build()); // 기존 데이터가 없으면 새 객체 생성
        // 필드 업데이트
        cafe.setStoreName(cafeDTO.getStoreName());
        cafe.setAddress(cafeDTO.getAddress());
        cafe.setContent(cafeDTO.getContent());
        cafe.setBestMenu(cafeDTO.getBestMenu());
        cafe.setTime(cafeDTO.getTime());
        cafe.setRating(cafeDTO.getRating());
        return cafeRepository.save(cafe);
    }

    @Override
    public double calculateRating(long cafeId) {
        Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("Cafe not found with ID: " + cafeId));
        return cafe.getReviews().stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0); // Default to 0.0 if no reviews are present
    }

    @Override
    public void deleteById(long id) {
        cafeRepository.deleteById(id);
    }

    @Override
    public List<CafeDTO> searchByStoreName(String storeName) {
        return cafeRepository.findByStoreNameContainingIgnoreCase(storeName)
                .stream().map(Utils::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CafeDTO> searchByMenuName(String menuName) {
        return menuRepository.findByMenuNameContainingIgnoreCase(menuName)
                .stream().map(menu -> Utils.toDTO(menu.getCafe()))
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTO> searchByAddress(String address) {
        return cafeRepository.findByAddressContainingIgnoreCase(address)
                .stream().map(Utils::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<CafeDTO> findSortedByRatingDesc() {
        return cafeRepository.findAll().stream()
                .sorted((c1, c2) -> Double.compare(
                        c2.getRating(), c1.getRating()))
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTO> findSortedByRatingAsc() {
        return cafeRepository.findAll().stream()
                .sorted((c1, c2) -> Double.compare(
                        c1.getRating(), c2.getRating()))
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTO> findSortedByReviewCount() {
        return cafeRepository.findAll().stream()
                .sorted((c1, c2) -> Integer.compare(
                        c2.getReviews().size(), c1.getReviews().size()))
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTO> findSortedByRecentUpdates() {
        return cafeRepository.findAll().stream()
                .sorted((c1, c2) -> c2.getUpdatedAt().compareTo(c1.getUpdatedAt()))
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTO> findOpenNow() {
        LocalTime now = LocalTime.now();
        return cafeRepository.findAll().stream()
                .filter(cafe -> cafe.getTime().stream()
                        .anyMatch(time -> {
                            String[] times = time.split(" - ");
                            if (times.length != 2) return false;
                            try {
                                LocalTime start = LocalTime.parse(times[0].trim());
                                LocalTime end = LocalTime.parse(times[1].trim());
                                return !now.isBefore(start) && !now.isAfter(end);
                            } catch (DateTimeParseException e) {
                                return false;
                            }
                        }))
                .map(Utils::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CafeDTO> findRandom() {
        List<Cafe> cafes = cafeRepository.findAll();
        Collections.shuffle(cafes);
        return cafes.stream().map(Utils::toDTO).collect(Collectors.toList());
    }
}