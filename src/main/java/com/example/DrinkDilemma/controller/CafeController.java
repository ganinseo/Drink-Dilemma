package com.example.DrinkDilemma.controller;

import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.dto.CafeDTO;
import com.example.DrinkDilemma.dto.ReviewDTO;
import com.example.DrinkDilemma.service.CafeService;
import com.example.DrinkDilemma.service.MenuService;
import com.example.DrinkDilemma.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CafeController {
    @Autowired
    CafeService cafeService;

    @Autowired
    MenuService menuService;

    @Autowired
    ReviewService reviewService;

    @RequestMapping("/cafe")
    public String drinkDilemma() {
        return "drinkdilemma"; // drinkdilemma.html 반환
    }

    @RequestMapping("/cafe/filtersearch") // 카페 검색 페이지
    public String filterserch(Model model) {
        List<CafeDTO> cafes = cafeService.findAll();
        cafes.forEach(cafe -> System.out.println("Cafe: " + cafe)); // 디버깅 로그
        model.addAttribute("cafes", cafes);
        return "filtersearch";
    }

    @RequestMapping("/cafe/filtersearch/{idx}") // 카페 검색 페이지의 각 아이템
    public String read(@PathVariable long idx, Model model) {
        CafeDTO cafe = cafeService.findById(idx);
        model.addAttribute("cafe", cafe);
        model.addAttribute("menus", menuService.findByCafeId(idx));

        // 리뷰 데이터 추가
        List<ReviewDTO> reviews = reviewService.getReviewsByCafeId(idx);
        model.addAttribute("reviews", reviews);
        return "read";
    }

    @RequestMapping("/cafe/searchform")
    public String searchForm(Model model) {
        model.addAttribute("cafes", cafeService.findAll());
        return "filtersearch";
    }

    @RequestMapping("/cafe/filtersearch/search") // 검색 조건
    public String search(@RequestParam String searchType, @RequestParam(required = false) String keyword, Model model) {
        List<CafeDTO> result;

        switch (searchType) {
            case "storeName":
                result = cafeService.searchByStoreName(keyword);
                break;
            case "menuName":
                result = cafeService.searchByMenuName(keyword);
                break;
            case "address":
                result = cafeService.searchByAddress(keyword);
                break;
            default:
                result = List.of();
        }
        model.addAttribute("cafes", result);
        return "filtersearch";
    }


    @RequestMapping("/cafe/filtersearch/filter") // 필터링
    @ResponseBody
    public List<CafeDTO> filterCafes(@RequestParam(required = false, defaultValue = "default") String filterType) {
        switch (filterType) {
            case "highRating":
                return cafeService.findSortedByRatingDesc();
            case "lowRating":
                return cafeService.findSortedByRatingAsc();
            case "manyReviews":
                return cafeService.findSortedByReviewCount();
            case "recentUpdates":
                return cafeService.findSortedByRecentUpdates();
            case "openNow":
                return cafeService.findOpenNow();
            case "default":
            default:
                return cafeService.findRandom(); // 기본 랜덤 정렬
        }
    }

    @RequestMapping("/cafe/filtersearch/addform")
    public String addform()  {
        return "addform";
    }

    @RequestMapping("/cafe/filtersearch/add")
    public String add(
            @ModelAttribute CafeDTO cafeDTO,
            @RequestParam List<String> menuNames,
            @RequestParam List<String> menuPrices) {

        // 1. 카페 저장
        Cafe savedCafe = cafeService.save(cafeDTO);

        // 2. 메뉴 저장
        for (int i = 0; i < menuNames.size(); i++) {
            String menuName = menuNames.get(i);
            String menuPrice = menuPrices.get(i);
            menuService.saveMenu(menuName, menuPrice, savedCafe);
        }
        return "redirect:/cafe/filtersearch";
    }

    @RequestMapping("/cafe/filtersearch/delete/{idx}")
    public String delete(@PathVariable long idx)  {
        cafeService.deleteById(idx);
        return "redirect:/cafe/filtersearch";
    }

    @RequestMapping("/cafe/filtersearch/updateform/{idx}")
    public String updateCafe(@PathVariable Long idx, Model model) {
        System.out.println("updateCafe called with idx: " + idx);
        CafeDTO cafe = cafeService.findById(idx);
        if (cafe == null) {
            throw new IllegalArgumentException("Cafe not found with idx: " + idx);
        }
        model.addAttribute("cafe", cafe);
        return "updateform";
    }

    @RequestMapping("/cafe/filtersearch/update")
    public String update(@ModelAttribute CafeDTO cafe) {
        cafeService.save(cafe);
        return "redirect:/cafe/filtersearch";
    }
}