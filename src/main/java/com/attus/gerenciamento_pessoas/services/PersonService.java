package com.attus.gerenciamento_pessoas.services;

import com.attus.gerenciamento_pessoas.dto.PersonDto;
import com.attus.gerenciamento_pessoas.entities.PersonEntity;
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

}
