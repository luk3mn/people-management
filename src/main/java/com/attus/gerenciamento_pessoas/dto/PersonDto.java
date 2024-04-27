package com.attus.gerenciamento_pessoas.dto;

import java.util.UUID;

public record PersonDto(
        UUID id,
        String firstName,
        String middleName,
        String lastName,
        String birthdate,
        String address
) {
}
