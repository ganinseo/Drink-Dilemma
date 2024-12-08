package com.example.DrinkDilemma.controller;

import com.example.DrinkDilemma.dto.ReviewDTO;
import com.example.DrinkDilemma.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/save")
    public String saveReview(@ModelAttribute ReviewDTO reviewDTO) {
        reviewService.saveReview(reviewDTO);
        return "redirect:/cafe/filtersearch/" + reviewDTO.getCafeId();
    }

    // 리뷰 수정
    @PostMapping("/update")
    public String updateReview(@ModelAttribute ReviewDTO reviewDTO) {
        reviewService.editReview(reviewDTO);
        // 수정 후 카페 상세 페이지로 리다이렉트
        return "redirect:/cafe/filtersearch/" + reviewDTO.getCafeId();
    }

    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable long id, @RequestParam long cafeId) {
        reviewService.deleteReview(id, cafeId);
        return "redirect:/cafe/filtersearch/" + cafeId;
    }

    // 리뷰 수정 폼 제공
    @GetMapping("/updateform/{id}")
    public String editReview(@PathVariable long id, Model model) {
        ReviewDTO review = reviewService.findById(id);
        model.addAttribute("review", review);
        return "editreview"; // 수정 폼 템플릿 이름
    }
}