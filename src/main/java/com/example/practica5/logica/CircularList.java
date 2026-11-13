package com.example.practica5.logica;

public class CircularList<T> {

    private Node<T> first;
    private Node<T> last;
    private int size;

    public CircularList() {
        first=null;
        last=null;
        size = 0;
    }

    public void addFirst(T data) {

        Node<T> n = new Node<>(data);

        if (first == null) {
            first=n;
            last=n;
            n.setNext(first);
        }
        else {
            n.setNext(first);
            first = n;
            last.setNext(first);
        }

        size++;
    }

    public void addLast(T data) {

        Node<T> n = new Node<>(data);

        if(first == null) {
            first = n;
            last = n;
            n.setNext(first);
        } else {
            n.setNext(first);
            last.setNext(n);
            last=n;
        }

        size++;
    }

    public T removeFirst() {

        if(first == null)
            return null;

        T data = first.getData();

        if (first == last) {
            first = null;
            last = null;
        }
        else {
            last.setNext(first.getNext());
            first = first.getNext();
        }

        size--;
        return data;
    }

    public T removeLast() {

        if(first == null) return null;

        T data=last.getData();

        if(first == last) {
            first=null;
            last=null;

        } else {
            Node<T> r=first;

            // buscamos el nodo anterior al ultimo
            while(r.getNext() != last) {
                r = r.getNext();
            }

            last=r;
            last.setNext(first);
        }

        size--;
        return data;
    }

    public T get(int index) {

        if(index < 0 || index >= size)
            return null;

        Node<T> r = first;

        for(int i=0; i<index; i++) {
            r = r.getNext();
        }

        return r.getData();
    }

    public Node<T> getFirst() {
        return first;
    }

    public int getSize() {
        return size;
    }
}