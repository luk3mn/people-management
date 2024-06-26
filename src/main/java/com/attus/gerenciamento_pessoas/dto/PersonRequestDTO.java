package com.attus.gerenciamento_pessoas.dto;

import com.attus.gerenciamento_pessoas.entities.Address;

import java.util.UUID;

public record PersonRequestDTO(
        String fullName,
        String birthdate,
        UUID mainAddressId
) {
}
