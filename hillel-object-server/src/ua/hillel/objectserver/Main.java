package ua.hillel.objectserver;

import ua.hillel.objectserver.server.ObjectServer;
import ua.hillel.objectserver.service.PersonInMemoryService;
import ua.hillel.objectserver.service.PersonService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        PersonService personService = new PersonInMemoryService();
        try (ObjectServer server = new ObjectServer(8000, personService)) {

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        System.out.println("Hello world!");
    }
}