package com.donatus.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static com.donatus.CustomException.*;

import static com.donatus.CustomException.throwIOException;
import static org.junit.jupiter.api.Assertions.*;

class ClientServicesTest {
    private ClientModel clientModel;
    private ClientServices client;

    @BeforeEach
    void setUp() throws IOException {
        Socket socket = new Socket("localhost", 5000);
        clientModel = new ClientModel(socket, "Dona");
        client = new ClientServices(clientModel);
    }

    @Test
    @DisplayName("SUCCESSFULLY SENT MESSAGE")
    void sendMessage_SUCCESSFUL() {
        //assertEquals("MESSAGE-SENT", client.sendMessage());

    }

    @Test
    void listenForMessage() {
    }
}