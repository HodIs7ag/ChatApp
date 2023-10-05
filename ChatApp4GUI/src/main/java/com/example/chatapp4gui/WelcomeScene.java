package com.example.chatapp4gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class WelcomeScene {
    private final TextField usernameField;
    private final TextField roomNameField;
    private ComboBox<String> roomComboBox;
    private final Button startButton;

    public WelcomeScene(Stage primaryStage) {
        // Initialize UI components
        VBox root = new VBox(10);
        usernameField = new TextField();
        roomNameField = new TextField();
        roomComboBox = new ComboBox<>();
        startButton = new Button("Start Chat");

        roomComboBox = new ComboBox<>();
        roomComboBox.setPromptText("Select Room type"); // Set a prompt text

        // Populate the ComboBox with room options
        ObservableList<String> roomOptions = FXCollections.observableArrayList(
                "Public",
                "Private"
        );
        roomComboBox.setItems(roomOptions);

        root.getChildren().addAll(new Label("Enter your username:"), usernameField,
                new Label("Room name:"), roomNameField, roomComboBox,
                startButton);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome to ChatApp");

    }


    public String getUsername() {
        return usernameField.getText();
    }
    public String getRoomName() {
        return roomNameField.getText();
    }

    public Button getStartButton() {
        return startButton;
    }

    public ComboBox<String> getRoomComboBox() {
        return roomComboBox;
    }

    public String getRoomType(){
        return getRoomComboBox().getSelectionModel().getSelectedItem();
    }

}