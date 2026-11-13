package com.example.practica5.logica;

import java.util.ArrayList;
import java.util.List;

public class GestorCartas {

    private CircularList<Carta> todas= new CircularList<>();
    private CircularList<Carta> porNivel= new CircularList<>();
    private CircularList<Carta> porAtributo = new CircularList<>();

    private CircularList<Carta> porAtaque =new CircularList<>();
    private CircularList<Carta> porDefensa =new CircularList<>();

    private DoubleCircularList<Carta> visibles = new DoubleCircularList<>();

    private DoubleNode<Carta> actual;
    private int posicion=0;


    public void cargar(CircularList<Carta> cartas) {

        todas = cartas;

        porNivel=new CircularList<>();
        porAtributo=new CircularList<>();

        porAtaque = new CircularList<>();
        porDefensa = new CircularList<>();

        Node<Carta> r=todas.getFirst();

        for(int i=0; i<todas.getSize(); i++) {

            Carta carta=r.getData();

            porAtaque.addLast(carta);
            porDefensa.addLast(carta);

            r=r.getNext();
        }

        mostrarTodas();
    }


    public void mostrarTodas() {
        cambiarLista(todas);
    }


    public void filtrarNivel(int nivel) {

        // quitamos los nodos del filtro anterior
        while(porNivel.getSize()>0) {
            porNivel.removeFirst();
        }

        Node<Carta> r = todas.getFirst();

        for(int i=0; i<todas.getSize(); i++) {

            Carta carta = r.getData();

            if(carta.getNivel()==nivel){
                porNivel.addLast(carta);
            }

            r=r.getNext();
        }

        cambiarLista(porNivel);
    }


    public void filtrarAtributo(String atributo) {

        while(porAtributo.getSize() > 0){
            porAtributo.removeFirst();
        }

        Node<Carta> r=todas.getFirst();

        for(int i=0; i<todas.getSize(); i++) {

            Carta carta=r.getData();

            if(atributo != null && atributo.equalsIgnoreCase(carta.getAtributo())) {

                porAtributo.addLast(carta);
            }

            r = r.getNext();
        }

        cambiarLista(porAtributo);
    }


    public void ordenarAtaque(boolean ascendente) {

        ordenar(porAtaque, true, ascendente);
        cambiarLista(porAtaque);
    }


    public void ordenarDefensa(boolean ascendente) {

        ordenar(porDefensa, false, ascendente);
        cambiarLista(porDefensa);
    }


    private void ordenar(CircularList<Carta> lista, boolean ataque, boolean ascendente) {

        Node<Carta> r = lista.getFirst();

        for(int i=0; i<lista.getSize()-1; i++) {

            Node<Carta> elegido = r;
            Node<Carta> aux=r.getNext();

            for(int j=i+1; j<lista.getSize(); j++) {

                if(comparar(aux.getData(), elegido.getData(), ataque, ascendente) < 0) {

                    elegido=aux;
                }

                aux=aux.getNext();
            }

            if(elegido != r) {

                // cambiamos las cartas de lugar sin modificar los enlaces
                Carta temp=r.getData();

                r.setData(elegido.getData());
                elegido.setData(temp);
            }

            r = r.getNext();
        }
    }


    private int comparar(Carta a, Carta b, boolean ataque, boolean ascendente) {

        int x = ataque ? a.getAtaque() : a.getDefensa();
        int y=ataque ? b.getAtaque() : b.getDefensa();

        // los valores no disponibles se dejan al final
        if(x < 0 && y >= 0){
            return 1;
        }


        if(y < 0 && x >= 0){
            return -1;
        }

        int resultado=Integer.compare(x,y);

        if(ascendente) {
            return resultado;
        }

        return -resultado;
    }


    private void cambiarLista(CircularList<Carta> lista) {

        // volvemos a llenar la doble circular con las cartas del filtro
        while(visibles.getSize()>0) {
            visibles.removeFirst();
        }

        Node<Carta> r=lista.getFirst();

        for(int i=0; i<lista.getSize(); i++) {

            visibles.addLast(r.getData());
            r=r.getNext();
        }

        actual=visibles.getFirst();

        if(actual == null){
            posicion=0;
        }

        else {
            posicion=1;
        }
    }


    public List<Integer> obtenerNiveles() {

        List<Integer> niveles=new ArrayList<>();

        Node<Carta> r=todas.getFirst();

        for(int i=0; i<todas.getSize(); i++) {

            int nivel=r.getData().getNivel();

            if(nivel >= 0 && !niveles.contains(nivel)) {
                niveles.add(nivel);
            }

            r=r.getNext();
        }

        niveles.sort(Integer::compareTo);
        return niveles;
    }


    public List<String> obtenerAtributos() {

        List<String> atributos = new ArrayList<>();
        Node<Carta> r=todas.getFirst();

        for(int i=0; i<todas.getSize(); i++) {

            String atributo = r.getData().getAtributo();

            if(atributo != null && !atributo.isBlank() && !atributos.contains(atributo)) {

                atributos.add(atributo);
            }

            r=r.getNext();
        }

        atributos.sort(String.CASE_INSENSITIVE_ORDER);

        return atributos;
    }


    public void siguiente() {

        if(actual == null){
            return;
        }

        actual=actual.getNext();
        posicion++;

        if(posicion > visibles.getSize()) {
            posicion=1;
        }
    }


    public void anterior() {

        if(actual == null) {
            return;
        }

        actual=actual.getPrevious();
        posicion--;

        if(posicion < 1) {
            posicion=visibles.getSize();
        }
    }


    public Carta getCartaActual() {

        if(actual == null) return null;

        return actual.getData();
    }


    public Carta getCartaAnterior() {

        if(actual == null) return null;

        return actual.getPrevious().getData();
    }


    public Carta getCartaSiguiente() {

        if(actual == null) return null;

        return actual.getNext().getData();
    }


    public int getTotal() {
        return visibles.getSize();
    }

    public int getTotalCartas() {
        return todas.getSize();
    }

    public int getPosicion() {
        return posicion;
    }
}