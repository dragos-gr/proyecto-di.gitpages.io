module com.example.controlador {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens Controlador to javafx.fxml;
    exports Controlador;
}