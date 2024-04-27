package com.attus.gerenciamento_pessoas.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "people")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Nome Completo
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name", nullable = false)
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String birthdate; // Data de nascimento
    @Column(nullable = false)
    private String address; // Endereço
}
