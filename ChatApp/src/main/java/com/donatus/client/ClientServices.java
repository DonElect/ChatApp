package com.donatus.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientServices {
    private ClientModel clientModel;

    public ClientServices(ClientModel clientModel){
        this.clientModel = clientModel;
    }

    public void sendMessage() {
        String result = "";
        try {
            clientModel.getBufferedWriter().write(clientModel.getUsername());
            clientModel.getBufferedWriter().newLine();
            clientModel.getBufferedWriter().flush();
            Scanner sc = new Scanner(System.in);
            while (clientModel.getSocket().isConnected()) {
                String messageToSend = sc.nextLine();
                if (!messageToSend.isEmpty()) {
                    clientModel.getBufferedWriter().write(clientModel.getUsername() + ": " + messageToSend);
                    clientModel.getBufferedWriter().newLine();
                    clientModel.getBufferedWriter().flush();
                    System.out.println("✓");
                } else System.out.println("Can not send an empty message!");
            }
        } catch (IOException e) {
            System.out.println("CONNECTION LOST!");
            closeEverything(clientModel.getSocket(), clientModel.getBufferedReader(), clientModel.getBufferedWriter());
        }
    }

    public void listenForMessage() {
        new Thread(() -> {
            String messageFromGroupChat;

            while (clientModel.getSocket().isConnected()) {
                try {
                    messageFromGroupChat = clientModel.getBufferedReader().readLine();
                    if (messageFromGroupChat != null)
                        System.out.println(messageFromGroupChat);
                } catch (IOException e) {
                    closeEverything(clientModel.getSocket(), clientModel.getBufferedReader(), clientModel.getBufferedWriter());
                    break;
                }
            }
        }).start();
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null)
                bufferedReader.close();
            if (bufferedWriter != null)
                bufferedWriter.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            System.out.println("Error! closing everything");
        }
    }
}
