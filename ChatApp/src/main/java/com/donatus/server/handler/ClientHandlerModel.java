package com.donatus.server.handler;

import lombok.Getter;

import java.io.*;
import java.net.Socket;

@Getter
public class ClientHandlerModel {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public ClientHandlerModel(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUserName = bufferedReader.readLine();
        } catch (IOException e) {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
                if (bufferedWriter != null)
                    bufferedWriter.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("Error! closing everything");
            }
        }
    }
}
