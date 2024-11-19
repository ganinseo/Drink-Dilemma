package com.example.DrinkDilemma.service;

import com.example.DrinkDilemma.repository.CafeRepository;
import com.example.DrinkDilemma.repository.MenuRepository;
import com.example.DrinkDilemma.Utils;
import com.example.DrinkDilemma.domain.Cafe;
import com.example.DrinkDilemma.dto.CafeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return cafeRepository.findAll().stream()
                .map(v ->  Utils.toDTO(v))
                .collect(Collectors.toList());
    }

    @Override
    public CafeDTO findById(long id) {
        return cafeRepository.findById(id)
                .map(v ->  Utils.toDTO(v))
                .orElse(null);
    }

    @Override
    public Cafe save(CafeDTO cafeDTO) {
        Cafe cafe = Utils.toEntity(cafeDTO);
        return cafeRepository.save(cafe);
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
}
