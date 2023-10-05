package com.example.chatapp4gui;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ChatScene {
    private final TextArea chatTextArea;
    private final TextField messageField;
    private final Button sendButton;
    private final Scene scene;
    private final VBox root;

    public ChatScene() {

        // Initialize UI components
        root = new VBox(10);
        chatTextArea = new TextArea();
        messageField = new TextField();
        sendButton = new Button("Send");

        root.getChildren().addAll(chatTextArea, messageField, sendButton);

        scene = new Scene(root, 600, 400);
    }

    public Scene getScene() {
        return scene;
    }

    public TextArea getChatTextArea() {
        return chatTextArea;
    }

    public TextField getMessageField() {
        return messageField;
    }



    public Button getSendButton() {
        return sendButton;
    }
}
