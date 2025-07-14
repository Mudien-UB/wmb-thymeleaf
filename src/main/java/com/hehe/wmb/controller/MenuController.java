package com.hehe.wmb.controller;

import com.hehe.wmb.dto.request.MenuRequest;
import com.hehe.wmb.dto.validation.OnUpdate;
import com.hehe.wmb.model.Menu;
import com.hehe.wmb.model.enums.MenuCategory;
import com.hehe.wmb.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuRepository menuRepository;

    public MenuController(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @GetMapping({"","/"})
    public String index(Model model) {
        List<Menu> menuList = menuRepository.findAll(Sort.by(Sort.Direction.ASC, "updatedAt"));
        model.addAttribute("menuList", menuList);
        return "menu/index";
    }

    @GetMapping("/add")
    public String addMenu(Model model) {
        model.addAttribute("menu", new MenuRequest());
        model.addAttribute("categories", MenuCategory.values());
        return "menu/add-menu";
    }

    @PostMapping("/add")
    public String addMenu(
            @ModelAttribute("menu") @Valid MenuRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", MenuCategory.values());
            model.addAttribute("menu", request);
            return "menu/add-menu";
        }

        System.out.println("menu request = " + request.toString());

        try{

        Menu newMenu = Menu.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .menuCategory(request.getCategory())
                .build();

        menuRepository.save(newMenu);
        }catch (Exception e){
            model.addAttribute("categories", MenuCategory.values());
            model.addAttribute("menu", request);
            model.addAttribute("errorMessage", "Failed to save menu: " + e.getMessage());
            return "menu/add-menu";
        }

        return "redirect:/menu";
    }

    @GetMapping("/edit/{id}")
    public String editMenu(Model model, @PathVariable @Positive(message = "invalid ID") long id) {
        try {
            Menu menu = menuRepository.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            MenuRequest menuRequest = new MenuRequest();
            menuRequest.setId(menu.getId());
            menuRequest.setName(menu.getName());
            menuRequest.setDescription(menu.getDescription());
            menuRequest.setPrice(menu.getPrice());
            menuRequest.setCategory(menu.getMenuCategory());

            model.addAttribute("menu", menuRequest);
            model.addAttribute("categories", MenuCategory.values());
        } catch (Exception e) {
            return "redirect:/menu";
        }

        return "menu/edit-menu";
    }


    @PutMapping("/edit/")
    public String editMenu(
            @ModelAttribute("menu") @Validated(OnUpdate.class) MenuRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", MenuCategory.values());
            return "menu/edit-menu";
        }

        try {
            Menu existingMenu = menuRepository.findById(request.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Menu not found"));

            existingMenu.setName(request.getName());
            existingMenu.setDescription(request.getDescription());
            existingMenu.setPrice(request.getPrice());
            existingMenu.setMenuCategory(request.getCategory());

            menuRepository.save(existingMenu);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to update menu: " + e.getMessage());
            model.addAttribute("categories", MenuCategory.values());
            return "menu/edit-menu";
        }

        return "redirect:/menu";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMenu(Model model, @PathVariable long id) {
        menuRepository.deleteById(id);
        return "redirect:/menu";
    }

}
