package com.attus.gerenciamento_pessoas.repositories;

import com.attus.gerenciamento_pessoas.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonEntity, UUID> {
}
