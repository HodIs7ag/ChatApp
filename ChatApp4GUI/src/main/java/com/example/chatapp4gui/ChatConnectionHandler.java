package com.example.chatapp4gui;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;



public class ChatConnectionHandler implements ConnectionHandler, Runnable {

    private static final ArrayList<ChatConnectionHandler> userHandlers = new ArrayList<>();

    private static final ArrayList<Room> rooms = new ArrayList<>();


    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private User user;
    private Room currentRoom;
    private String roomType;

    public ChatConnectionHandler(Socket socket) {
        try {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());


            Message joinMessage = (Message) in.readObject();
            setUser(joinMessage.getSender());
            setRoomType(joinMessage.getContent());
            checkRoom(joinMessage.getRoom());

            userHandlers.add(this);

        } catch (Exception e) {
            closeConnection();
        }
    }


    @Override
    public void run() {
        Message messageFromUser;
        while (socket.isConnected()) {
            try {
                messageFromUser = (Message) in.readObject();
                broadcastMessage(messageFromUser);
            } catch (IOException | ClassNotFoundException e) {
                closeConnection();
                break;
            }
        }
    }

    @Override
    public void startReadingMessages() {
        Thread thread = new Thread(this);
        thread.start();

    }

    @Override
    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            closeConnection();
        }
    }

    @Override
    public void closeConnection() {
        userHandlers.remove(this);

        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error!!, Couldn't close the socket");
        }
    }





    public void broadcastMessage(Message messageToSend) {
        for (ChatConnectionHandler userHandler : userHandlers) {
            if (!userHandler.equals(this) && userHandler.getCurrentRoom().getId().equals(this.getCurrentRoom().getId())
                    && userHandler.getCurrentRoom().getRoomName().equals(getCurrentRoom().getRoomName())) {
                userHandler.sendMessage(messageToSend);
            }
        }
    }





    public void checkRoom(String roomName) {
        //if the room exist
        for (Room room : rooms){
            if (room.getRoomName().equals(roomName))
            {
                setCurrentRoom(room);
                getCurrentRoom().addNewUser(getUser());     //invokes addNewUser according to room Type (public, Private)
                System.out.println("room recalled" + getCurrentRoom()+ getUser());
                return;
            }
        }

        if (getRoomType().equals("Public")){
            setCurrentRoom(new PublicRoom(roomName, getUser()));
        }
        else if (getRoomType().equals("Private")) {
            setCurrentRoom(new PrivateRoom(roomName, getUser()));
        }
        System.out.println("room created"+ getCurrentRoom() + getUser());

        rooms.add(getCurrentRoom());


        }


    public void setUser(User user) {
        this.user = user;
    }

    public synchronized User getUser() {
        return this.user;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }


}
