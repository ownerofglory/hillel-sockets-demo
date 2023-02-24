package ua.hillel.objectserver.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import ua.hillel.objectserver.model.Person;
import ua.hillel.objectserver.service.PersonService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ObjectServer implements AutoCloseable {
    private ServerSocket serverSocket;
    private PersonService service;

    public ObjectServer(int port, PersonService personService) throws IOException {
        serverSocket = new ServerSocket(port);
        service = personService;

       while (true) {
           Socket clientSocket = serverSocket.accept();
           new Thread(() -> {
               try (OutputStream outputStream = clientSocket.getOutputStream();
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream));
                    InputStream in = clientSocket.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
               ) {

                   ObjectMapper objectMapper = new ObjectMapper();
                   String command;
                   do {

                       writer.println("Commands:");
                       writer.println("-get-all :get all persona");
                       writer.println("-add :add a person");
                       writer.flush();


                       command = bufferedReader.readLine();

                       if (command.equalsIgnoreCase("-get-all")) {
                           List<Person> persons = personService.getPersons();

                           String personsString = objectMapper.writeValueAsString(persons);
                           writer.println(personsString);
                           writer.flush();
                       } else if (command.equalsIgnoreCase("-add")) {
                           writer.println("Enter name");
                           writer.flush();

                           String name = bufferedReader.readLine();


                           writer.println("Enter age");
                           writer.flush();

                           String ageStr = bufferedReader.readLine();

                           Person person = new Person();
                           person.setName(name);
                           person.setAge(Integer.parseInt(ageStr));

                           service.addPerson(person);
                       } else if (command.equalsIgnoreCase("-add-json")) {
                           writer.println("Enter person in json format");
                           writer.flush();

                           String json = bufferedReader.readLine();
                           Person person = objectMapper.readValue(json, Person.class);

                           service.addPerson(person);
                       }

                   } while (!command.equalsIgnoreCase("-end"));

               } catch (IOException e) {
                   throw new RuntimeException(e);
               }

           }).start();
       }
    }

    @Override
    public void close() throws Exception {
        if (serverSocket.isClosed()) {
            return;
        }
        serverSocket.close();
    }
}
