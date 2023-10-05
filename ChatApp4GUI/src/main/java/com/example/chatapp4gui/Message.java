package com.example.chatapp4gui;

import java.io.Serializable;

public class Message implements Serializable {
    private final String content;
    private final User sender;
    private final String room;

    public Message(User sender, String room, String content) {
        this.sender = sender;
        this.room = room;
        this.content = content;
    }

    //Overload
    public Message(String room, String content) {
        this.sender = Server.getServerUser();
        this.room = room;
        this.content = content;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getRoom() {
        return room;
    }
}
