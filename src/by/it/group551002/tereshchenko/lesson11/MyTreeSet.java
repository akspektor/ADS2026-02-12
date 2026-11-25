package by.it.group551002.tereshchenko.lesson11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MyTreeSet<E> implements Set<E> {

    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public MyTreeSet() {
        this.elements = (E[]) new Object[10];
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    private int binarySearch(Object o) {
        int low = 0;
        int high = size - 1;
        Comparable<? super E> key = (Comparable<? super E>) o;

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = key.compareTo(elements[mid]);

            if (cmp > 0) {
                low = mid + 1;
            } else if (cmp < 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -(low + 1);
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        E[] newArr = (E[]) new Object[elements.length * 2];
        for (int i = 0; i < size; i++) {
            newArr[i] = elements[i];
        }
        elements = newArr;
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
    public boolean contains(Object o) {
        if (o == null || size == 0) {
            return false;
        }
        return binarySearch(o) >= 0;
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
        if (e == null) {
            return false;
        }

        int index = binarySearch(e);
        if (index >= 0) {
            return false;
        }
        int insert = -(index + 1);

        if (size == elements.length) {
            resize();
        }
        for (int i = size; i > insert; i--) {
            elements[i] = elements[i - 1];
        }

        elements[insert] = e;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null || size == 0) {
            return false;
        }

        int index = binarySearch(o);
        if (index < 0) {
            return false;
        }

        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }

        elements[size - 1] = null;
        size--;
        return true;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        String result = "[";
        for (int i = 0; i < size; i++) {
            result += elements[i];
            if (i < size - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
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
        return true;
    }

}