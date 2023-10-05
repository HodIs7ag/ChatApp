package com.example.chatapp4gui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatAppGUI extends Application implements ConnectionHandler {
    private Stage primaryStage;
    private WelcomeScene welcomeScene;
    private ChatScene chatScene;
    private User user;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String roomName;
    private String roomType;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        setWelcomeScene(new WelcomeScene(getPrimaryStage()));
        primaryStage.show();
        setChatScene(new ChatScene());

        // Set action for the start button in WelcomeScene

        getWelcomeScene().getStartButton().setOnAction(e -> {

            String username = getWelcomeScene().getUsername();
            setRoomName(getWelcomeScene().getRoomName());
            setRoomType(getWelcomeScene().getRoomType());

            setUser(new User(username));



            // Connect to Server and join room
            connectToServer();
            showChatScene();

            startReadingMessages();
            sendMessage(new Message(getUser(),getRoomName(), getRoomType()));
            sendMessage(new Message(getRoomName(), getUser().getUsername()+" Joined the room"));
        });


        //send button action
        getChatScene().getSendButton().setOnAction(e -> {
            String message = getChatScene().getMessageField().getText();

            getChatScene().getChatTextArea().appendText(getUser().getUsername() + ": " + message + "\n");

            // Send the message to the server
            sendMessage(new Message(getUser(), getRoomName(), message));

            // Clear the message field
            getChatScene().getMessageField().clear();
        });

        primaryStage.setOnCloseRequest(e -> {
            try {
                sendMessage(new Message(getRoomName(),
                        getUser().getUsername() + " Left the room"));
            } finally {
                closeConnection(); // Close the socket
                // Terminate the Java application
                System.exit(0);
            }



        });
    }

    public void connectToServer() {
        try {
            setSocket(new Socket("localhost", 1234));
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (UnknownHostException e) {
            getChatScene().getChatTextArea().appendText("UnknownHostException\n");
        } catch (IOException e) {
            getChatScene().getChatTextArea().appendText("Server is disconnected\n");
        }
    }

    @Override
    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            getChatScene().getChatTextArea().appendText("Error Sending message\nServer is disconnected\n");
            closeConnection();
        }
    }



    @Override
    public void startReadingMessages() {
        new Thread(() -> {
            Message messageFromServer;
            while (true) {
                try {
                    messageFromServer = (Message) in.readObject();
                    if (messageFromServer != null) {
                        getChatScene().getChatTextArea().appendText(messageFromServer.getSender().getUsername() +
                                ": " + messageFromServer.getContent() + "\n");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    getChatScene().getChatTextArea().appendText("Error receiving from Server");
                    closeConnection();
                }
            }
        }).start();
    }


    @Override
    public void closeConnection() {
        try {
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (getSocket() != null && !getSocket().isClosed()) {
                getSocket().close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error closing the Server Connection!: "+ e);
        }
    }


    public void showChatScene() {
        getPrimaryStage().setScene(getChatScene().getScene());
        getPrimaryStage().setTitle(getUser().getUsername()+" --- "+ getRoomName());
    }


    //get and set methods
    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setChatScene(ChatScene chatScene) {
        this.chatScene = chatScene;
    }

    public ChatScene getChatScene() {
        return chatScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public  void setUser(User user) {
        this.user = user;
    }
    public  User getUser() {
        return user;
    }

    public void setWelcomeScene(WelcomeScene welcomeScene) {
        this.welcomeScene = welcomeScene;
    }

    public WelcomeScene getWelcomeScene() {
        return welcomeScene;
    }


}


