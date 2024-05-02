package com.attus.gerenciamento_pessoas.dto;

public record AddressDTO(
        String zipCode,
        Integer number,
        String city,
        String state,
        String street
) {
}
