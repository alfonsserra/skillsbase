package com.systelab.skillsbase.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {
    private final Long id;

    public CategoryNotFoundException(Long id) {
        super("category-not-found-" + id);
        this.id = id;
    }

    public Long getCategoryId() {
        return id;
    }
}