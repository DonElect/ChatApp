package com.donatus.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        ServerServices server = new ServerServices(serverSocket);
        server.startServer();
    }
}
