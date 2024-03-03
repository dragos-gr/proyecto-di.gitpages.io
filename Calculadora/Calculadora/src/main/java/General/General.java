package General;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class General {
    public static boolean ventanaInfoAbierta = false;

    public static void abrirVentanaInformacion() throws IOException {
        if (ventanaInfoAbierta == false) {
            FXMLLoader loader = new FXMLLoader(Conversiones.class.getResource("/Vista/info.fxml"));
            Pane root = (Pane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Información");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            //stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
            stage.getIcons().add(new Image(Conversiones.class.getResource("/ImagenesEIconos/icon_blue.png").toExternalForm()));
            stage.setOnCloseRequest(event -> {
                ventanaInfoAbierta = false;
            });
            stage.show();
            ventanaInfoAbierta = true;
        }
    }
}
