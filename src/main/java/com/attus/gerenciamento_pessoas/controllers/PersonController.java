package com.attus.gerenciamento_pessoas.controllers;

import com.attus.gerenciamento_pessoas.dto.PersonDto;
import com.attus.gerenciamento_pessoas.entities.PersonEntity;
import com.attus.gerenciamento_pessoas.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public PersonEntity createPerson(@RequestBody PersonDto personDto, HttpServletRequest request) {
        return personService.createPerson(personDto, request);
    }
}
