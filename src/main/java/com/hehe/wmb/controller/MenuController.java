package com.hehe.wmb.controller;

import com.hehe.wmb.dto.request.MenuFilterRequest;
import com.hehe.wmb.dto.request.MenuRequest;
import com.hehe.wmb.dto.validation.OnUpdate;
import com.hehe.wmb.model.Menu;
import com.hehe.wmb.model.enums.MenuCategory;
import com.hehe.wmb.service.MenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * Menampilkan halaman daftar menu.
     *
     * Fitur:
     * - Mendukung filter nama menu
     * - Mendukung pengurutan berdasarkan kolom tertentu (sortBy) dan arah ascending/descending (asc)
     *
     * @param model Model untuk dikirim ke view
     * @param name Parameter pencarian nama menu (opsional)
     * @param sortBy Kolom untuk pengurutan (opsional)
     * @param asc Arah pengurutan (default false = descending)
     * @return nama view (menu/index)
     */
    @GetMapping({"","/"})
    public String index(
            Model model,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "asc", required = false, defaultValue = "false") boolean asc
    ) {

        MenuFilterRequest menuFilterRequest = new MenuFilterRequest();
        if(StringUtils.hasText(name)){
            menuFilterRequest.setName(name);
        }
        if(StringUtils.hasText(sortBy)){
            menuFilterRequest.setSortBy(sortBy);
        }
        menuFilterRequest.setAsc(asc);

        List<Menu> menuList = menuService.getAllMenus(menuFilterRequest);

        model.addAttribute("menuList", menuList);
        model.addAttribute("paramName", name);
        model.addAttribute("asc", menuFilterRequest.isAsc());
        model.addAttribute("paramSortBy", menuFilterRequest.getSortBy());

        return "menu/index";
    }

    /**
     * Menampilkan form untuk menambahkan menu baru.
     *
     * @param model Model untuk dikirim ke view
     * @return nama view (menu/add-menu)
     */
    @GetMapping("/add")
    public String addMenuForm(Model model) {
        model.addAttribute("menu", new MenuRequest());
        model.addAttribute("categories", MenuCategory.values());
        return "menu/add-menu";
    }

    /**
     * Menangani pengiriman form untuk menambahkan menu baru.
     *
     * Validasi dilakukan menggunakan anotasi @Valid pada MenuRequest.
     * Jika validasi gagal, akan kembali ke form.
     * Jika sukses, redirect ke daftar menu.
     *
     * @param request Data menu yang dikirim dari form
     * @param bindingResult Hasil validasi
     * @param model Model untuk dikirim ke view
     * @return redirect ke /menu atau kembali ke form jika error
     */
    @PostMapping("/add")
    public String addMenu(
            @ModelAttribute("menu") @Valid MenuRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", MenuCategory.values());
            return "menu/add-menu";
        }

        try {
            menuService.addMenu(request);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Gagal menyimpan menu: " + e.getMessage());
            model.addAttribute("categories", MenuCategory.values());
            return "menu/add-menu";
        }

        return "redirect:/menu";
    }

    /**
     * Menampilkan form untuk mengedit menu berdasarkan ID.
     *
     * @param model Model untuk dikirim ke view
     * @param id ID menu yang ingin diedit (harus positif)
     * @return nama view (menu/edit-menu) atau redirect ke /menu jika gagal
     */
    @GetMapping("/edit/{id}")
    public String editMenuForm(Model model, @PathVariable @Positive(message = "ID tidak valid") long id) {
        try {
            Menu menu = menuService.getMenuById(id);

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

    /**
     * Menangani pengiriman form untuk update menu.
     *
     * Validasi menggunakan grup OnUpdate.
     * Jika validasi gagal, kembali ke form edit.
     * Jika sukses, redirect ke daftar menu.
     *
     * @param request Data menu hasil edit
     * @param bindingResult Hasil validasi
     * @param model Model untuk dikirim ke view
     * @return redirect ke /menu atau kembali ke form jika error
     */
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
            menuService.updateMenu(request);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Gagal memperbarui menu: " + e.getMessage());
            model.addAttribute("categories", MenuCategory.values());
            return "menu/edit-menu";
        }

        return "redirect:/menu";
    }

    /**
     * Menghapus menu berdasarkan ID.
     *
     * @param id ID menu yang akan dihapus
     * @return redirect ke /menu
     */
    @DeleteMapping("/delete/{id}")
    public String deleteMenu(@PathVariable long id) {
        menuService.deleteMenu(id);
        return "redirect:/menu";
    }

}
