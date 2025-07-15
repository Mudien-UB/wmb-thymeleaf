package com.hehe.wmb.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuFilterRequest {

    String name = "";
    String sortBy = "updatedAt";
    boolean asc;

    public MenuFilterRequest() {

    }
}
