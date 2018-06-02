package com.systelab.skillsbase.repository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private final Long id;

    public UserNotFoundException(Long id) {
        super("user-not-found-" + id);
        this.id = id;
    }

    public Long getUserId() {
        return id;
    }


}
