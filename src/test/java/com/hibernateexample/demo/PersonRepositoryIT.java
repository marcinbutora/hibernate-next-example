package com.hibernateexample.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PersonRepositoryIT {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void shouldCheckIfAddressIsSetToPerson() {
        //given
        Address addressOne = addressRepository.save(new Address("Żywiec", "Os. Młodych", "34-300"));
        Address addressTwo = addressRepository.save(new Address("Sosnowiec", "Błotna", "66-666"));
        Person personOne = personRepository.save(new Person("Marcin", "Butora", addressOne));
        Person personTwo = personRepository.save(new Person("John", "Smith", addressTwo));

        //when
        personRepository.saveAll(Arrays.asList(personOne, personTwo));

        //then
        assertThat(personRepository.findById(personOne.getId()).get().getAddress()).isEqualTo(addressOne);
    }

    @Test
    public void shouldCheckAddressIsChangedToPerson() {
        //given
        Person personOne = personRepository.save(new Person("Marcin", "Butora", new Address("Żywiec", "Os. Młodych", "34-300")));
        Address addressToUpdate = addressRepository.save(new Address("Bielsko-Biała", "Os. Starych", "43-300"));

        //when
        personOne.setAddress(addressToUpdate);
        personRepository.save(personOne);
        Optional<Person> foundedPerson = personRepository.findById(personOne.getId());

        //then
        assertThat(foundedPerson.get().getAddress()).isEqualTo(addressToUpdate);
    }
}