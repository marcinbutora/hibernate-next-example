package com.hibernateexample.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void shouldReturnListOfPersonLivingInGivenCity() {
        //given
        Person personOne = new Person("Marcin", "Butora");
        Person personTwo = new Person("Janek", "Kowalski");
        Person personThree = new Person("John", "Smith");
        Address addressOne = new Address("Katowice", "Sobieskiego", "44-100");
//        Address addressTwo = new Address("Żywiec", "Sobieskiego", "34-300");

        //when
        addressOne.setPersonList(new HashSet<Person>(Arrays.asList(personOne,personTwo,personThree)));
        addressRepository.save(addressOne);

        //then
        assertThat(addressRepository.findByCity("Katowice").get().getPersonList()).containsAll(Arrays.asList(personOne
                ,personTwo,personThree));
    }

}