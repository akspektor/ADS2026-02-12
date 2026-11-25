package by.it.group551002.tereshchenko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyHashSet<E> implements Set<E> {

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private static final int CAPACITY = 10;
    private Node<E>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public MyHashSet() {
        table = (Node<E>[]) new Node[CAPACITY];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private int getIndex(Object o) {
        if (o == null) {
            return 0;
        }
        int index = o.hashCode() % table.length;
        return index < 0 ? index + table.length : index;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o);
        Node<E> node = table[index];
        while (node != null) {
            if (node.data.equals(o)) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        int index = getIndex(e);
        Node<E> node = table[index];
        while (node != null) {
            if (node.data.equals(e)) {
                return false;
            }
            node = node.next;
        }
        table[index] = new Node<>(e, table[index]);
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = getIndex(o);
        Node<E> node = table[index];
        Node<E> prev = null;
        while (node != null) {
            if (node.data.equals(o)) {
                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return true;
            }
            prev = node;
            node = node.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String result = "[";
        boolean first = true;

        for (int i = 0; i < table.length; i++) {
            Node<E> node = table[i];

            while (node != null) {
                if (!first) {
                    result += ", ";
                }
                result += node.data;
                first = false;

                node = node.next;
            }
        }

        result += "]";
        return result;
    }
}