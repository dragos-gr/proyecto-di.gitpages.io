package Controlador;

import General.Conversiones;
import General.General;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoControlador implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        General.ventanaInfoAbierta = false;
        stage.close();
    }
}
