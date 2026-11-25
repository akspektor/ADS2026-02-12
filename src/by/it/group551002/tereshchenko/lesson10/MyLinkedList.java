package by.it.group551002.tereshchenko.lesson10;

import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Deque<E> {

    private static class Node<E> {
        E data;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E data, Node<E> next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
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
        if (size == 0) {
            return "[]";
        }
        String result = "[";
        Node<E> curr = head;
        while (curr != null) {
            result += curr.data;
            if (curr.next != null) {
                result += ", ";
            }
            curr = curr.next;
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

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(null, element, head);

        if (head == null) {
            tail = newNode;
        } else {
            head.prev = newNode;
        }

        head = newNode;
        size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(tail, element, null);

        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }

        tail = newNode;
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

    private E unlink(Node<E> node) {
        E element = node.data;

        if (node.prev == null) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }

        node.data = null;
        size--;
        return element;
    }


    public E remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node<E> newNode = head;
        for (int i = 0; i < index; i++) {
            newNode = newNode.next;
        }

        return unlink(newNode);
    }

    @Override
    public boolean remove(Object o) {
        Node<E> newNode = head;
        for (int i = 0; i < size; i++) {
            if (newNode.data.equals(o)) {
                unlink(newNode);
                return true;
            }
            newNode = newNode.next;
        }

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
    public E getFirst() {
        if (size == 0) {
            return null;
        }
        return head.data;
    }

    @Override
    public E getLast() {
        if (size == 0) {
            return null;
        }
        return tail.data;
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
        if (head == null) {
            return null;
        }
        return unlink(head);
    }

    @Override
    public E pollLast() {
        if (tail == null) {
            return null;
        }
        return unlink(tail);
    }
}