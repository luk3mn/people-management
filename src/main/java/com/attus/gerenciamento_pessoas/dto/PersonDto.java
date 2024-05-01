package com.attus.gerenciamento_pessoas.dto;

import com.attus.gerenciamento_pessoas.entities.Address;
import com.attus.gerenciamento_pessoas.entities.Person;

import java.util.UUID;

public record PersonDto(
//        UUID id,
        String fullName,
        String birthdate,
//        Address address
        String zipCode,
        Integer number,
        String city,
        String state,
        String street
) {
}
