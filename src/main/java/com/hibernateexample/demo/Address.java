package com.hibernateexample.demo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String street;
    private String zip;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Person> personList;

    public Address() {
    }

    public Address(String city, String street, String zip) {
        this.city = city;
        this.street = street;
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPersonList(Set<Person> personList) {
        this.personList = personList;
    }

    public Long getId() {
        return id;
    }

    public Set<Person> getPersonList() {
        return personList;
    }
}
