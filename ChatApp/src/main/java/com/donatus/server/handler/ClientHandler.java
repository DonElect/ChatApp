package com.donatus.server.handler;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private ClientHandlerModel clientHandlerModel;
    public static ArrayList<ClientHandlerModel> clientHandlers = new ArrayList<>();

    public ClientHandler(ClientHandlerModel newClient){
        this.clientHandlerModel = newClient;
        clientHandlers.add(newClient);
        broadcastMessage("SEVER: " + newClient.getClientUserName() + " has entered the chat!");
    }

    @Override
    public void run() {
        String messageFromClient;

        while (clientHandlerModel.getSocket().isConnected()) {
            try {
                messageFromClient = clientHandlerModel.getBufferedReader().readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(clientHandlerModel.getSocket(),
                        clientHandlerModel.getBufferedReader(),
                        clientHandlerModel.getBufferedWriter());
                break;
            } catch (NullPointerException e) {
                removeClientHandlers();
                System.out.println("A client has disconnected!");
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandlerModel clientHandler : clientHandlers) {
            try {
                if (!clientHandler.getClientUserName().equals(this.clientHandlerModel.getClientUserName())) {
                    clientHandler.getBufferedWriter().write(messageToSend);
                    clientHandler.getBufferedWriter().newLine();
                    clientHandler.getBufferedWriter().flush();
                }
            } catch (IOException e) {
                closeEverything(clientHandlerModel.getSocket(),
                        clientHandlerModel.getBufferedReader(),
                        clientHandlerModel.getBufferedWriter());
            }
        }
    }

    public void removeClientHandlers() {
        clientHandlers.remove(this.clientHandlerModel);
        broadcastMessage("SERVER: " + this.clientHandlerModel.getClientUserName() + " has left the chat!");
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        removeClientHandlers();
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
