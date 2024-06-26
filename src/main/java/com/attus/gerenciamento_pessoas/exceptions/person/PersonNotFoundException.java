package com.attus.gerenciamento_pessoas.exceptions.person;

import org.springframework.http.HttpStatus;

public class PersonNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public PersonNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
