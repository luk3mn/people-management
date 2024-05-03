package com.attus.gerenciamento_pessoas.dto;

import com.attus.gerenciamento_pessoas.entities.Address;

import java.util.UUID;

public record PersonResponseDTO(
        UUID id,
        String fullName,
        String birthdate,
        Address address,
        UUID mainAddressId
) {
}
