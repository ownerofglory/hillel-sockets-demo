package ua.hillel.client;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.nio.Buffer;

public class EchoClient implements AutoCloseable {
    private Socket socket;

    public EchoClient(String host, int port) throws IOException {
        socket = new Socket(host, port);

        try (InputStream inputStream = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        ) {


            String response = reader.readLine();
            System.out.println(response);

        }
    }

    @Override
    public void close() throws Exception {
        if (socket.isClosed()) {
            return;
        }
        socket.close();
    }
}
