package com.hehe.wmb.dto.request;

import com.hehe.wmb.entity.enums.MenuCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuFilterRequest {

    // sort request
    String name = "";
    String sortBy = "updatedAt";
    boolean asc;

    // pagination request
    int page;
    int size;

    MenuCategory category;

    public MenuFilterRequest() {

    }
}
