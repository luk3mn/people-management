package com.attus.gerenciamento_pessoas.controllers;

import com.attus.gerenciamento_pessoas.dto.AddressDTO;
import com.attus.gerenciamento_pessoas.dto.PersonDto;
import com.attus.gerenciamento_pessoas.dto.PersonAddressResponseDto;
import com.attus.gerenciamento_pessoas.entities.Person;
import com.attus.gerenciamento_pessoas.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    // TODO: Make a DTO to show just the main address
    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        var capturedAll = personService.searchPerson();
        return ResponseEntity.ok(capturedAll);
    }

    // TODO: show only main address
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable UUID id) {
        var captured = personService.searchPersonById(id);
        return ResponseEntity.ok(captured);
    }

    // TODO: consider how to include more than one address and place it as main
    @PostMapping
    public ResponseEntity<PersonAddressResponseDto> createPerson(@Valid @RequestBody PersonDto personDto) {
        PersonAddressResponseDto created = personService.createPerson(personDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonAddressResponseDto> update(@RequestBody PersonDto personDto, @PathVariable UUID id) {
        var updated = personService.updatePerson(id, personDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<Person> findAllAddress(@PathVariable UUID id) {
        var address = personService.searchAllAddressByPerson(id);
        return ResponseEntity.ok(address);
    }

    @PostMapping("/{personId}/address")
    public ResponseEntity<PersonAddressResponseDto> newAddress(@PathVariable UUID personId, @RequestBody AddressDTO addressDTO) {
        var newAddress = personService.createNewAddressForPerson(personId, addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAddress);
    }
}
