package Controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/Vista/calculadora_main.fxml"));
        Pane rootPane = (Pane) loader.load();
        Scene scene = new Scene(rootPane, 360, 760);
        scene.getStylesheets().add(getClass().getResource("/Vista/style.css").toExternalForm());
        stage.getIcons().add(new Image(getClass().getResource("/ImagenesEIconos/icon_blue.png").toExternalForm()));
        stage.setTitle("Calculadora 7.3 de Dragos");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}