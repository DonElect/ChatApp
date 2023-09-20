package com.donatus.client;

import lombok.NoArgsConstructor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

@NoArgsConstructor
public class Client {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username for the group chat: ");
        String username = sc.nextLine();

        try (Socket socket = new Socket("localhost", 5000)) {
            ClientModel clientModel = new ClientModel(socket, username);
            ClientServices client = new ClientServices(clientModel);
            client.listenForMessage();
            client.sendMessage();
        }
    }
}
