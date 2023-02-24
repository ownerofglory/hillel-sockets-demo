package ua.hillel.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer implements ChatConnectionListener, AutoCloseable {
    private final ServerSocket serverSocket;
    private final List<ChatConnection> connections = new ArrayList<>();

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);

        while (true) {
            Socket socket = serverSocket.accept();
            ChatConnection chatConnection = new ChatConnection(socket, this);
        }
    }

    @Override
    public void close() throws Exception {
        if (serverSocket.isClosed()) {
            return;
        }
        serverSocket.close();
    }

    @Override
    public void onConnect(ChatConnection connection) {
        System.out.println("Entered the chat: " + connection);
        for (ChatConnection chatConnection : connections) {
            chatConnection.sendMessage(String.format("-- [%s] entered the chat%n", connection.getName()));
        }


        connections.add(connection);
    }

    @Override
    public void onMessage(ChatConnection connection, String message) {
        for (ChatConnection chatConnection : connections) {
            if (!chatConnection.equals(connection)) {
                chatConnection.sendMessage(String.format("[%s]:%s%n", connection.getName(), message));
            }
        }

    }

    @Override
    public void onDisconnect(ChatConnection connection) {
        System.out.println("Left the chat: " + connection);
        for (ChatConnection chatConnection : connections) {
            chatConnection.sendMessage(String.format("-- [%s] left the chat%n", connection.getName()));
        }

        connections.remove(connection);
    }

}
