package com.example.DrinkDilemma.controller;

import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.dto.CafeDTO;
import com.example.DrinkDilemma.service.CafeService;
import com.example.DrinkDilemma.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CafeController {
    @Autowired
    CafeService cafeService;

    @Autowired
    MenuService menuService;

    @RequestMapping("/cafe")
    public String list(Model model) {
        model.addAttribute("cafes", cafeService.findAll());
        return "list";
    }

    @RequestMapping("/cafe/addform")
    public String addform()  {
        return "addform";
    }

    @RequestMapping("/cafe/add")
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

        return "redirect:/cafe";
    }

    @RequestMapping("/cafe/{idx}")
    public String read(@PathVariable long idx, Model model) {
        // 카페 정보 가져오기
        model.addAttribute("cafe", cafeService.findById(idx));

        // 메뉴 리스트 가져오기
        model.addAttribute("menus", menuService.findByCafeId(idx));
        return "read";
    }

    @RequestMapping("/cafe/delete/{idx}")
    public String delete(@PathVariable long idx)  {
        cafeService.deleteById(idx);
        return "redirect:/cafe";
    }

    @RequestMapping("/cafe/updateform/{idx}")
    public String updatCafe(@PathVariable Long idx,  Model model) {
        CafeDTO cafe = cafeService.findById(idx);
        model.addAttribute("cafe", cafe);
        return "updateform";
    }

    @RequestMapping("/cafe/update")
    public String update(@ModelAttribute CafeDTO cafe)  {
        cafeService.save(cafe);
        return "redirect:/cafe";
    }

    @RequestMapping("/cafe/searchform")
    public String searchForm() { return "searchform"; }

    @RequestMapping("/cafe/search")
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
        return "searchform";
    }
}
