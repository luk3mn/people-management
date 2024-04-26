package com.attus.gerenciamento_pessoas.controllers;

import com.attus.gerenciamento_pessoas.entities.PersonEntity;
import com.attus.gerenciamento_pessoas.services.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public List<PersonEntity> findAll() {
        return personService.searchPerson();
    }
}
