package com.hehe.wmb.repository;

import com.hehe.wmb.model.Menu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository  extends JpaRepository<Menu, Long> {

    List<Menu> findAllByNameContaining(String name, Sort sort);
}
