package Controlador;

import General.Conversiones;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ConversionesControlador implements Initializable {
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private Button btnAceptar;
    @javafx.fxml.FXML
    private CheckBox checkTiempo;
    @javafx.fxml.FXML
    private CheckBox checkLongitud;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Conversiones.LISTA_UNIDADES.contains("Longitud")) {
            checkLongitud.setSelected(true);
        }
        if (Conversiones.LISTA_UNIDADES.contains("Tiempo")) {
            checkTiempo.setSelected(true);
        }
    }

    @javafx.fxml.FXML
    public void aceptar(ActionEvent actionEvent) {
        modificarVistaConversiones();
        volver(actionEvent);
    }

    private void modificarVistaConversiones() {
        if (checkLongitud.isSelected()) {
            if (!Conversiones.LISTA_UNIDADES.contains("Longitud")) {
                Conversiones.LISTA_UNIDADES.add("Longitud");
            }
        } else {
            Conversiones.LISTA_UNIDADES.remove("Longitud");
        }
        if (checkTiempo.isSelected()) {
            if (!Conversiones.LISTA_UNIDADES.contains("Tiempo")) {
                Conversiones.LISTA_UNIDADES.add("Tiempo");
            }
        } else {
            Conversiones.LISTA_UNIDADES.remove("Tiempo");
        }
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        Conversiones.ventanaConversionAbierta = false;
        stage.close();
    }
}
