package com.example.chatapp4gui;


public class PrivateRoom extends Room{
    private static final int MAX_CAPACITY = 2;
    public PrivateRoom(String roomName,User admin) {
        super(roomName,admin);
    }

    public boolean isRoomFull() {
        return getUsers().size() >= MAX_CAPACITY;
    }

    @Override
    public void addNewUser(User user) {
        if (!isRoomFull()){
            getUsers().add(user);
        }
    }
    @Override
    public void displayRoomInfo(ChatScene scene){
        scene.getChatTextArea().appendText(
                "Private Room: "+ getRoomName()
                + "User1: " + getAdmin().getUsername()
                + "User2: "+ getUsers().get(1)
        );
    }

}
