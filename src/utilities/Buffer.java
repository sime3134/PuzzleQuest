package utilities;

import java.util.LinkedList;

public class Buffer<T> {
    private final LinkedList<T> buffer = new LinkedList<>();

    public void add(T obj) {
        buffer.addLast(obj);
    }
    public void addFirst(T obj) { buffer.addFirst(obj); }

    public T get()  {
        return buffer.removeFirst();
    }

    public T peek() { return buffer.peekFirst(); }

    public  void clear() {
        buffer.clear();
    }

    public int size() {
        return buffer.size();
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }
}
