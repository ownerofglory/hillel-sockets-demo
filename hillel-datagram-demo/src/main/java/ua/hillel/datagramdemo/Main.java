package ua.hillel.datagramdemo;

import ua.hillel.datagramdemo.server.UdpEchoServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (UdpEchoServer server = new UdpEchoServer(8000)) {

        } catch (IOException e) {


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello world!");
    }
}