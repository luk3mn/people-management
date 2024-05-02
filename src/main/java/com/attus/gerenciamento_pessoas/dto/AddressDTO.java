package com.attus.gerenciamento_pessoas.dto;

public record AddressDTO(
//        UUID id,
        String zipCode,
        Integer number,
        String city,
        String state,
        String street
) {
}
