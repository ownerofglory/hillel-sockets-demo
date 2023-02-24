package ua.hillel.objectserver.service;

import ua.hillel.objectserver.model.Address;
import ua.hillel.objectserver.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonInMemoryService implements PersonService {
    private List<Person> list = new ArrayList<>() {{
        Person person = new Person();
        person.setName("John");
        person.setAge(34);

        Address address = new Address();
        address.setCity("Odessa");
        address.setCity("Preobrzhenska");
        address.setZipCode(65001);
        address.setNumber(12);

        person.setAddress(address);

        add(person);
    }};

    @Override
    public List<Person> getPersons() {
        return list;
    }

    @Override
    public void addPerson(Person person) {
        list.add(person);
    }
}
