package com.systelab.skillsbase.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SkillNotFoundException extends RuntimeException {
    private final Long id;

    public SkillNotFoundException(Long id) {
        super("skill-not-found-" + id);
        this.id = id;
    }

    public Long getSkillId() {
        return id;
    }
}