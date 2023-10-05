module com.example.chatapp4gui {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.chatapp4gui to javafx.fxml;
    exports com.example.chatapp4gui;
}