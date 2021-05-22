package com.hibernateexample.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class AddressRepositoryIT {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void shouldReturnAddressListOfPerson() {
        //given
        Address address = new Address("Żywiec", "Os. Młodych", "43-300");
        Person father = new Person("Marcin", "Butora");
        Person sonYounger = new Person("Kacper", "Butora");
        Person sonOlder = new Person("Maciej", "Butora");
        Person daughter = new Person("Zuzanna", "Butora");
        Person mother = new Person("Anna", "Butora");

        //when
        address.setPersonList(new HashSet<>(Arrays.asList(father, sonOlder, sonYounger, daughter, mother)));
        addressRepository.save(address);

        //then
        assertThat(address.getPersonList().containsAll(Arrays.asList(father,sonOlder,sonYounger,daughter,mother))).isTrue();
        assertThat(addressRepository.findById(address.getId()).get().getPersonList()).containsExactlyInAnyOrder(father,sonOlder,sonYounger,daughter,mother);
    }

    @Test
    public void shouldChangeSurnameOfPersonGivenByAddress() {
        //given
        Address address = new Address("Żywiec", "Osiedle Młodych", "34-300");
        Person father = new Person("Marcin", "Butora");
        Person sonYounger = new Person("Kacper", "Butora");

        //when
        address.setPersonList(new HashSet<>(Arrays.asList(father,sonYounger)));
        Address savedAddress = addressRepository.save(address);
        Optional<Person> foundedPerson = savedAddress.getPersonList().stream()
                .filter(person -> person.getFirstname().equals("Kacper") && person.getLastname().equals("Butora"))
                .findFirst();
        foundedPerson.ifPresent(person -> person.setLastname("Kowalski"));
        addressRepository.save(address);

        //then
        assertThat(personRepository.findPersonByFirstnameAndLastname("Kacper", "Kowalski")).isPresent();
    }
}