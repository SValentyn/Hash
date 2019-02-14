import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * Класс для работы с хэшированием с помощью массива, который не содержит повторений.
 * Реализованы способы получения хэша: умножением и делением.
 * Предусмотрена обработка коллизий.
 *
 * @author Syniuk Valentyn
 */
public class OperationsAndMain {

    private static List[] list = new List[22];

    public static void main(String[] args) {

        // HashSet - не содержит повторений
        HashSet<Integer> hashSet = new HashSet<>(10);
        while (hashSet.size() != 10) {
            Random random = new Random();
            hashSet.add(random.nextInt(1000) + 1);
        }

        // преобразуем HashSet в массив arrayInt
        Integer[] array = hashSet.toArray(new Integer[hashSet.size()]);
        System.out.print("\n\nRandom array: { ");
        for (Integer element : array)
            System.out.print(element + ", ");
        System.out.println("}");

        // задаём размеры хеш-таблиц в массив arrayHash
        int[] arrayHash = new int[]{10, 11, 16, 17, 32, 37, 64, 67};
        System.out.println("Size hash-table: { 10, 11, 16, 17, 32, 37, 64, 67 }");

        /**
         * Подсчитываем хэши методом деления
         */
        System.out.println("\n=== Division ===\n");
        for (Integer element : array) {
            int[] tempArray = new int[68]; // исп. для подсчёта коллизий (68 - длина исходного массива)
            for (int hash : arrayHash) {
                System.out.print("number = " + element + " & hash = " + hash + " -> " +
                        Division(element, hash) + " | ");
                tempArray[Division(element, hash)]++;
            }
            System.out.println();
            showCollisions(tempArray);
        }

        /**
         * Подсчитываем хэши методом умножения
         */
        System.out.println("\n=== Multiplication ===\n");
        for (Integer element : array) {
            int[] tempArray = new int[68]; // исп. для подсчёта коллизий (68 - длина исходного массива)
            for (int hash : arrayHash) {
                System.out.print("number = " + element + " & hash = " + hash + " -> " +
                        Multiplication(element, hash) + " | ");
                tempArray[Multiplication(element, hash)]++;
            }
            System.out.println();
            showCollisions(tempArray);
        }
    }

    private static void showCollisions(int[] tempArray) {
        for (int i = 0; i < tempArray.length; i++) {
            if (tempArray[i] > 1) { // одна коллизия для чисел из массива arrayInt будет в любом случае
                System.out.println("\thash = " + i + " -> collisions = " + tempArray[i]);
            }
        }
    }

    private static int Division(int k, int n) {  // способ деления
        return k % n;  // h(key) = key mod value;
    }

    private static int Multiplication(double k, double n) { // способ умножения
        return (int) (n * ((k * 0.6180339887) % 1));
    }

    // Метод умножения для строк
//    private static int Multiplication(String s) {
//        return (int) (13 * ((s.length() * 0.6180339887) % 1));
//        // or -> return s.length() * 13;
//    }

    // добавление в список (хеш-таблицу) значения по ключу
    private static void addToList(int key, int value) {
        if (!isFull()) {
            if (list[key] == null) {  // решение путём цепочки
                List<Integer> tempList = new List<>(Integer.class, 1);
                tempList.add(value);
                list[key] = tempList;
            } else {
                List<Integer> tempList = list[key];
                tempList.add(value);
                list[key] = tempList;
            }
        } else {  // линейным путём, если позволяет размер
            resize();
            addToList(key, value);
        }
    }

    private static boolean isFull() {
        for (List element : list) {
            if (element == null) return false;
        }
        return true;
    }

    private static void resize() {
        list = Arrays.copyOf(list, list.length + 1);
    }

    private static void print() {
        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) return;
            StringBuilder sb = new StringBuilder();
            for (Object o : list[i].getArray()) {
                sb.append(o).append(" ");
            }
            System.out.print(" hash = " + i + " : " + sb.toString() + "|");
        }
    }
}
