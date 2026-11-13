package com.example.practica5.logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

public class LectorCSV {

    public CircularList<Carta> leer(File archivo) throws IOException {

        CircularList<Carta> cartas=new CircularList<>();

        try(BufferedReader lector=Files.newBufferedReader(archivo.toPath(), StandardCharsets.UTF_8)) {

            String encabezado=lector.readLine();

            if(encabezado == null) {
                throw new IOException("El archivo CSV esta vacio");
            }

            String[] columnas=separar(encabezado.replace("\uFEFF", ""));

            String[] esperadas={"nombre", "atributo", "tipo", "nivel",
                    "ataque", "defensa", "id", "codigo"
            };

            if(columnas.length != 8) {
                throw new IOException("Se esperaban 8 columnas separadas por comas. Encabezado: "+ encabezado);
            }

            for(int i=0; i<esperadas.length; i++) {

                if(!columnas[i].equalsIgnoreCase(esperadas[i])) {
                    throw new IOException("Columna incorrecta: " + columnas[i] + ". Se esperaba: " + esperadas[i]);
                }
            }

            String linea;
            int numLinea=1;

            while((linea=lector.readLine()) != null) {

                numLinea++;

                if(linea.isBlank()) continue;

                String[] datos=separar(linea);

                if(datos.length != 8 || datos[0].isBlank()) {
                    throw new IOException("Error en la linea " + numLinea);
                }

                String nombre=datos[0];
                String atributo=datos[1];
                String tipo=datos[2];

                int nivel=numero(datos[3]);
                int ataque=numero(datos[4]);
                int defensa=numero(datos[5]);

                String id=datos[6].trim();
                String imagen="";

                // obtenemos la direccion usando el id
                if(id.matches("\\d+")) {
                    imagen="https://images.ygoprodeck.com/images/cards/" + id + ".jpg";
                }

                Carta carta=new Carta(nombre, atributo, tipo, "", imagen, nivel, ataque, defensa);

                cartas.addLast(carta);
            }
        }

        return cartas;
    }


    // separa las columnas respetando las comas dentro de comillas
    private String[] separar(String linea) {

        ArrayList<String> campos=new ArrayList<>();
        StringBuilder texto=new StringBuilder();

        boolean comillas=false;

        for(int i=0; i<linea.length(); i++) {

            char c=linea.charAt(i);

            if(c=='"') {

                if(comillas && i+1<linea.length() && linea.charAt(i+1)=='"') {

                    texto.append('"');
                    i++;
                }
                else {
                    comillas=!comillas;
                }

            } else if(c==',' && !comillas) {

                campos.add(texto.toString().trim());
                texto.setLength(0);

            } else {
                texto.append(c);
            }
        }

        campos.add(texto.toString().trim());

        return campos.toArray(new String[0]);
    }


    private int numero(String texto) {

        if(texto.isBlank()){
            return -1;
        }

        try {
            return Integer.parseInt(texto.trim());

        } catch(NumberFormatException e) {
            return -1;
        }
    }
}