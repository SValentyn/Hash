import java.lang.reflect.Array;
import java.util.Arrays;

class List<T> {

    private T[] array;

    @SuppressWarnings("unchecked")
    List(Class<T> type, int size) {
        this.array = (T[]) Array.newInstance(type, size);
    }

    public T[] getArray() {
        return array;
    }

    public void add(T element) {
        if (!isFull()) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == null) {
                    array[i] = element;
                }
            }
        } else {
            resize();
            add(element);
        }
    }

    private boolean isFull() {
        for (T element : array) {
            if (element == null) return false;
        }
        return true;
    }

    private void resize() {
        this.array = Arrays.copyOf(array, array.length + 1);
    }
}
