package com.attus.gerenciamento_pessoas.services;

import com.attus.gerenciamento_pessoas.dto.AddressDTO;
import com.attus.gerenciamento_pessoas.dto.PersonRequestDTO;
import com.attus.gerenciamento_pessoas.dto.PersonDto;
import com.attus.gerenciamento_pessoas.dto.PersonAddressResponseDto;
import com.attus.gerenciamento_pessoas.entities.Address;
import com.attus.gerenciamento_pessoas.entities.Person;
import com.attus.gerenciamento_pessoas.exceptions.person.PersonNotFoundException;
import com.attus.gerenciamento_pessoas.repositories.AddressRepository;
import com.attus.gerenciamento_pessoas.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public List<Person> searchPerson() {
        return personRepository.findAll();
    }

    public PersonAddressResponseDto createPerson(PersonDto personDto) {
        Person person = new Person();
        person.setFullName(personDto.fullName());
        person.setBirthdate(personDto.birthdate());

        person = personRepository.save(person);

        Address address = saveAddress(personDto, person);

        if (person.getMainAddressId() == null) {
            person.setMainAddressId(address.getId());
            personRepository.save(person);
        }

        return new PersonAddressResponseDto(person.getId(), person.getFullName(), person.getBirthdate(), address, person.getMainAddressId());
    }

    public Person searchPersonById(UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id));
    }

    public PersonAddressResponseDto updateAddress(UUID addressId, AddressDTO addressDTO) {

        Optional<Address> foundAddress = addressRepository.findById(addressId);
        if (foundAddress.isEmpty()) {
            throw new RuntimeException("Address not found with id: " + addressId);
        }

        Address address = foundAddress.get();
        address.setZipCode(addressDTO.zipCode());
        address.setNumber(addressDTO.number());
        address.setState(addressDTO.state());
        address.setCity(addressDTO.city());
        address.setStreet(addressDTO.street());
        addressRepository.save(address); // save information

        Optional<Person> foundPerson = personRepository.findById(address.getPerson().getId());
        if (foundPerson.isEmpty()) {
            throw new PersonNotFoundException("Person not found with id: " + address.getPerson().getId());
        }

        Person person = foundPerson.get();
        return new PersonAddressResponseDto(person.getId(), person.getFullName(), person.getBirthdate(), address, person.getMainAddressId());
    }

    public PersonAddressResponseDto updatePerson(UUID id, PersonRequestDTO personRequestDTO) {

        Optional<Person> foundPerson = personRepository.findById(id);
        if (foundPerson.isEmpty()) {
            throw new PersonNotFoundException("Person not found with id: " + id);
        }

        Person person = foundPerson.get();
        person.setFullName(personRequestDTO.fullName());
        person.setBirthdate(personRequestDTO.birthdate());
        person.setMainAddressId(personRequestDTO.mainAddressId());
        personRepository.save(person); // save new person's information

        // Recovering address person information to return as response
        Optional<Address> foundAddress = addressRepository.findById(personRequestDTO.mainAddressId());
        if (foundAddress.isEmpty()) {
            throw new RuntimeException("Address not found with id: " + personRequestDTO.mainAddressId());
        }

        return new PersonAddressResponseDto(person.getId(), person.getFullName(), person.getBirthdate(), foundAddress.get(), person.getMainAddressId());
    }

    public Person searchAllAddressByPerson(UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id));
    }

    public PersonAddressResponseDto createNewAddressForPerson(UUID personId, AddressDTO addressDTO) {

        Optional<Person> person = personRepository.findById(personId);
        if (person.isEmpty()) {
            throw new RuntimeException("Person not found");
        }

        // Create a new Address entity
        Address address = new Address();
        address.setStreet(addressDTO.street());
        address.setZipCode(addressDTO.zipCode());
        address.setNumber(addressDTO.number());
        address.setCity(addressDTO.city());
        address.setState(addressDTO.state());
        address.setPerson(person.get());

        Address newAddress = addressRepository.save(address);

        // Update the main address ID in the Person entity if it's not set yet
        if (person.get().getMainAddressId() == null) {
            person.get().setMainAddressId(address.getId());
            personRepository.save(person.get());
        }

        return new PersonAddressResponseDto(person.get().getId(), person.get().getFullName(), person.get().getBirthdate(), newAddress, person.get().getMainAddressId());
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
