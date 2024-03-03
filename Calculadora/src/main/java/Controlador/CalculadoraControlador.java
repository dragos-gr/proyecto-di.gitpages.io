package Controlador;

import General.Conversiones;
import General.General;
import General.Operaciones;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CalculadoraControlador implements Initializable {
    @javafx.fxml.FXML
    private Button btnAtras;
    @javafx.fxml.FXML
    private Button btnDecimal;
    @javafx.fxml.FXML
    private Button btnCE;
    @javafx.fxml.FXML
    private Button btn8;
    @javafx.fxml.FXML
    private Button btn9;
    @javafx.fxml.FXML
    private Button btn6;
    @javafx.fxml.FXML
    private Button btn7;
    @javafx.fxml.FXML
    private Button btn4;
    @javafx.fxml.FXML
    private Button btn5;
    @javafx.fxml.FXML
    private Button btn2;
    @javafx.fxml.FXML
    private Button btn3;
    @javafx.fxml.FXML
    private Button btn0;
    @javafx.fxml.FXML
    private Button btn1;
    @javafx.fxml.FXML
    private Label lbDisplayPrincipal;
    private boolean borrarDisplay = false;
    private boolean botonesBloqueados = false;
    private String valorMemoriaActual = "0";
    @javafx.fxml.FXML
    private Label lbDisplaySecundario;
    @javafx.fxml.FXML
    private VBox vbox_principal;
    @javafx.fxml.FXML
    private Button btnSumar;
    @javafx.fxml.FXML
    private Button btnMMinus;
    @javafx.fxml.FXML
    private Button btn000;
    @javafx.fxml.FXML
    private Button btnCambioSigno;
    @javafx.fxml.FXML
    private Button btnC;
    @javafx.fxml.FXML
    private Button btnMultiplicar;
    @javafx.fxml.FXML
    private Button btnRestar;
    @javafx.fxml.FXML
    private Button btnM;
    @javafx.fxml.FXML
    private Button btnMC;
    @javafx.fxml.FXML
    private Button btnMPlus;
    @javafx.fxml.FXML
    private Button btnIgual;
    @javafx.fxml.FXML
    private Button btnDividir;
    @javafx.fxml.FXML
    private Label lbMemoriaActual;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnMC.setDisable(true);
        btnM.setDisable(true);
        lbMemoriaActual.setVisible(false);
    }

    @javafx.fxml.FXML
    public void agregarNumero(ActionEvent actionEvent) {
        String numeroPresionado = ((Button) actionEvent.getSource()).getText();
        auxiliarNumero(numeroPresionado);
    }

    @javafx.fxml.FXML
    public void agregarTresCeros(ActionEvent actionEvent) {
        if (!Operaciones.esIgualA0(lbDisplayPrincipal.getText()) && !borrarDisplay) {
            lbDisplayPrincipal.setText(lbDisplayPrincipal.getText() + "000");
        }
    }

    /**
     * BOTÓN C -> BORRA TODO
     *
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void borrarTodo(ActionEvent actionEvent) {
        if (botonesBloqueados) {
            bloquearBotones(false);
        }
        lbDisplaySecundario.setText("");
        lbDisplayPrincipal.setText("0");
    }

    /**
     * BOTÓN CE -> RESET DEL DISPLAY PRINCIPAL A 0
     *
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void borrarDisplayPrincipal(ActionEvent actionEvent) {
        lbDisplayPrincipal.setText("0");
    }

    /**
     * FLECHA PARA ATRÁS EN EL DISPLAY PRINCIPAL
     *
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void atrasDisplayPrincipal(ActionEvent actionEvent) {
        String textoDisplayPrincipal = lbDisplayPrincipal.getText();
        if (!Operaciones.esIgualA0(lbDisplayPrincipal.getText())) {
            if (textoDisplayPrincipal.length() == 1 || (textoDisplayPrincipal.length() == 2 && textoDisplayPrincipal.startsWith("-"))) {
                lbDisplayPrincipal.setText("0");
            } else {
                lbDisplayPrincipal.setText(textoDisplayPrincipal.substring(0, textoDisplayPrincipal.length() - 1));
            }
        }
    }

    @javafx.fxml.FXML
    public void cambiarSigno(ActionEvent actionEvent) {
        if (!Operaciones.esIgualA0(lbDisplayPrincipal.getText()) && !borrarDisplay) {
            String num = String.valueOf(Double.parseDouble(lbDisplayPrincipal.getText()) * -1);
            //no queremos que aparezcan números como 12.0, 33.0; queremos 12 ó 33
            if (num.endsWith(".0")) {
                num = num.substring(0, num.length() - 2);
            }
            if (num.equals("-0")) {
                lbDisplayPrincipal.setText("0");

            } else {
                lbDisplayPrincipal.setText(num);
            }
        }
    }

    @javafx.fxml.FXML
    public void agregarDecimal(ActionEvent actionEvent) {
        if (!lbDisplayPrincipal.getText().contains(".")) {
            lbDisplayPrincipal.setText(lbDisplayPrincipal.getText() + ".");
        }
    }

    @javafx.fxml.FXML
    public void agregarOperacion(ActionEvent actionEvent) {
        String operacionPresionada = ((Button) actionEvent.getSource()).getText();
        calcular((actionEvent));
        lbDisplaySecundario.setText(lbDisplayPrincipal.getText() + " " + operacionPresionada);
        borrarDisplay = true;
    }

    @javafx.fxml.FXML
    public void calcular(ActionEvent actionEvent) {
        if (lbDisplaySecundario.getText().contains("=") || lbDisplaySecundario.getText().isEmpty()) {
            return;
        }
        String numero1 = lbDisplaySecundario.getText().split(" ")[0];
        String numero2 = lbDisplayPrincipal.getText();
        String operacion = lbDisplaySecundario.getText().split(" ")[1];
        if ((numero2.equals("0") || numero2.equals("-0")) && (operacion.equals("/"))) {
            bloquearBotones(true);
            lbDisplaySecundario.setText("");
            lbDisplayPrincipal.setText("Divisón por cero");
            return;
        }
        if (!lbDisplaySecundario.getText().isEmpty()) {
            lbDisplaySecundario.setText(lbDisplaySecundario.getText() + " " + numero2 + " =");
            lbDisplayPrincipal.setText(Operaciones.operar(numero1, numero2, operacion));
        }
    }

    private void bloquearBotones(boolean bloquear) {
        botonesBloqueados = bloquear;
        btnSumar.setDisable(bloquear);
        btnMMinus.setDisable(bloquear);
        btn000.setDisable(bloquear);
        btn0.setDisable(bloquear);
        btn1.setDisable(bloquear);
        btn2.setDisable(bloquear);
        btn3.setDisable(bloquear);
        btn4.setDisable(bloquear);
        btn5.setDisable(bloquear);
        btn6.setDisable(bloquear);
        btn7.setDisable(bloquear);
        btn8.setDisable(bloquear);
        btn9.setDisable(bloquear);
        btnCambioSigno.setDisable(bloquear);
        btnMultiplicar.setDisable(bloquear);
        btnAtras.setDisable(bloquear);
        btnDecimal.setDisable(bloquear);
        btnRestar.setDisable(bloquear);
        btnM.setDisable(bloquear);
        btn0.setDisable(bloquear);
        btnCE.setDisable(bloquear);
        btnMC.setDisable(bloquear);
        btn3.setDisable(bloquear);
        btnMPlus.setDisable(bloquear);
        btnIgual.setDisable(bloquear);
        btnDividir.setDisable(bloquear);
    }

    @javafx.fxml.FXML
    public void mostrarMemoria(ActionEvent actionEvent) {
        if (lbMemoriaActual.isVisible()) {
            lbMemoriaActual.setVisible(false);
        } else {
            lbMemoriaActual.setVisible(true);
        }
    }

    @javafx.fxml.FXML
    public void memoriaPlus(ActionEvent actionEvent) {
        valorMemoriaActual = Operaciones.operar(valorMemoriaActual, lbDisplayPrincipal.getText(), "+");
        operarMemoria();
    }

    @javafx.fxml.FXML
    public void memoriaMinus(ActionEvent actionEvent) {
        valorMemoriaActual = Operaciones.operar(valorMemoriaActual, lbDisplayPrincipal.getText(), "-");
        operarMemoria();
    }

    @javafx.fxml.FXML
    public void borrarMemoria(ActionEvent actionEvent) {
        valorMemoriaActual = "0";
        lbMemoriaActual.setText("Memoria actual = " + valorMemoriaActual);
        btnM.setDisable(true);
        btnMC.setDisable(true);
        lbMemoriaActual.setText("");
    }

    private void operarMemoria() {
        lbMemoriaActual.setText("Memoria actual = " + valorMemoriaActual);
        if (btnM.isDisabled()) {
            btnM.setDisable(false);
            btnMC.setDisable(false);
        }
    }

    @javafx.fxml.FXML
    public void cambiarACientifica(ActionEvent actionEvent) throws IOException {
        VBox panel = FXMLLoader.load(getClass().getResource("/Vista/calculadora_cientifica.fxml"));

        //esto es el id del vbox principal que yo tenga, siguelo en casa
        vbox_principal.getChildren().setAll(panel);
        vbox_principal.getScene().getWindow().setWidth(540 + 16);
        vbox_principal.getScene().getWindow().setHeight(660 + 40);
    }

    @javafx.fxml.FXML
    public void keyPressed(KeyEvent event) {
        String botonPresionado = event.getText();
        KeyCode codigoPresionado = event.getCode();
        if (codigoPresionado == KeyCode.BACK_SPACE) {
            atrasDisplayPrincipal(new ActionEvent());
            return;
        }
        if (codigoPresionado == KeyCode.ESCAPE || codigoPresionado == KeyCode.C) {
            borrarTodo(new ActionEvent());
            return;
        }
        if (codigoPresionado == KeyCode.MINUS) {
            cambiarSigno(new ActionEvent());
            return;
        }
        if (botonPresionado.matches("[0-9]")) {
            auxiliarNumero(botonPresionado);
            return;
        }
        if (botonPresionado.equals(".") || botonPresionado.equals(",")) {
            agregarDecimal(new ActionEvent());
        }
    }

    private void auxiliarNumero(String numeroPresionado) {
        if (borrarDisplay) {
            lbDisplayPrincipal.setText(numeroPresionado);
            borrarDisplay = false;
        } else {
            if (Operaciones.esIgualA0(lbDisplayPrincipal.getText())) {
                lbDisplayPrincipal.setText(numeroPresionado);
            } else {
                lbDisplayPrincipal.setText(lbDisplayPrincipal.getText() + numeroPresionado);
            }
        }
    }

    @javafx.fxml.FXML
    public void cambiarAConversor(ActionEvent actionEvent) throws IOException {
        VBox panel = FXMLLoader.load(getClass().getResource("/Vista/calculadora_conversion.fxml"));
        vbox_principal.getChildren().setAll(panel);
        vbox_principal.getScene().getWindow().setWidth(420 + 12);
        vbox_principal.getScene().getWindow().setHeight(620 + 40);
    }

    @javafx.fxml.FXML
    public void abrirValoresConversion(ActionEvent actionEvent) throws IOException {
        Conversiones.abrirVentanaConversiones();
    }

    @javafx.fxml.FXML
    public void abrirInfo(ActionEvent actionEvent) throws IOException {
        General.abrirVentanaInformacion();
    }
}
