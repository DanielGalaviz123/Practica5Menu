package com.example.practica5.logica;

public class Carta {

    private String nombre;
    private String atributo;
    private String tipo;
    private String descripcion, imagen;

    private int nivel;
    private int ataque, defensa;

    public Carta(String nombre, String atributo, String tipo, String descripcion,
                 String imagen, int nivel, int ataque, int defensa) {

        this.nombre=nombre;
        this.atributo = atributo;
        this.tipo=tipo;

        this.descripcion = descripcion;
        this.imagen= imagen;
        this.nivel = nivel;

        this.ataque=ataque;
        this.defensa = defensa;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAtributo() {
        return atributo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public int getNivel() {
        return nivel;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    @Override
    public String toString() {
        return nombre;
    }
}