
package com.example.practica5.interfaz;

import com.example.practica5.logica.Carta;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;

public class VistaCarta extends VBox {

    private static final HashMap<String, Image> imagenes=new HashMap<>();

    private final ImageView imagen = new ImageView();

    private final Label aviso = new Label("SIN IMAGEN");
    private final Label nombre=new Label("Sin cartas");
    private final Label dato = new Label("");

    public VistaCarta(String titulo, boolean grande) {

        setAlignment(Pos.CENTER);
        setSpacing(grande ? 10 : 7);
        setPadding(new Insets(14, 10, 14, 10));

        setPrefWidth(grande ? 280 : 156);
        setMinWidth(grande ? 280 : 156);
        setMaxWidth(grande ? 280 : 156);

        Color color=Color.web(grande ? "#123F8B" : "#0B2859");
        Color borde=Color.web(grande ? "#55F1FF" : "#2C82C2");

        setBackground(Estilo.fondo(color, 12));

        setBorder(new Border(new BorderStroke(
                borde, BorderStrokeStyle.SOLID,
                new CornerRadii(12),
                new BorderWidths(grande ? 3 : 1)
        )));

        if(grande) {
            setEffect(new DropShadow(28, Color.web("#009DFF")));
        } else {
            setOpacity(0.86);
        }

        Label encabezado=Estilo.texto(titulo, grande ? 13 : 10, Estilo.CYAN, true);

        imagen.setFitWidth(grande ? 224 : 115);
        imagen.setFitHeight(grande ? 322 : 166);
        imagen.setPreserveRatio(true);
        imagen.setSmooth(true);

        aviso.setTextFill(Color.web("#B8D9EC"));
        aviso.setFont(Font.font("Segoe UI", 11));

        StackPane espacioImagen=new StackPane(aviso, imagen);
        espacioImagen.setAlignment(Pos.CENTER);
        espacioImagen.setMinHeight(grande ? 322 : 166);

        nombre.setTextFill(Color.WHITE);
        nombre.setFont(Font.font("Segoe UI", FontWeight.BOLD, grande ? 15 : 10));

        nombre.setWrapText(true);
        nombre.setMaxWidth(grande ? 250 : 130);
        nombre.setAlignment(Pos.CENTER);
        nombre.setTextAlignment(TextAlignment.CENTER);

        dato.setTextFill(Color.web("#77F5FF"));
        dato.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));

        dato.setWrapText(true);
        dato.setAlignment(Pos.CENTER);
        dato.setTextAlignment(TextAlignment.CENTER);
        dato.setMaxWidth(250);

        getChildren().addAll(encabezado, espacioImagen, nombre);

        if(grande) {
            getChildren().add(dato);
        }
    }


    public void mostrar(Carta carta, String filtro) {

        imagen.setImage(null);
        aviso.setText("SIN IMAGEN");
        aviso.setVisible(true);

        if(carta == null) {
            nombre.setText("Sin cartas");
            dato.setText("");
            return;
        }

        nombre.setText(carta.getNombre());

        // mostramos todos los datos sin importar el filtro
        dato.setText(
                "NIVEL " + valor(carta.getNivel())
                        + "   •   " + carta.getAtributo()
                        + "\nATK " + valor(carta.getAtaque())
                        + "   DEF " + valor(carta.getDefensa())
        );

        cargarImagen(carta.getImagen());
    }


    private void cargarImagen(String ruta) {

        if(ruta == null || ruta.isBlank()) {
            return;
        }

        try {

            Image foto=imagenes.get(ruta);

            if(foto == null) {
                foto = new Image(ruta, true);
                imagenes.put(ruta, foto);
            }

            imagen.setImage(foto);

            if(foto.isError()) {
                return;
            }

            if(foto.getProgress() >= 1) {
                aviso.setVisible(false);
                return;
            }

            aviso.setText("CARGANDO...");

            Image imagenActual = foto;

            imagenActual.progressProperty().addListener((obs, antes, ahora) -> {
                if(imagen.getImage() == imagenActual && ahora.doubleValue() >= 1) {
                    aviso.setVisible(imagenActual.isError());
                }
            });

            imagenActual.errorProperty().addListener((obs, antes, ahora) -> {
                if(imagen.getImage() == imagenActual && ahora) {
                    aviso.setText("SIN IMAGEN");
                    aviso.setVisible(true);
                }
            });

        } catch(Exception e) {
            aviso.setText("SIN IMAGEN");
        }
    }


    private String valor(int numero) {
        return numero < 0 ? "N/D" : String.valueOf(numero);
    }
}
