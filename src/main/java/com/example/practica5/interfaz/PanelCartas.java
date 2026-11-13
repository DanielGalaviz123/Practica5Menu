
package com.example.practica5.interfaz;

import com.example.practica5.logica.Carta;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.net.URL;

public class PanelCartas extends StackPane {

    private final VistaCarta anterior = new VistaCarta("ANTERIOR", false);
    private final VistaCarta actual = new VistaCarta("ACTUAL", true);
    private final VistaCarta siguiente = new VistaCarta("SIGUIENTE", false);

    private final Label contador = Estilo.texto("(0/0)", 19, Estilo.OSCURO, true);
    private final Label nombre = Estilo.texto("SELECCIONA UN ARCHIVO CSV", 20, Estilo.BLANCO, true);
    private final Label dato = Estilo.texto("", 14, Estilo.CYAN, true);
    private final Label descripcion = Estilo.texto("Carga un archivo", 12, Color.web("#D2E7F9"), false);

    private final FadeTransition aparecer = new FadeTransition(Duration.millis(240), actual);
    private final TranslateTransition mover = new TranslateTransition(Duration.millis(240), actual);


    public PanelCartas(Runnable irAnterior, Runnable irSiguiente) {

        colocarFondo();

        Rectangle sombra = new Rectangle();
        sombra.widthProperty().bind(widthProperty());
        sombra.heightProperty().bind(heightProperty());
        sombra.setFill(Color.web("#00112E", 0.22));
        sombra.setMouseTransparent(true);

        Label titulo = Estilo.texto("Cartas", 34, Estilo.BLANCO, true);

        contador.setPadding(new Insets(7, 17, 7, 17));
        contador.setBackground(Estilo.fondo(Estilo.CYAN, 6));

        HBox encabezado = new HBox(16, titulo, contador);
        encabezado.setAlignment(Pos.CENTER);

        anterior.setCursor(Cursor.HAND);
        siguiente.setCursor(Cursor.HAND);

        anterior.setOnMouseClicked(e -> irAnterior.run());
        siguiente.setOnMouseClicked(e -> irSiguiente.run());

        HBox cartas = new HBox(10,
                crearFlecha("❮", irAnterior),
                anterior, actual, siguiente,
                crearFlecha("❯", irSiguiente)
        );
        cartas.setAlignment(Pos.CENTER);

        nombre.setWrapText(true);
        dato.setWrapText(true);
        descripcion.setWrapText(true);
        descripcion.setMaxWidth(730);

        VBox informacion = new VBox(7, nombre, dato, descripcion);
        informacion.setMaxWidth(750);
        informacion.setMinHeight(115);
        informacion.setPadding(new Insets(15, 20, 15, 20));

        informacion.setBackground(Estilo.fondo(Color.web("#071B41", 0.92), 10));

        informacion.setBorder(new Border(new BorderStroke(
                Estilo.CYAN, BorderStrokeStyle.SOLID,
                new CornerRadii(10), new BorderWidths(4, 4, 4, 4)
        )));

        VBox contenido = new VBox(19, encabezado, cartas, informacion);
        contenido.setAlignment(Pos.CENTER);
        contenido.setPadding(new Insets(18, 12, 18, 12));

        getChildren().addAll(sombra, contenido);

        Rectangle recorte = new Rectangle();
        recorte.widthProperty().bind(widthProperty());
        recorte.heightProperty().bind(heightProperty());
        setClip(recorte);
    }


    private void colocarFondo() {

        URL ruta = getClass().getResource("/images/fondo_yugioh.png");

        if(ruta == null) {
            setBackground(Estilo.fondo(Color.web("#053A98"), 0));
            return;
        }

        Image imagen = new Image(ruta.toExternalForm());

        BackgroundSize tamaño = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                false, false, false, true
        );

        setBackground(new Background(new BackgroundImage(imagen, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, tamaño
        )));
    }


    private Button crearFlecha(String texto, Runnable accion) {

        Button boton= Estilo.boton(texto);

        boton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 21));
        boton.setAlignment(Pos.CENTER);
        boton.setPrefSize(39, 52);
        boton.setMinWidth(39);
        boton.setMaxWidth(39);

        boton.setOnAction(e -> accion.run());

        boton.setOnMouseEntered(e -> boton.setBackground(Estilo.fondo(Color.web("#1756B2"), 6)));

        boton.setOnMouseExited(e -> boton.setBackground(Estilo.fondo(Estilo.AZUL, 6)));

        return boton;
    }


    public void mostrar(Carta cartaAnterior, Carta cartaActual, Carta cartaSiguiente, String filtro, int posicion, int total) {

        anterior.mostrar(cartaAnterior, filtro);
        actual.mostrar(cartaActual, filtro);
        siguiente.mostrar(cartaSiguiente, filtro);

        contador.setText("(" + posicion + "/" + total + ")");

        if(cartaActual == null) {
            nombre.setText("NO HAY CARTAS");
            dato.setText("");
            descripcion.setText("No hay resultados con este filtro.");
            return;
        }

        nombre.setText(cartaActual.getNombre());

        // mostrar todos los datos sin importar el filtro
        dato.setText(
                "NIVEL: " + valor(cartaActual.getNivel())
                        + "   •   ATRIBUTO: " + cartaActual.getAtributo()
                        + "\nATK: " + valor(cartaActual.getAtaque())
                        + "   •   DEF: " + valor(cartaActual.getDefensa())
        );

        String detalle = cartaActual.getDescripcion();

        if(detalle == null || detalle.isBlank()) {
            descripcion.setText(cartaActual.getTipo() + "  •  " + cartaActual.getAtributo());
        } else {
            descripcion.setText(detalle);
        }

        animarCarta();
    }


    private void animarCarta() {

        aparecer.stop();
        mover.stop();

        aparecer.setFromValue(0.55);
        aparecer.setToValue(1);

        mover.setFromY(10);
        mover.setToY(0);

        aparecer.playFromStart();
        mover.playFromStart();
    }


    private String valor(int numero) {
        return numero < 0 ? "N/D" : String.valueOf(numero);
    }
}
