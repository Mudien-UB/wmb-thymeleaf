package com.hehe.wmb.service;

import com.hehe.wmb.dto.request.MenuFilterRequest;
import com.hehe.wmb.dto.request.MenuRequest;
import com.hehe.wmb.model.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAllMenus();
    List<Menu> getAllMenus(MenuFilterRequest menuFilterRequest);

    Menu addMenu(MenuRequest request);
    Menu getMenuById(Long id);
    Menu updateMenu(MenuRequest request);
    void deleteMenu(Long id);
}
