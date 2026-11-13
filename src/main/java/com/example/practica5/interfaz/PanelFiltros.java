package com.example.practica5.interfaz;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.List;
import java.util.function.Consumer;

public class PanelFiltros extends VBox {

    private final ComboBox<Integer> comboNivel= new ComboBox<>();
    private final ComboBox<String> comboAtributo =new ComboBox<>();

    private final CheckBox ascAtaque= new CheckBox("Menor a mayor");
    private final CheckBox ascDefensa = new CheckBox("Menor a mayor");

    private final Button botonTodas;
    private final Button botonNivel;
    private final Button botonAtributo;
    private final Button botonAtaque;
    private final Button botonDefensa;
    private final Button botonSonido;

    private Button seleccionado;

    public PanelFiltros(Runnable cargarArchivo, Consumer<String> elegirFiltro, Runnable cambiarMusica) {

        Label titulo =Estilo.texto("YU-GI-OH", 27, Estilo.BLANCO, true);

        //titulo.setRotate(-3);

        Label subtitulo =Estilo.texto("25TH ANNIVERSARY", 12, Estilo.CYAN, true);
        Label seccion =Estilo.texto("Buscador", 13, Estilo.CYAN, true);

        Button botonCargar = Estilo.boton("+  CARGAR CSV");
        botonCargar.setBackground(Estilo.fondo(Estilo.CYAN, 7));
        botonCargar.setTextFill(Estilo.OSCURO);

        botonCargar.setOnMouseEntered(e -> botonCargar.setBackground(Estilo.fondo(Color.web("#A6F8FF"), 7)));
        botonCargar.setOnMouseExited(e -> botonCargar.setBackground(Estilo.fondo(Estilo.CYAN, 7)));
        botonCargar.setOnAction(e -> cargarArchivo.run());

        botonTodas = crearBotonFiltro("01   TODAS LAS CARTAS", "todas", elegirFiltro);
        botonNivel = crearBotonFiltro("02   POR NIVEL", "nivel", elegirFiltro);
        botonAtributo = crearBotonFiltro("03   POR ATRIBUTO", "atributo", elegirFiltro);
        botonAtaque = crearBotonFiltro("04   POR ATAQUE", "ataque", elegirFiltro);
        botonDefensa = crearBotonFiltro("05   POR DEFENSA", "defensa", elegirFiltro);

        comboNivel.setPromptText("Selecciona nivel");
        comboAtributo.setPromptText("Selecciona atributo");

        prepararCombo(comboNivel);
        prepararCombo(comboAtributo);

        ascAtaque.setSelected(true);
        ascDefensa.setSelected(true);

        ascAtaque.setTextFill(Estilo.BLANCO);
        ascDefensa.setTextFill(Estilo.BLANCO);

        botonSonido = Estilo.boton("SIN AUDIO");
        botonSonido.setOnAction(e -> cambiarMusica.run());

        setSpacing(10);
        setPadding(new Insets(24, 17, 18, 20));

        setPrefWidth(265);
        setMinWidth(265);

        setBackground(Estilo.fondo(new LinearGradient(
                0, 0, 0, 1, true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#07182F")),
                new Stop(1, Color.web("#123B85"))
        ), 0));

        getChildren().addAll(
                titulo,
                subtitulo,
                new Separator(),
                botonCargar,
                seccion,
                botonTodas,
                botonNivel,
                comboNivel,
                botonAtributo,
                comboAtributo,
                botonAtaque,
                ascAtaque,
                botonDefensa,
                ascDefensa,
                new Separator(),
                botonSonido
        );

        marcarFiltro("todas");
    }

    private Button crearBotonFiltro(String texto, String filtro, Consumer<String> elegirFiltro) {

        Button boton = Estilo.boton(texto);

        boton.setOnAction(e -> elegirFiltro.accept(filtro));

        boton.setOnMouseEntered(e -> {

            if (boton != seleccionado) {
                boton.setBackground(Estilo.fondo(Color.web("#1756B2"), 6));
            }
        });

        boton.setOnMouseExited(e -> {

            if (boton != seleccionado) {
                boton.setBackground(Estilo.fondo(Estilo.AZUL, 6));
            }
        });

        return boton;
    }

    private void prepararCombo(ComboBox<?> combo) {

        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setPrefHeight(34);

        combo.setBackground(Estilo.fondo(Color.web("#EBF9FF"), 6));
    }

    public void marcarFiltro(String filtro) {

        Button boton = switch (filtro) {

            case "todas" -> botonTodas;
            case "nivel" -> botonNivel;
            case "atributo" -> botonAtributo;
            case "ataque" -> botonAtaque;
            case "defensa" -> botonDefensa;

            default -> null;
        };

        if (boton == null) {
            return;
        }

        if (seleccionado != null) {
            seleccionado.setBackground(Estilo.fondo(Estilo.AZUL, 6));

            seleccionado.setTextFill(Estilo.BLANCO);
        }

        seleccionado = boton;
        seleccionado.setBackground(Estilo.fondo(Estilo.CYAN, 6));
        seleccionado.setTextFill(Estilo.OSCURO);
    }

    public void cargarFiltros(List<Integer> niveles, List<String> atributos) {

        comboNivel.getItems().setAll(niveles);
        comboAtributo.getItems().setAll(atributos);
        comboNivel.getSelectionModel().clearSelection();
        comboAtributo.getSelectionModel().clearSelection();
    }

    public Integer getNivel() {
        return comboNivel.getValue();
    }

    public String getAtributo() {
        return comboAtributo.getValue();
    }

    public boolean ataqueAscendente() {
        return ascAtaque.isSelected();
    }

    public boolean defensaAscendente() {
        return ascDefensa.isSelected();
    }

    public void actualizarMusica(String texto, boolean disponible) {

        botonSonido.setText(texto);
        botonSonido.setDisable(!disponible);
    }
}