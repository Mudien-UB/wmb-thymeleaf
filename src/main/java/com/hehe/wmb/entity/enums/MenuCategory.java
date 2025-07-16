package com.hehe.wmb.entity.enums;

import java.util.Arrays;
import java.util.Optional;

public enum MenuCategory {

    FOOD("FOOD"),
    SNACK("SNACK"),
    DRINK("DRINK"),
    COFFEE("COFFEE");

    private final String value;

    MenuCategory(String value) {
        this.value = value;
    }

    public static Optional<MenuCategory> fromString(String value) {
        return Arrays.stream(MenuCategory.values())
                .filter(c -> c.name().equalsIgnoreCase(value))
                .findFirst();
    }

}
