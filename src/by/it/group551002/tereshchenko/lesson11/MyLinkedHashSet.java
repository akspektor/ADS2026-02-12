package by.it.group551002.tereshchenko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyLinkedHashSet<E> implements Set<E> {

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> before;
        Node<E> after;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private static final int CAPACITY = 16;
    private Node<E>[] table;
    private int size;
    private Node<E> head;
    private Node<E> tail;

    @SuppressWarnings("unchecked")
    public MyLinkedHashSet() {
        table = (Node<E>[]) new Node[CAPACITY];
        size = 0;
        head = null;
        tail = null;
    }

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
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        int index = getIndex(o);
        Node<E> node = table[index];
        while (node != null) {
            if (o.equals(node.data)) {
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
            if (e.equals(node.data)) {
                return false;
            }
            node = node.next;
        }

        Node<E> newNode = new Node<>(e, table[index]);
        table[index] = newNode;

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.after = newNode;
            newNode.before = tail;
            tail = newNode;
        }

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

                if (node.before == null) {
                    head = node.after;
                } else {
                    node.before.after = node.after;
                }

                if (node.after == null) {
                    tail = node.before;
                } else {
                    node.after.before = node.before;
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
        for (Object item : c) {
            if (!contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E item : c) {
            if (add(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;
        for (Object item : c) {
            if (remove(item)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        Node<E> curr = head;
        while (curr != null) {
            Node<E> next = curr.after;
            if (!c.contains(curr.data)) {
                remove(curr.data);
                modified = true;
            }
            curr = next;
        }
        return modified;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String result = "[";
        boolean first = true;

        Node<E> node = head;
        while (node != null) {
            if (!first) {
                result += ", ";
            }
            result += node.data;
            first = false;

            node = node.after;
        }

        result += "]";
        return result;
    }
}