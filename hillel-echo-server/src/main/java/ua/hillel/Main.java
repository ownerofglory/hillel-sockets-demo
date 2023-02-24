package ua.hillel;

import ua.hillel.server.EchoServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try(EchoServer echoServer = new EchoServer(8080)) {
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello world!");
    }
}