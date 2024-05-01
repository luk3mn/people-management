package com.attus.gerenciamento_pessoas.services;

import com.attus.gerenciamento_pessoas.dto.PersonDto;
import com.attus.gerenciamento_pessoas.dto.PersonResponseDto;
import com.attus.gerenciamento_pessoas.entities.Address;
import com.attus.gerenciamento_pessoas.entities.Person;
import com.attus.gerenciamento_pessoas.exceptions.person.PersonNotFoundException;
import com.attus.gerenciamento_pessoas.repositories.AddressRepository;
import com.attus.gerenciamento_pessoas.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public List<Person> searchPerson() {
        return personRepository.findAll();
    }

    public PersonResponseDto createPerson(PersonDto personDto) {
        Person person = new Person();
        person.setFullName(personDto.fullName());
        person.setBirthdate(personDto.birthdate());

        person = personRepository.save(person);

        Address address = saveAddress(personDto, person);
        return new PersonResponseDto(person.getId(), person.getFullName(), person.getBirthdate(), address);
    }

    public Person searchPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id));
    }

//    public PersonEntity updatePerson(UUID id, PersonEntity personEntity) {
//
//        var person = personRepository.findById(id).orElse(null);
//
//        if (person == null) {
//            throw new PersonNotFoundException("Person not found with id: " + id);
//        }
//
//        person.setFirstName(personEntity.getFirstName());
//        person.setMiddleName(personEntity.getMiddleName());
//        person.setLastName(personEntity.getLastName());
//        person.setAddress(personEntity.getAddress());
//        person.setBirthdate(personEntity.getBirthdate());
//        return personRepository.save(person);
//    }

    public List<Person> searchAllAddressByPerson(UUID id) {
        return personRepository.findAll();
    }

    private Address saveAddress(PersonDto personDto, Person person) {
        var address = new Address();

        address.setCity(personDto.city());
        address.setStreet(personDto.street());
        address.setZipCode(personDto.zipCode());
        address.setState(personDto.state());
        address.setNumber(personDto.number());
        address.setPerson(person);

        return this.addressRepository.save(address);
    }

}
