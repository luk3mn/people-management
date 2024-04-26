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

    @Column(name = "full_name", nullable = false)
    private String fullName; // Nome Completo
    @Column(nullable = false)
    private String birthdate; // Data de nascimento
    @Column(nullable = false)
    private String address; // Endereço
}
