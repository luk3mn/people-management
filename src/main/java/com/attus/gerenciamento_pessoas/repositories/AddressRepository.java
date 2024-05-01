package com.attus.gerenciamento_pessoas.repositories;

import com.attus.gerenciamento_pessoas.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
