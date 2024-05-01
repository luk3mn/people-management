package com.attus.gerenciamento_pessoas.dto;

import com.attus.gerenciamento_pessoas.entities.Address;
import com.attus.gerenciamento_pessoas.entities.Person;

import java.util.UUID;

public record PersonResponseDto(
        UUID id,
        String fullName,
        String birthdate,
        Address address
) {
}
