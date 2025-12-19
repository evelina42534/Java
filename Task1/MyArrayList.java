public class MyArrayList<T> extends MyList<T> {

    private Object[] data;
    private int size;

    public MyArrayList() {
        data = new Object[10];
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        @SuppressWarnings("unchecked")
        T value = (T) data[index];
        return value;
    }

    @Override
    public void set(int index, T value) {
        checkIndex(index);
        data[index] = value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void add(T value) {
        if (size >= data.length * 0.8) {
            int newCapacity = data.length * 2;
            Object[] newData = new Object[newCapacity];

            for (int i = 0; i < size; i++) {
                newData[i] = data[i];
            }

            data = newData;
        }

        data[size] = value;
        size++;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
    }

    public MyIterator<T> iterator() {
        return new MyArrayListIterator();
    }

    private class MyArrayListIterator implements MyIterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        public T next() {
            @SuppressWarnings("unchecked")
            T value = (T) data[currentIndex];
            currentIndex++;
            return value;
        }
    }
}
