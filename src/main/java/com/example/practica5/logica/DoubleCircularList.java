package com.example.practica5.logica;

public class DoubleCircularList<T> {

    private DoubleNode<T> first;
    private DoubleNode<T> last;
    private int size;

    public DoubleCircularList() {
        first=null;
        last=null;
        size=0;
    }

    public void addFirst(T data) {

        DoubleNode<T> n = new DoubleNode<>(data);

        if (first == null) {
            first=n;
            last=n;

            n.setNext(n);
            n.setPrevious(n);
        }
        else {
            n.setNext(first);
            n.setPrevious(last);

            first.setPrevious(n);
            last.setNext(n);
            first=n;
        }

        size++;
    }

    public void addLast(T data) {

        DoubleNode<T> n = new DoubleNode<>(data);

        if(first == null) {
            first = n;
            last=n;

            n.setNext(n);
            n.setPrevious(n);

        } else {
            n.setNext(first);
            n.setPrevious(last);

            last.setNext(n);
            first.setPrevious(n);
            last=n;
        }

        size++;
    }

    public T removeFirst() {

        if(first == null) return null;

        T data=first.getData();

        if(first == last) {
            first=null;
            last=null;
        } else {
            first=first.getNext();
            first.setPrevious(last);
            last.setNext(first);
        }

        size--;
        return data;
    }

    public T removeLast() {

        if(first == null)
            return null;

        T data = last.getData();

        if(first == last) {
            first=null;
            last=null;

        } else {
            last=last.getPrevious();

            last.setNext(first);
            first.setPrevious(last);
        }

        size--;
        return data;
    }


    public DoubleNode<T> getFirst() {
        return first;
    }

    public int getSize() {
        return size;
    }
}