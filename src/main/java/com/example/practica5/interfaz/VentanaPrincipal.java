package com.example.practica5.interfaz;

import com.example.practica5.logica.Carta;
import com.example.practica5.logica.CircularList;
import com.example.practica5.logica.GestorCartas;
import com.example.practica5.logica.LectorCSV;

import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;

public class VentanaPrincipal extends BorderPane {

    private final GestorCartas gestor= new GestorCartas();
    private final MusicaFondo musica= new MusicaFondo();

    private final PanelFiltros menu;
    private final PanelCartas visor;

    private String filtro = "todas";

    public VentanaPrincipal() {

        setBackground(Estilo.fondo(Estilo.OSCURO, 0));

        menu = new PanelFiltros(this::cargarArchivo, this::seleccionarFiltro, this::cambiarMusica);
        visor = new PanelCartas(() -> mover(false), () -> mover(true));

        setLeft(menu);
        setCenter(visor);
        musica.iniciar();
        menu.actualizarMusica(musica.getEstado(), musica.disponible());
    }


    private void cargarArchivo() {

        FileChooser selector= new FileChooser();
        selector.setTitle("Seleccionar cartas CSV");

        selector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos CSV", "*.csv"));

        File archivo=selector.showOpenDialog(getScene().getWindow());

        if(archivo== null) return;

        try {

            CircularList<Carta> cartas= new LectorCSV().leer(archivo);
            if(cartas.getSize() == 0) {
                aviso("No se encontraron cartas en el archivo.");
                return;
            }

            gestor.cargar(cartas);
            filtro="todas";

            menu.cargarFiltros(gestor.obtenerNiveles(), gestor.obtenerAtributos());
            menu.marcarFiltro("todas");
            actualizar();

        } catch(Exception e) {

            aviso("No se pudo cargar el archivo: "+ e.getMessage());
        }
    }


    private void seleccionarFiltro(String tipo) {

        if(gestor.getTotalCartas() == 0) {
            aviso("Primero debes cargar un archivo CSV.");
            return;
        }

        switch(tipo) {

            case "todas" -> gestor.mostrarTodas();

            case "nivel" -> {

                Integer nivel = menu.getNivel();

                if(nivel == null) {
                    aviso("Selecciona un nivel.");
                    return;
                }

                gestor.filtrarNivel(nivel);
            }

            case "atributo" -> {

                String atributo=menu.getAtributo();

                if(atributo == null) {
                    aviso("Selecciona un atributo.");
                    return;
                }

                gestor.filtrarAtributo(atributo);
            }

            case "ataque" -> gestor.ordenarAtaque(menu.ataqueAscendente());
            case "defensa" -> gestor.ordenarDefensa(menu.defensaAscendente());

            default -> {
                return;
            }
        }

        filtro=tipo;
        menu.marcarFiltro(tipo);

        actualizar();
    }


    private void mover(boolean siguiente) {

        if(gestor.getTotal() == 0) return;

        if(siguiente) {
            gestor.siguiente();
        }
        else {
            gestor.anterior();
        }

        actualizar();
    }


    private void actualizar() {

        visor.mostrar(
                gestor.getCartaAnterior(),
                gestor.getCartaActual(),
                gestor.getCartaSiguiente(),
                filtro,
                gestor.getPosicion(),
                gestor.getTotal()
        );
    }


    private void cambiarMusica() {

        musica.cambiarSonido();

        menu.actualizarMusica(musica.getEstado(), musica.disponible());
    }


    private void aviso(String mensaje) {

        Alert alerta= new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Yu-Gi-Oh! MP23");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }

    public void cerrar() {
        musica.cerrar();
    }
}