package com.hehe.wmb.dto.request;

import com.hehe.wmb.dto.validation.OnUpdate;
import com.hehe.wmb.entity.enums.MenuCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MenuRequest {

    @NotNull(groups = OnUpdate.class)
    private Long id;

    @NotBlank(message = "name required")
    @Length(max = 100, min = 2, message = "max length is 100, and min is 2 character")
    private String name;

    @Length(max = 255, message = "too long description")
    private String description;

    @Positive(message = "cannot be negative value")
    private Double price;

    @NotNull(message = "category required")
    private MenuCategory category;
}

