package by.it.group551002.tereshchenko.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private static class Node {
        int key;
        String value;
        boolean color;
        Node left;
        Node right;
        Node parent;

        Node(int key, String value, boolean color, Node parent) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.parent = parent;
        }
    }

    private Node root;
    private int size;

    private boolean colorOf(Node p) {
        return (p == null ? BLACK : p.color);
    }

    private Node parentOf(Node p) {
        return (p == null ? null : p.parent);
    }

    private void setColor(Node p, boolean c) {
        if (p != null) {
            p.color = c;
        }
    }

    private Node leftOf(Node p) {
        return (p == null ? null : p.left);
    }

    private Node rightOf(Node p) {
        return (p == null ? null : p.right);
    }

    private void rotateLeft(Node p) {
        if (p != null) {
            Node r = p.right;
            p.right = r.left;
            if (r.left != null) {
                r.left.parent = p;
            }
            r.parent = p.parent;
            if (p.parent == null) {
                root = r;
            } else if (p.parent.left == p) {
                p.parent.left = r;
            } else {
                p.parent.right = r;
            }
            r.left = p;
            p.parent = r;
        }
    }

    private void rotateRight(Node p) {
        if (p != null) {
            Node l = p.left;
            p.left = l.right;
            if (l.right != null) {
                l.right.parent = p;
            }
            l.parent = p.parent;
            if (p.parent == null) {
                root = l;
            } else if (p.parent.right == p) {
                p.parent.right = l;
            } else {
                p.parent.left = l;
            }
            l.right = p;
            p.parent = l;
        }
    }

    private void fixAfterInsertion(Node x) {
        x.color = RED;

        while (x != null && x != root && x.parent.color == RED) {
            if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                Node y = rightOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == rightOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateLeft(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateRight(parentOf(parentOf(x)));
                }
            } else {
                Node y = leftOf(parentOf(parentOf(x)));
                if (colorOf(y) == RED) {
                    setColor(parentOf(x), BLACK);
                    setColor(y, BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    x = parentOf(parentOf(x));
                } else {
                    if (x == leftOf(parentOf(x))) {
                        x = parentOf(x);
                        rotateRight(x);
                    }
                    setColor(parentOf(x), BLACK);
                    setColor(parentOf(parentOf(x)), RED);
                    rotateLeft(parentOf(parentOf(x)));
                }
            }
        }
        root.color = BLACK;
    }

    private Node getNode(Object key) {
        if (!(key instanceof Integer)) {
            return null;
        }
        int k = (Integer) key;
        Node p = root;
        while (p != null) {
            if (k < p.key) {
                p = p.left;
            } else if (k > p.key) {
                p = p.right;
            } else {
                return p;
            }
        }
        return null;
    }

    private Node successor(Node t) {
        if (t == null) {
            return null;
        } else if (t.right != null) {
            Node p = t.right;
            while (p.left != null) {
                p = p.left;
            }
            return p;
        } else {
            Node p = t.parent;
            Node ch = t;
            while (p != null && ch == p.right) {
                ch = p;
                p = p.parent;
            }
            return p;
        }
    }

    private void deleteNode(Node p) {
        size--;

        if (p.left != null && p.right != null) {
            Node s = successor(p);
            p.key = s.key;
            p.value = s.value;
            p = s;
        }

        Node replacement = (p.left != null ? p.left : p.right);

        if (replacement != null) {
            replacement.parent = p.parent;
            if (p.parent == null) {
                root = replacement;
            } else if (p == p.parent.left) {
                p.parent.left = replacement;
            } else {
                p.parent.right = replacement;
            }

            p.left = p.right = p.parent = null;

            if (p.color == BLACK) {
                fixAfterDeletion(replacement);
            }
        } else if (p.parent == null) {
            root = null;
        } else {
            if (p.color == BLACK) {
                fixAfterDeletion(p);
            }

            if (p.parent != null) {
                if (p == p.parent.left) {
                    p.parent.left = null;
                } else if (p == p.parent.right) {
                    p.parent.right = null;
                }
                p.parent = null;
            }
        }
    }

    private void fixAfterDeletion(Node x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                Node sib = rightOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }

                if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else {
                Node sib = leftOf(parentOf(x));

                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }

                if (colorOf(rightOf(sib)) == BLACK && colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }
        setColor(x, BLACK);
    }

    private void inOrder(Node p, StringBuilder sb) {
        if (p != null) {
            inOrder(p.left, sb);
            if (sb.length() > 1) {
                sb.append(", ");
            }
            sb.append(p.key).append("=").append(p.value);
            inOrder(p.right, sb);
        }
    }

    private boolean containsValue(Node node, Object value) {
        if (node == null) {
            return false;
        }
        if (value == null ? node.value == null : value.equals(node.value)) {
            return true;
        }
        return containsValue(node.left, value) || containsValue(node.right, value);
    }

    private void fillSubMap(Node node, MyRbMap target, Integer fromKey, Integer toKey) {
        if (node == null) {
            return;
        }
        if (fromKey == null || node.key >= fromKey) {
            fillSubMap(node.left, target, fromKey, toKey);
        }
        boolean afterOrAtFrom = (fromKey == null || node.key >= fromKey);
        boolean beforeTo = (toKey == null || node.key < toKey);
        if (afterOrAtFrom && beforeTo) {
            target.put(node.key, node.value);
        }
        if (toKey == null || node.key < toKey) {
            fillSubMap(node.right, target, fromKey, toKey);
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
        root = null;
        size = 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        return containsValue(root, value);
    }

    @Override
    public String get(Object key) {
        Node node = getNode(key);
        return node != null ? node.value : null;
    }

    @Override
    public String put(Integer key, String value) {
        Node t = root;
        if (t == null) {
            root = new Node(key, value, BLACK, null);
            size = 1;
            return null;
        }
        Node parent;
        int cmp;
        do {
            parent = t;
            cmp = key.compareTo(t.key);
            if (cmp < 0) {
                t = t.left;
            } else if (cmp > 0) {
                t = t.right;
            } else {
                String old = t.value;
                t.value = value;
                return old;
            }
        } while (t != null);

        Node e = new Node(key, value, BLACK, parent);
        if (cmp < 0) {
            parent.left = e;
        } else {
            parent.right = e;
        }
        fixAfterInsertion(e);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        Node p = getNode(key);
        if (p == null) {
            return null;
        }
        String oldValue = p.value;
        deleteNode(p);
        return oldValue;
    }

    @Override
    public String toString() {
        if (root == null) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder("{");
        inOrder(root, sb);
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Comparator<? super Integer> comparator() {
        return null;
    }

    @Override
    public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) {
        MyRbMap sub = new MyRbMap();
        fillSubMap(root, sub, fromKey, toKey);
        return sub;
    }

    @Override
    public SortedMap<Integer, String> headMap(Integer toKey) {
        return subMap(null, toKey);
    }

    @Override
    public SortedMap<Integer, String> tailMap(Integer fromKey) {
        return subMap(fromKey, null);
    }

    @Override
    public Integer firstKey() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        Node p = root;
        while (p.left != null) {
            p = p.left;
        }
        return p.key;
    }

    @Override
    public Integer lastKey() {
        if (root == null) {
            throw new NoSuchElementException();
        }
        Node p = root;
        while (p.right != null) {
            p = p.right;
        }
        return p.key;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends String> m) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Integer> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<Integer, String>> entrySet() {
        throw new UnsupportedOperationException();
    }
}