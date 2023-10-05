package com.example.chatapp4gui;


public interface ConnectionHandler {
    void startReadingMessages();
    void sendMessage(Message message);
    void closeConnection();
}
