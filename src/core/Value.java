package core;

/**
 * @author Simon Jern
 */
public class Value<T> {

    private T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public Value(T value){
        this.value = value;
    }
}
