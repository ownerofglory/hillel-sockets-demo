package ua.hillel.datagramdemo.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalDateTime;

public class UdpEchoServer implements AutoCloseable {
    private DatagramSocket datagramSocket;

    public UdpEchoServer(int port) throws IOException {
        this.datagramSocket = new DatagramSocket(port);
        boolean isRunning = true;
       while (isRunning) {
           byte[] bytes = new byte[128];

           DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
           datagramSocket.receive(datagramPacket);
           InetAddress address = datagramPacket.getAddress();
           int senderPort = datagramPacket.getPort();
           byte[] data = datagramPacket.getData();
           String request = new String(data, 0, datagramPacket.getLength());

           String responseString = "Echo response: " + LocalDateTime.now();
           byte[] responseBytes = responseString.getBytes();

           DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, address, senderPort);
           datagramSocket.send(responsePacket);

           if (request.equalsIgnoreCase("-end")) {
               isRunning = false;
           }
       }
    }

    @Override
    public void close() throws Exception {
        if (datagramSocket.isClosed()) {
            return;
        }
        datagramSocket.close();
    }
}
