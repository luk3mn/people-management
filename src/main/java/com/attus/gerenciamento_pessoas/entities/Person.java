package com.attus.gerenciamento_pessoas.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "people")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Nome Completo
    @Column(name = "full_name", nullable = false)
    private String fullName; // Nome completo

    @Column(nullable = false)
    private String birthdate; // Data de nascimento

    @OneToMany(mappedBy = "person")
    @JsonManagedReference
    private List<Address> address; // Endereço

    @Column(name = "main_address_id")
    private UUID mainAddressId; // ID of the main address
}
