package com.hehe.wmb.service.impl;

import com.hehe.wmb.dto.request.MenuFilterRequest;
import com.hehe.wmb.dto.request.MenuRequest;
import com.hehe.wmb.entity.Menu;
import com.hehe.wmb.repository.MenuRepository;
import com.hehe.wmb.service.MenuService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    @Override
    public Page<Menu> getAllMenusByPage(MenuFilterRequest menuFilterRequest) {

        // buat sorting dan direction
        Sort sort = Sort.by(
                menuFilterRequest.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC ,
                menuFilterRequest.getSortBy()
        );

        // buat pagination
        Pageable pageable = PageRequest.of(menuFilterRequest.getPage() - 1, menuFilterRequest.getSize(), sort);

        if(menuFilterRequest.getCategory() == null) {
            return menuRepository.findAllByNameContaining(
                    menuFilterRequest.getName(),
                    pageable
            );
        }else {
            return menuRepository.findAllByNameContainingAndMenuCategory(
                    menuFilterRequest.getName(),
                    menuFilterRequest.getCategory(),
                    pageable
            );
        }

    }

    @Override
    public Menu addMenu(MenuRequest request) {
        Menu newMenu = Menu.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .menuCategory(request.getCategory())
                .build();
        return menuRepository.save(newMenu);
    }

    @Override
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + id));
    }

    @Override
    public Menu updateMenu(MenuRequest request) {
        Menu existingMenu = menuRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("Menu not found with id: " + request.getId()));

        existingMenu.setName(request.getName());
        existingMenu.setDescription(request.getDescription());
        existingMenu.setPrice(request.getPrice());
        existingMenu.setMenuCategory(request.getCategory());

        return menuRepository.save(existingMenu);
    }

    @Override
    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }

}
