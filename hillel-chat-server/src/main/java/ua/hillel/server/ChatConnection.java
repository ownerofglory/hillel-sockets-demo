package ua.hillel.server;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ChatConnection {
    private Socket socket;
    private ChatConnectionListener listener;
    private BufferedReader reader;
    private PrintWriter writer;
    private String name;

    public ChatConnection(Socket socket, ChatConnectionListener listener) throws IOException {
        this.socket = socket;
        this.listener = listener;

        InputStream inputStream = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));

        OutputStream outputStream = socket.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream));

        writer.println("Please enter your name:");
        writer.flush();

        name = reader.readLine();

        new Thread(() -> {
            listener.onConnect(ChatConnection.this);

            try {
                while (true) {
                    String message = reader.readLine();
                    if (message == null) {
                        break;
                    }
                    listener.onMessage(ChatConnection.this, message);
                }
            } catch (IOException e) {
                // throw new RuntimeException(e);
            } finally {
                listener.onDisconnect(ChatConnection.this);
            }
        }).start();
    }

    public String getName() {
        return name;
    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatConnection that = (ChatConnection) o;
        return Objects.equals(socket, that.socket) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, name);
    }
}
