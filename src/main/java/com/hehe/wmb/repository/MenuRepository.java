package com.hehe.wmb.repository;

import com.hehe.wmb.entity.Menu;
import com.hehe.wmb.entity.enums.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository  extends JpaRepository<Menu, Long> {

    List<Menu> findAllByNameContaining(String name, Sort sort);

    Page<Menu> findAllByNameContaining(String name, Pageable pageable);

    Page<Menu> findAllByNameContainingAndMenuCategory(String name, MenuCategory menuCategory, Pageable pageable);
}
