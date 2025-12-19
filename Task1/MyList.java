public abstract class MyList<T> {

    public abstract T get(int index);
    public abstract void set(int index, T value);
    public abstract int size();
    public abstract void add(T value);
}