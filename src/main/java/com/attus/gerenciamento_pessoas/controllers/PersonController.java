package com.attus.gerenciamento_pessoas.controllers;

import com.attus.gerenciamento_pessoas.dto.AddressDTO;
import com.attus.gerenciamento_pessoas.dto.PersonDto;
import com.attus.gerenciamento_pessoas.dto.PersonAddressResponseDto;
import com.attus.gerenciamento_pessoas.dto.PersonRequestDTO;
import com.attus.gerenciamento_pessoas.entities.Person;
import com.attus.gerenciamento_pessoas.services.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pessoa", description = "Construção das APIs de Pessoa com relacionamento com Endereço")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonAddressResponseDto>> findAll() {
        var capturedAll = personService.searchPerson();
        return ResponseEntity.ok(capturedAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonAddressResponseDto> findById(@PathVariable UUID id) {
        var captured = personService.searchPersonById(id);
        return ResponseEntity.ok(captured);
    }

    @PostMapping
    public ResponseEntity<PersonAddressResponseDto> createPerson(@Valid @RequestBody PersonDto personDto) {
        PersonAddressResponseDto created = personService.createPerson(personDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonAddressResponseDto> update(@RequestBody PersonRequestDTO personRequestDTO, @PathVariable UUID id) {
        var updated = personService.updatePerson(id, personRequestDTO);
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

    @PutMapping("/address/{addressId}")
    public ResponseEntity<PersonAddressResponseDto> updateAddress(@PathVariable UUID addressId, @RequestBody AddressDTO addressDTO) {
        var updated = personService.updateAddress(addressId, addressDTO);
        return ResponseEntity.ok(updated);
    }
}
