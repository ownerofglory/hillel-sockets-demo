package ua.hillel.objectserver.service;

import ua.hillel.objectserver.model.Person;

import java.util.List;

public interface PersonService {
    List<Person> getPersons();
    void addPerson(Person person);
}
