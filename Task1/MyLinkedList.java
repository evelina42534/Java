public class MyLinkedList<T> extends MyList<T> {

    private Node<T> start;
    private Node<T> end;
    private int size;

    public MyLinkedList() {
        start = null;
        end = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node<T> current = getNode(index);
        return current.value;
    }

    @Override
    public void set(int index, T value) {
        checkIndex(index);
        Node<T> current = getNode(index);
        current.value = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value);

        if (start == null) {
            start = newNode;
            end = newNode;
        } else {
            end.next = newNode;
            end = newNode;
        }

        size++;
    }

    private Node<T> getNode(int index) {
        Node<T> current = start;
        int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public MyIterator<T> iterator() {
        return new MyLinkedListIterator();
    }

    private class MyLinkedListIterator implements MyIterator<T> {
        private Node<T> current = start;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T value = current.value;
            current = current.next;
            return value;
        }
    }
}
