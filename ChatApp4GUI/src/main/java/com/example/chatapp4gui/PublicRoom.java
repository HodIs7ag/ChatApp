package com.example.chatapp4gui;


public class PublicRoom extends Room{


    public PublicRoom(String roomName,User admin) {
        super(roomName,admin);
    }

    public String members(){
        StringBuilder list = new StringBuilder();
        for (User user : getUsers())
            list.append(user).append("\n");
        return list.toString();
    }
    @Override
    public void addNewUser(User user) {
        getUsers().add(user);
    }

    @Override
    public void displayRoomInfo(ChatScene scene){
        scene.getChatTextArea().appendText(
                "Public Room: "+ getRoomName() +
                    "Members: "+ members()
        );
    }


}
