package com.hehe.wmb.controller;

import com.hehe.wmb.dto.request.MenuFilterRequest;
import com.hehe.wmb.dto.request.MenuRequest;
import com.hehe.wmb.dto.validation.OnUpdate;
import com.hehe.wmb.entity.Menu;
import com.hehe.wmb.entity.enums.MenuCategory;
import com.hehe.wmb.service.MenuService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * - Mendukung pagination
     *
     * @param model Model untuk dikirim ke view
     * @param name Parameter pencarian nama menu (opsional)
     * @param sortBy Kolom untuk pengurutan (opsional)
     * @param asc Arah pengurutan (default false = descending)
     * @param page Halaman pagination data (default value 1)
     * @param size Ukuran data tiap halaman pagination (default value 10)
     * @return nama view (menu/index)
     */
    @GetMapping({"","/"})
    public String index(
            Model model,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "asc", required = false, defaultValue = "false") boolean asc,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "category", required = false) String category
    ) {

        MenuFilterRequest menuFilterRequest = new MenuFilterRequest();
        if(StringUtils.hasText(name)){
            menuFilterRequest.setName(name);
        }
        if(StringUtils.hasText(sortBy)){
            menuFilterRequest.setSortBy(sortBy);
        }
        menuFilterRequest.setAsc(asc);
        menuFilterRequest.setPage(page);
        menuFilterRequest.setSize(size);

        if(StringUtils.hasText(category)){
            MenuCategory.fromString(category.toUpperCase()).ifPresent(menuFilterRequest::setCategory);
        }

        Page<Menu> menuList = menuService.getAllMenusByPage(menuFilterRequest);

        // kirim data ke thymeleaf
        model.addAttribute("menuList", menuList);

        // kirim param search name, direction dan sortBy
        model.addAttribute("paramName", name);
        model.addAttribute("asc", menuFilterRequest.isAsc());
        model.addAttribute("paramSortBy", menuFilterRequest.getSortBy());

        // kirim param pagination
        model.addAttribute("paramPage", menuFilterRequest.getPage());
        model.addAttribute("paramSize", menuFilterRequest.getSize());

        // buat startPage dan endPage
        int currentPage = menuList.getNumber() + 1;
        int totalPages = menuList.getTotalPages();
        int startPage = Math.max(currentPage - 1, 1);
        int endPage = Math.min(currentPage + 1, totalPages);

        // kirim data startPage dan endPage
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // kirim filter param by category
        model.addAttribute("categories", MenuCategory.values());
        String paramCategory = menuFilterRequest.getCategory() != null ? menuFilterRequest.getCategory().name() : null;
        model.addAttribute("paramCategory",  paramCategory);


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
            model.addAttribute("errorMessage", "Gagal menyimpan menu: " + e.getCause().getMessage());
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
