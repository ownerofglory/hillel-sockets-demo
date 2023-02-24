package ua.hillel;

import ua.hillel.client.EchoClient;

import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        DatagramSocket localhost = new DatagramSocket(8000, InetAddress.getByName("localhost"));

        try (EchoClient echoClient = new EchoClient("localhost", 8080)) {

            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello world!");
    }
}