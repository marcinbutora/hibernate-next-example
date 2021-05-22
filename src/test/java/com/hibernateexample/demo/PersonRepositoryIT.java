package com.hibernateexample.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PersonRepositoryIT {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void shouldCheckIfAddressIsSavedToPerson() {
        //given
        Person person = personRepository.save(new Person("Marcin", "Butora"));
        Address address = addressRepository.save(new Address("Żywiec", "Os. Młodych", "34-300"));

        //when
        person.setAddress(address);
        Person savedPerson = personRepository.save(person);

        //then
        assertThat(savedPerson.getAddress()).isEqualTo(address);
        assertThat(addressRepository.findById(address.getId()).get()).isEqualTo(savedPerson.getAddress());
    }

    @Test
    public void shouldCheckIfAddressIsSavedToPersonVer2() {
        //given
        Person person = personRepository.save(new Person("Marcin", "Butora", new Address("Żywiec", "1 Maja", "34-300")));
        Address address = addressRepository.save(new Address("Bielsko-Biała", "2 Maja", "43-300"));

        //when
        person.setAddress(address);
        Person savedPerson = personRepository.save(person);

        //then
        assertThat(savedPerson.getAddress()).isEqualTo(address);
    }
}