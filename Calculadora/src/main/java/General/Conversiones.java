package General;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Conversiones {
    public static final String API_URL = "https://api.frankfurter.app/latest?from=%s&to=%s";
    public static final ObservableList<String> LISTA_UNIDADES = FXCollections.observableArrayList("Moneda",
            "Longitud",
            "Tiempo"
    );
    public static boolean ventanaConversionAbierta = false;

    //MONEDA
    public static final String[] UNIDADES_MONEDA = {"dólar", "euro", "libra", "yen", "yuan", "leu"};
    public static final ObservableList<String> LISTA_MONEDA = FXCollections.observableArrayList("dólar", "euro",
            "libra", "yen", "yuan", "leu");
    public static double[][] factoresMoneda = {
            {1, 0.85, 0.73, 110.99, 6.37, 4.18},        // dólar
            {1.18, 1, 0.86, 130.69, 7.54, 4.96},        // euro
            {1.37, 1.16, 1, 151.94, 8.76, 5.76},        // libra
            {0.009, 0.0076, 0.0066, 1, 0.058, 0.038},   // yen
            {0.157, 0.133, 0.115, 17.29, 1, 0.656},     // yuan
            {0.24, 0.203, 0.176, 26.65, 1.525, 1}       // leu
    };

    //LONGITUD
    public static final String[] UNIDADES_LONGITUD = {"centímetros", "pies", "metros", "yardas", "kilómetros", "millas"};
    public static final ObservableList<String> LISTA_LONGITUD = FXCollections.observableArrayList("centímetros",
            "pies", "metros", "yardas", "kilómetros", "millas");
    public static final double[][] FACTORES_LONGITUD = {
            {1, 0.0328084, 0.01, 0.0109361, 0.00001, 0.0000062137},  // Conversiones desde centímetros
            {30.48, 1, 0.3048, 0.333333, 0.0003048, 0.000189394},    // Conversiones desde pies
            {100, 3.28084, 1, 1.09361, 0.001, 0.000621371},          // Conversiones desde metros
            {91.44, 3, 0.9144, 1, 0.0009144, 0.000568182},           // Conversiones desde yardas
            {100000, 3280.84, 1000, 1093.61, 1, 0.621371},           // Conversiones desde kilómetros
            {160934, 5280, 1609.34, 1760, 1.60934, 1}                // Conversiones desde millas
    };

    //TIEMPO
    public static final String[] UNIDADES_TIEMPO = {"milisegundos", "segundos", "minutos", "horas", "días", "meses"};
    public static final ObservableList<String> LISTA_TIEMPO = FXCollections.observableArrayList("milisegundos",
            "segundos", "minutos", "horas", "días", "meses");
    public static final double[][] FACTORES_TIEMPO = {
            {1, 0.001, 1.66667e-5, 2.77778e-7, 1.15741e-8, 3.80517e-10},    // Conversiones desde milisegundos
            {1000, 1, 0.0166667, 0.000277778, 0.0000115741, 3.80517e-7},    // Conversiones desde segundos
            {60000, 60, 1, 0.0166667, 0.000694444, 2.28157e-5},             // Conversiones desde minutos
            {3.6e+6, 3600, 60, 1, 0.0416667, 0.00136986},                   // Conversiones desde horas
            {8.64e+7, 86400, 1440, 24, 1, 0.0328549},                       // Conversiones desde días
            {2.628e+9, 2.628e+6, 43829.1, 730.484, 30.4375, 1}              // Conversiones desde meses
    };

    /**
     * reestablece los valor a los iniciales
     */
    public static void cambiarValores() {
        factoresMoneda = new double[][]{
                {1, 0.85, 0.73, 110.99, 6.37, 4.18},        // dólar
                {1.18, 1, 0.86, 130.69, 7.54, 4.96},        // euro
                {1.37, 1.16, 1, 151.94, 8.76, 5.76},        // libra
                {0.009, 0.0076, 0.0066, 1, 0.058, 0.038},   // yen
                {0.157, 0.133, 0.115, 17.29, 1, 0.656},     // yuan
                {0.24, 0.203, 0.176, 26.65, 1.525, 1}       // leu
        };
    }

    /**
     * cambia los valores de las conversiones
     *
     * @param valorEuro
     * @param valorLibra
     * @param valorYen
     * @param valorYuan
     * @param valorLeu
     */
    public static void cambiarValores(double valorEuro, double valorLibra, double valorYen, double valorYuan, double valorLeu) {
        double[] filaDeReferencia = {1, valorEuro, valorLibra, valorYen, valorYuan, valorLeu};
        factoresMoneda = new double[filaDeReferencia.length][filaDeReferencia.length];
        for (int i = 0; i < filaDeReferencia.length; i++) {
            for (int j = 0; j < filaDeReferencia.length; j++) {
                factoresMoneda[i][j] = filaDeReferencia[i] / filaDeReferencia[j];
            }
        }
    }

    public static void abrirVentanaConversiones() throws IOException {
        if (!Conversiones.ventanaConversionAbierta) {
            FXMLLoader loader = new FXMLLoader(Conversiones.class.getResource("/Vista/conversiones.fxml"));
            Pane root = (Pane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Valores de conversión");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            //stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
            stage.getIcons().add(new Image(Conversiones.class.getResource("/ImagenesEIconos/icon_blue.png").toExternalForm()));
            stage.setOnCloseRequest(event -> {
                Conversiones.ventanaConversionAbierta = false;
            });
            stage.show();
            Conversiones.ventanaConversionAbierta = true;
        }
    }
}
