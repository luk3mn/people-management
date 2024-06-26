package com.attus.gerenciamento_pessoas.services;

import com.attus.gerenciamento_pessoas.dto.AddressDTO;
import com.attus.gerenciamento_pessoas.dto.PersonRequestDTO;
import com.attus.gerenciamento_pessoas.dto.PersonDto;
import com.attus.gerenciamento_pessoas.dto.PersonAddressResponseDto;
import com.attus.gerenciamento_pessoas.entities.Address;
import com.attus.gerenciamento_pessoas.entities.Person;
import com.attus.gerenciamento_pessoas.exceptions.address.AddressNotFoundException;
import com.attus.gerenciamento_pessoas.exceptions.person.PersonNotFoundException;
import com.attus.gerenciamento_pessoas.repositories.AddressRepository;
import com.attus.gerenciamento_pessoas.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public List<PersonAddressResponseDto> searchPerson() {
        List<Person> people = personRepository.findAll();
        List<PersonAddressResponseDto> responseDtos = new ArrayList<>();

        for (Person person : people) {
            // find the main address
            Address mainAddress = person.getAddress().stream()
                    .filter(address -> address.getId().equals(person.getMainAddressId()))
                    .findFirst()
                    .orElse(null);

            // iterate the list of person and return as a DTO to put on a specif response list
            PersonAddressResponseDto responseDto = new PersonAddressResponseDto(
                    person.getId(),
                    person.getFullName(),
                    person.getBirthdate(),
                    mainAddress,
                    person.getMainAddressId()
            );

            // store each searched item into a response list DTO
            responseDtos.add(responseDto);
        }

        return responseDtos;
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

    public PersonAddressResponseDto searchPersonById(UUID id) {
        Person person = personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id, HttpStatus.NOT_FOUND));

        Optional<Address> foundAddress = addressRepository.findById(person.getMainAddressId());
        if (foundAddress.isEmpty()) {
            throw new AddressNotFoundException("Address not found with id: " + person.getMainAddressId(), HttpStatus.NOT_FOUND);
        }

        return new PersonAddressResponseDto(person.getId(), person.getFullName(), person.getBirthdate(), foundAddress.get(), person.getMainAddressId());
    }

    public PersonAddressResponseDto updateAddress(UUID addressId, AddressDTO addressDTO) {

        Optional<Address> foundAddress = addressRepository.findById(addressId);
        if (foundAddress.isEmpty()) {
            throw new AddressNotFoundException("Address not found with id: " + addressId, HttpStatus.NOT_FOUND);
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
            throw new PersonNotFoundException("Person not found with id: " + address.getPerson().getId(), HttpStatus.NOT_FOUND);
        }

        Person person = foundPerson.get();
        return new PersonAddressResponseDto(person.getId(), person.getFullName(), person.getBirthdate(), address, person.getMainAddressId());
    }

    public PersonAddressResponseDto updatePerson(UUID id, PersonRequestDTO personRequestDTO) {

        Optional<Person> foundPerson = personRepository.findById(id);
        if (foundPerson.isEmpty()) {
            throw new PersonNotFoundException("Person not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        Person person = foundPerson.get();
        person.setFullName(personRequestDTO.fullName());
        person.setBirthdate(personRequestDTO.birthdate());
        person.setMainAddressId(personRequestDTO.mainAddressId());
        personRepository.save(person); // save new person's information

        // Recovering address person information to return as response
        Optional<Address> foundAddress = addressRepository.findById(personRequestDTO.mainAddressId());
        if (foundAddress.isEmpty()) {
            throw new AddressNotFoundException("Address not found with id: " + personRequestDTO.mainAddressId(), HttpStatus.NOT_FOUND);
        }

        return new PersonAddressResponseDto(person.getId(), person.getFullName(), person.getBirthdate(), foundAddress.get(), person.getMainAddressId());
    }

    public Person searchAllAddressByPerson(UUID id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public PersonAddressResponseDto createNewAddressForPerson(UUID personId, AddressDTO addressDTO) {

        Optional<Person> person = personRepository.findById(personId);
        if (person.isEmpty()) {
            throw new PersonNotFoundException("Person not found with id: " + personId, HttpStatus.NOT_FOUND);
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
