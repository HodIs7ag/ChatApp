package com.example.chatapp4gui;


import java.util.ArrayList;
import java.util.UUID;


//abstract class
public abstract class Room {
    private final String id = UUID.randomUUID().toString();
    private final String roomName;
    private final ArrayList<User> users;
    private final User admin;

    public Room(String roomName, User admin) {
        this.admin = admin;
        users = new ArrayList<>();
        this.users.add(admin);
        this.roomName = roomName;

    }

    //abstract method
    public abstract void addNewUser(User user);
    public abstract void displayRoomInfo(ChatScene scene);
    public String getId() {return this.id;}

    public ArrayList<User> getUsers() {
        return users;
    }


    public User getAdmin() {
        return admin;
    }

    public String getRoomName() {
        return roomName;
    }

    @Override
    public String toString() {
        return getRoomName();
    }
}
