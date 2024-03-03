package Controlador;

import General.Conversiones;
import General.General;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConversorControlador implements Initializable {
    @javafx.fxml.FXML
    private Button btnCE;
    @javafx.fxml.FXML
    private Label lbDisplayPrincipal;
    @javafx.fxml.FXML
    private Label lbDisplaySecundario;
    @javafx.fxml.FXML
    private ComboBox cbConversion;
    @javafx.fxml.FXML
    private Button btnAtras;
    @javafx.fxml.FXML
    private Button btn8;
    @javafx.fxml.FXML
    private Button btnDecimal;
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
    private VBox vbox_principal;
    @javafx.fxml.FXML
    private Button btn3;
    @javafx.fxml.FXML
    private Button btn0;
    @javafx.fxml.FXML
    private ComboBox cbUnidad2;
    @javafx.fxml.FXML
    private Button btn1;
    @javafx.fxml.FXML
    private ComboBox cbUnidad1;
    private String unidad1 = "cm";
    private String unidad2 = "km";
    private String auxiliarConversion;
    private String unidad1PorDefecto;
    private String unidad2PorDefecto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbDisplayPrincipal.setText("");
        cbConversion.setItems(Conversiones.LISTA_UNIDADES);

        cbUnidad1.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    unidad1 = cambiarValor(newValue);
                } catch (NullPointerException ex) {
                    unidad1 = unidad1PorDefecto;
                }
                if (!lbDisplaySecundario.getText().isEmpty()) {
                    String aux = obtenerNumeroSinUnidad(lbDisplaySecundario.getText());
                    lbDisplaySecundario.setText(aux + " " + unidad1);
                    calcular();
                }
            }
        });
        cbUnidad2.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    unidad2 = cambiarValor(newValue);
                } catch (NullPointerException ex) {
                    unidad2 = unidad2PorDefecto;
                }

                calcular();
            }
        });
        cbConversion.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lbDisplayPrincipal.setText("");
                lbDisplaySecundario.setText("");
                switch (newValue) {
                    case "Moneda":
                        unidad1PorDefecto = "";
                        unidad2PorDefecto = "";
                        cbUnidad1.setItems(Conversiones.LISTA_MONEDA);
                        cbUnidad2.setItems(Conversiones.LISTA_MONEDA);
                        cbUnidad1.setValue(Conversiones.LISTA_MONEDA.get(0));
                        cbUnidad2.setValue(Conversiones.LISTA_MONEDA.get(1));
                        auxiliarConversion = "moneda";
                        break;
                    case "Longitud":
                        unidad1PorDefecto = "m";
                        unidad2PorDefecto = "cm";
                        cbUnidad1.setItems(Conversiones.LISTA_LONGITUD);
                        cbUnidad2.setItems(Conversiones.LISTA_LONGITUD);
                        cbUnidad1.setValue(Conversiones.LISTA_LONGITUD.get(2));
                        cbUnidad2.setValue(Conversiones.LISTA_LONGITUD.get(0));
                        auxiliarConversion = "longitud";
                        break;
                    case "Tiempo":
                        unidad1PorDefecto = "h";
                        unidad2PorDefecto = "m";
                        cbUnidad1.setItems(Conversiones.LISTA_TIEMPO);
                        cbUnidad2.setItems(Conversiones.LISTA_TIEMPO);
                        cbUnidad1.setValue(Conversiones.LISTA_TIEMPO.get(3));
                        cbUnidad2.setValue(Conversiones.LISTA_TIEMPO.get(2));
                        auxiliarConversion = "tiempo";
                        break;
                }
            }
        });
        cbConversion.setValue(Conversiones.LISTA_UNIDADES.get(0));
    }

    private String cambiarValor(String nuevoValor) {
        switch (nuevoValor.toLowerCase()) {
            case "dólar":
                return "usd";
            case "euro":
                return "eur";
            case "libra":
                return "gbp";
            case "yen":
                return "jpy";
            case "yuan":
                return "cny";
            case "leu":
                return "ron";
            case "milisegundos":
                return "ms";
            case "segundos":
                return "s";
            case "minutos", "metros":
                return "m";
            case "horas":
                return "h";
            case "días":
                return "d";
            case "meses":
                return "M";
            case "centímetros":
                return "cm";
            case "pies":
                return "ft";
            case "yardas":
                return "yd";
            case "kilómetros":
                return "km";
            case "millas":
                return "mi";
        }
        return null;
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
            borrarDisplayPrincipal(new ActionEvent());
            return;
        }
        if (botonPresionado.matches("[0-9]")) {
            auxiliarNumero(botonPresionado);
            calcular();
            return;
        }
        if (botonPresionado.equals(".") || botonPresionado.equals(",")) {
            agregarDecimal(new ActionEvent());
        }
    }

    private void auxiliarNumero(String numeroPresionado) {
        if (lbDisplaySecundario.getText().isEmpty()) {
            lbDisplaySecundario.setText(numeroPresionado + " " + unidad1);
        } else {
            String aux = obtenerNumeroSinUnidad(lbDisplaySecundario.getText());
            lbDisplaySecundario.setText(aux + numeroPresionado + " " + unidad1);
        }
    }

    @javafx.fxml.FXML
    public void agregarNumero(ActionEvent actionEvent) {
        String numeroPresionado = ((Button) actionEvent.getSource()).getText();
        auxiliarNumero(numeroPresionado);
        calcular();
    }

    @javafx.fxml.FXML
    public void borrarDisplayPrincipal(ActionEvent actionEvent) {
        lbDisplaySecundario.setText("");
        lbDisplayPrincipal.setText("");
    }

    @javafx.fxml.FXML
    public void atrasDisplayPrincipal(ActionEvent actionEvent) {
        String aux = obtenerNumeroSinUnidad(lbDisplaySecundario.getText());
        boolean borrarPunto = false;
        if (lbDisplaySecundario.getText().isEmpty()) {
            return;
        }
        try {
            if (aux.charAt(aux.length() - 2) == '.') {
                borrarPunto = true;
            }
        } catch (StringIndexOutOfBoundsException ex) {
        }
        if (borrarPunto) {
            aux = aux.substring(0, aux.length() - 2);
        } else {
            aux = aux.substring(0, aux.length() - 1);
        }
        if (obtenerNumeroSinUnidad(lbDisplaySecundario.getText()).length() == 1) {
            lbDisplaySecundario.setText("");
            lbDisplayPrincipal.setText("");
        } else {
            lbDisplaySecundario.setText(aux + " " + unidad1);
            calcular();
        }
    }

    @javafx.fxml.FXML
    public void agregarDecimal(ActionEvent actionEvent) {
        if (!lbDisplaySecundario.getText().contains(".") && !lbDisplaySecundario.getText().isEmpty()) {
            String aux = obtenerNumeroSinUnidad(lbDisplaySecundario.getText());
            lbDisplaySecundario.setText(aux + ". " + unidad1);
        }
    }

    @javafx.fxml.FXML
    public void cambiarACientifica(ActionEvent actionEvent) throws IOException {
        VBox panel = FXMLLoader.load(getClass().getResource("/Vista/calculadora_cientifica.fxml"));
        vbox_principal.getChildren().setAll(panel);
        vbox_principal.getScene().getWindow().setWidth(540 + 16);
        vbox_principal.getScene().getWindow().setHeight(660 + 40);
    }

    @javafx.fxml.FXML
    public void cambiarABasica(ActionEvent actionEvent) throws IOException {
        VBox panel = FXMLLoader.load(getClass().getResource("/Vista/calculadora_main.fxml"));
        vbox_principal.getChildren().setAll(panel);
        vbox_principal.getScene().getWindow().setWidth(360 + 16);
        vbox_principal.getScene().getWindow().setHeight(760 + 40);
    }

    private String obtenerNumeroSinUnidad(String numeroConUnidad) {
        return numeroConUnidad.split(" ")[0];
    }

    private void calcular() {
        if (lbDisplaySecundario.getText().isEmpty()) {
            return;
        }
        double factorConversion = obtenerFactorConversion();
        double numero = Double.parseDouble(obtenerNumeroSinUnidad(lbDisplaySecundario.getText()));
        String resultado = String.valueOf(numero * factorConversion);

        if (resultado.endsWith(".0")) {
            resultado = resultado.substring(0, resultado.length() - 2);
        }
        if (resultado.length() > 14 && resultado.contains(".")) {
            resultado = resultado.substring(0, 14);
        }
        lbDisplayPrincipal.setText(resultado + " " + unidad2);
    }

    private double obtenerFactorConversion() {
        if (auxiliarConversion.equals("moneda")) {
            try {
                return apiConversion();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (auxiliarConversion.equals("longitud")) {
            return ayudanteConversion(Conversiones.UNIDADES_LONGITUD, Conversiones.FACTORES_LONGITUD);
        } else if (auxiliarConversion.equals("tiempo")) {
            return ayudanteConversion(Conversiones.UNIDADES_TIEMPO, Conversiones.FACTORES_TIEMPO);
        }
        throw new NullPointerException();
    }

    private double apiConversion() throws IOException {
        String fromCurrency = unidad1.toUpperCase();
        String toCurrency = unidad2.toUpperCase();
        if (fromCurrency.equals(toCurrency)) {
            return 1;
        }
        String apiUrl = String.format(Conversiones.API_URL, fromCurrency, toCurrency);
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            JsonObject rates = jsonResponse.getAsJsonObject("rates");
            return rates.get(toCurrency).getAsDouble();
        } else {
            System.out.println("Failed to fetch conversion rate. Response code: " + responseCode);
            return -1;
        }
    }

    private double ayudanteConversion(String[] unidades, double[][] factores) {
        int indiceOrigen = -1;
        int indiceDestino = -1;
        for (int i = 0; i < unidades.length; i++) {
            if (unidades[i].equals(cbUnidad1.getValue().toString())) {
                indiceOrigen = i;
            }
            if (unidades[i].equals(cbUnidad2.getValue().toString())) {
                indiceDestino = i;
            }
        }
        return factores[indiceOrigen][indiceDestino];
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
