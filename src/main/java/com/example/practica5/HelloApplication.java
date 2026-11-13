package com.example.practica5;

import com.example.practica5.interfaz.VentanaPrincipal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {

        VentanaPrincipal ventana = new VentanaPrincipal();

        Scene escena = new Scene(ventana, 1240, 790);

        stage.setTitle("Yu-Gi-Oh");
        stage.setMinWidth(1100);
        stage.setMinHeight(760);

        stage.setScene(escena);
        stage.setOnCloseRequest(e -> ventana.cerrar());

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}