package com.attus.gerenciamento_pessoas.exceptions.person;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String message) {
        super(message);
    }

}
