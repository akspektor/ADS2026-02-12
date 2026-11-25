package by.it.group551002.tereshchenko.lesson10;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MyPriorityQueue<E> implements Queue<E> {

    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public MyPriorityQueue() {
        this.elements = (E[]) new Object[10];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b) {
        return ((Comparable<? super E>) a).compareTo(b);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        E[] newElements = (E[]) new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }
        elements = newElements;
    }

    private void siftUp(int k) {
        E key = elements[k];
        while (k > 0) {
            int parent = (k - 1) / 2;
            if (compare(key, elements[parent]) >= 0) {
                break;
            }
            elements[k] = elements[parent];
            k = parent;
        }
        elements[k] = key;

    }

    private void siftDown(int k) {
        int left = 2 * k + 1;
        int right = 2 * k + 2;
        int small = k;
        if (left < size && compare(elements[left], elements[small]) < 0) {
            small = left;
        }
        if (right < size && compare(elements[right], elements[small]) < 0) {
            small = right;
        }

        if (small != k) {
            E temp = elements[k];
            elements[k] = elements[small];
            elements[small] = temp;
            siftDown(small);
        }
    }

    private void heapify() {
        for (int i = (size / 2) - 1; i >= 0; i--) {
            siftDown(i);
        }
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
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String res = "[";
        for (int i = 0; i < size; i++) {
            res += elements[i];
            if (i < size - 1) {
                res += ", ";
            }
        }
        res += "]";
        return res;
    }

    @Override
    public boolean add(E element) {
        return offer(element);
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean offer(E element) {
        if (element == null) {
            return false;
        }
        if (size == elements.length) {
            resize();
        }
        elements[size] = element;
        siftUp(size);
        size++;
        return true;
    }

    @Override
    public E poll() {
        if (size == 0) {
            return null;
        }
        E result = elements[0];
        E last = elements[size - 1];
        elements[size - 1] = null;
        size--;
        if (size > 0) {
            elements[0] = last;
            siftDown(0);
        }
        return result;
    }

    @Override
    public E remove() {
        E x = poll();
        if (x != null) {
            return x;
        }
        throw new NoSuchElementException();
    }

    @Override
    public E peek() {
        if (size == 0) {
            return null;
        }
        return elements[0];
    }

    @Override
    public E element() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return elements[0];
    }

    @Override
    public boolean contains(Object o) {
        if (o != null) {
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) {
                    return true;
                }
            }
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
    public boolean containsAll(Collection<?> c) {
        for (Object e : c) {
            if (!contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c.isEmpty()) {
            return false;
        }
        for (E item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;

        for (int i = 0; i < size; i++) {
            if (c.contains(elements[i])) {
                elements[i] = null;
                modified = true;
            }
        }

        if (!modified) {
            return false;
        }

        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (elements[i] != null) {
                elements[newSize++] = elements[i];
            }
        }

        for (int i = newSize; i < size; i++) {
            elements[i] = null;
        }
        size = newSize;

        heapify();
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;

        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                elements[i] = null;
                modified = true;
            }
        }

        if (!modified) {
            return false;
        }

        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (elements[i] != null) {
                elements[newSize++] = elements[i];
            }
        }

        for (int i = newSize; i < size; i++) {
            elements[i] = null;
        }
        size = newSize;

        heapify();
        return true;
    }
}