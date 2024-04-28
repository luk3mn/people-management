package com.attus.gerenciamento_pessoas.controllers;

import com.attus.gerenciamento_pessoas.dto.PersonDto;
import com.attus.gerenciamento_pessoas.entities.AddressEntity;
import com.attus.gerenciamento_pessoas.entities.PersonEntity;
import com.attus.gerenciamento_pessoas.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    // TODO: Make a DTO to show just the main address
    @GetMapping
    public ResponseEntity<List<PersonEntity>> findAll() {
        var capturedAll = personService.searchPerson();
        return ResponseEntity.ok(capturedAll);
    }

    // TODO: show only main address
    @GetMapping("/{id}")
    public ResponseEntity<PersonEntity> findById(@PathVariable UUID id) {
        var captured = personService.searchPersonById(id);
        return ResponseEntity.ok(captured);
    }

    @PostMapping
    public ResponseEntity<PersonEntity> createPerson(@Valid @RequestBody PersonDto personDto, HttpServletRequest request) {
        var created = personService.createPerson(personDto, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonEntity> update(@RequestBody PersonEntity personEntity, @PathVariable UUID id) {
        var updated = personService.updatePerson(id, personEntity);
        return ResponseEntity.ok(updated);
    }

    // TODO: GET  /{idPerson}/address -> Show all the address by person
    @GetMapping("/{id}/address")
    public ResponseEntity<List<PersonEntity>> findAllAddress(@PathVariable UUID id) {
        var address = personService.searchAllAddressByPerson(id);
        return ResponseEntity.ok(address);
    }

    // TODO: POST /{idPerson}/address -> Create a new address, choose main address and associate to the person
    // TODO: PUT  /{idPerson}/address/idAddress -> Edit a specif address associated to specif person
}
