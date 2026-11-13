package com.example.practica5.interfaz;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Estilo {

    public static final Color BLANCO = Color.web("#F5FCFF");
    public static final Color CYAN = Color.web("#58EFFF");
    public static final Color AZUL = Color.web("#0B3378");
    public static final Color OSCURO = Color.web("#071831");

    public static Background fondo(Paint color, double radio) {
        return new Background(new BackgroundFill(color, new CornerRadii(radio), Insets.EMPTY));
    }

    public static Label texto(String contenido, int tamaño, Color color, boolean negrita) {

        Label label = new Label(contenido);
        label.setTextFill(color);

        label.setFont(Font.font("Segoe UI", negrita ? FontWeight.BOLD : FontWeight.NORMAL, tamaño));

        return label;
    }

    public static Button boton(String contenido) {

        Button boton = new Button(contenido);

        boton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        boton.setTextFill(BLANCO);
        boton.setAlignment(Pos.CENTER_LEFT);

        boton.setPadding(new Insets(10, 12, 10, 12));
        boton.setMaxWidth(Double.MAX_VALUE);
        boton.setCursor(Cursor.HAND);

        boton.setBackground(fondo(AZUL, 6));

        return boton;
    }
}