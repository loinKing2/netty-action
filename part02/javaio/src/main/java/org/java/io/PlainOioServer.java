package org.java.io;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
// Old io
public class PlainOioServer {

    public static void main(String[] args) throws IOException {
        new PlainOioServer().serve(8996);
    }


    public void serve(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port);
        while (true) {
            try {
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                new Thread(() -> {
                    try (OutputStream out = clientSocket.getOutputStream()) {
                        out.write("Hi\n".getBytes(StandardCharsets.UTF_8));
                        out.flush();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
