package com.attus.gerenciamento_pessoas.dto;

public record PersonDto(
        String fullName,
        String birthdate,
        String zipCode,
        Integer number,
        String city,
        String state,
        String street
) {
}
