package com.attus.gerenciamento_pessoas.services;

import com.attus.gerenciamento_pessoas.dto.PersonDto;
import com.attus.gerenciamento_pessoas.entities.PersonEntity;
import com.attus.gerenciamento_pessoas.exceptions.person.PersonNotFoundException;
import com.attus.gerenciamento_pessoas.repositories.AddressRepository;
import com.attus.gerenciamento_pessoas.repositories.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public List<PersonEntity> searchPerson() {
        return personRepository.findAll();
    }

    public PersonEntity createPerson(PersonDto personDto, HttpServletRequest request) {

        var person = new PersonEntity();

        var idPerson = request.getAttribute("id");
        person.setId((UUID) idPerson);

        person.setFirstName(personDto.firstName());
        person.setAddress(personDto.address());
        person.setMiddleName(personDto.middleName());
        person.setLastName(personDto.lastName());
        person.setBirthdate(personDto.birthdate());

        return personRepository.save(person);
    }

    public PersonEntity searchPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id));
    }

    public PersonEntity updatePerson(UUID id, PersonEntity personEntity) {

        var person = personRepository.findById(id).orElse(null);

        if (person == null) {
            throw new PersonNotFoundException("Person not found with id: " + id);
        }

        person.setFirstName(personEntity.getFirstName());
        person.setMiddleName(personEntity.getMiddleName());
        person.setLastName(personEntity.getLastName());
        person.setAddress(personEntity.getAddress());
        person.setBirthdate(personEntity.getBirthdate());
        return personRepository.save(person);
    }

    public List<PersonEntity> searchAllAddressByPerson(UUID id) {
        return personRepository.findAll();
    }
}
