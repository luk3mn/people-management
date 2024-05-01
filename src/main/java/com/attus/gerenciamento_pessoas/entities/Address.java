package com.attus.gerenciamento_pessoas.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String street;
    @Column(name = "zip_code", nullable = false)
    private String zipCode;
    @Column(nullable = false)
    private Integer number;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String state;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person person;

}
