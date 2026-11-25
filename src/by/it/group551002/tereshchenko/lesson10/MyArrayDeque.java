package by.it.group551002.tereshchenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class MyArrayDeque<E> implements Deque<E> {

    private E[] elements;
    private int size;
    private int head;
    private int tail;

    @SuppressWarnings("unchecked")
    public MyArrayDeque() {
        this.elements = (E[]) new Object[10];
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
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
    public Iterator<E> descendingIterator() {
        return null;
    }

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i < size; i++) {
            E element = elements[(head + i) % elements.length];
            result += element;
            if (i < size - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }

    @Override
    public boolean add(E element) {
        addLast(element);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity * 2;
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[(head + i) % oldCapacity];
        }
        elements = newElements;
        head = 0;
        tail = size;
    }

    @Override
    public void addFirst(E element) {
        if (size == elements.length) {
            resize();
        }
        head = (head - 1 + elements.length) % elements.length;
        elements[head] = element;
        size++;
    }

    @Override
    public void addLast(E element) {
        if (size == elements.length) {
            resize();
        }
        elements[tail] = element;
        tail = (tail + 1) % elements.length;
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        return false;
    }

    @Override
    public boolean offerLast(E e) {
        return false;
    }

    @Override
    public E removeFirst() {
        return null;
    }

    @Override
    public E removeLast() {
        return null;
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void push(E e) {

    }

    @Override
    public E pop() {
        return null;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        return elements[head];
    }

    @Override
    public E getLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }
        int lastIndex = (tail - 1 + elements.length) % elements.length;
        return elements[lastIndex];
    }

    @Override
    public E peekFirst() {
        return null;
    }

    @Override
    public E peekLast() {
        return null;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        return false;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E pollFirst() {
        if (size == 0) {
            return null;
        }
        E element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return element;
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        }
        tail = (tail - 1 + elements.length) % elements.length;
        E element = elements[tail];
        elements[tail] = null;
        size--;
        return element;
    }

}