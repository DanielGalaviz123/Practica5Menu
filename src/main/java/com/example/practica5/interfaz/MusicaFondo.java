package com.example.practica5.interfaz;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class MusicaFondo {

    private MediaPlayer reproductor;

    public void iniciar() {

        URL archivo = getClass().getResource("/music/fondo.mp3");

        System.out.println("Ruta de la musica: " + archivo);

        if(archivo == null) {
            System.out.println("No se encontro el archivo mp3");
            return;
        }

        try {
            Media audio=new Media(archivo.toExternalForm());

            audio.setOnError(() -> {
                System.out.println("Error del archivo: " + audio.getError());
            });

            reproductor = new MediaPlayer(audio);

            reproductor.setCycleCount(MediaPlayer.INDEFINITE);
            reproductor.setVolume(0.65);

            // esperamos a que el archivo este listo
            reproductor.setOnReady(() -> {System.out.println("La musica esta lista");
                reproductor.play();
            });

            reproductor.setOnPlaying(() -> System.out.println("La musica se esta reproduciendo"));

            reproductor.setOnError(() -> {System.out.println("Error del reproductor: " + reproductor.getError());});

        } catch(Exception e) {
            System.out.println("No se pudo iniciar la musica");
            e.printStackTrace();
            reproductor=null;
        }
    }

    public void cambiarSonido() {

        if(reproductor == null){
            return;
        }

        reproductor.setMute(!reproductor.isMute());
    }

    public boolean disponible() {
        return reproductor != null && reproductor.getError() == null;
    }

    public String getEstado() {

        if(reproductor == null || reproductor.getError() != null) {
            return "SIN AUDIO";
        }

        if(reproductor.isMute()) {
            return "ACTIVAR";
        }

        return "SILENCIAR";
    }

    public void cerrar() {

        if(reproductor != null) {
            reproductor.stop();
            reproductor.dispose();
            reproductor=null;
        }
    }
}