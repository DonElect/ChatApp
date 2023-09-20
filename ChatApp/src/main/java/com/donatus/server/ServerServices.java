package com.donatus.server;

import com.donatus.server.handler.ClientHandler;
import com.donatus.server.handler.ClientHandlerModel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerServices {
    private ServerSocket serverSocket;

    public ServerServices(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientHandlerModel newClient = new ClientHandlerModel(socket);
                ClientHandler clientHandler = new ClientHandler(newClient);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            closeServerSocket();
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("IOException " + e);
        }
    }
}
