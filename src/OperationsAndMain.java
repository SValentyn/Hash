import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

/**
 * A class for working with hashing using an array that does not contain repetitions.
 * Implemented ways to get hash: multiplication and division. Collision handling is provided.
 *
 * @author Syniuk Valentyn
 */
public class OperationsAndMain {

    private static List[] list = new List[22];

    public static void main(String[] args) {

        // HashSet type collection - no repetitions
        HashSet<Integer> hashSet = new HashSet<>(10);
        while (hashSet.size() != 10) {
            Random random = new Random();
            hashSet.add(random.nextInt(1000) + 1);
        }

        // Convert the HashSet to an array
        Integer[] array = hashSet.toArray(new Integer[hashSet.size()]);
        System.out.print("\n\nRandom array: { ");
        for (Integer element : array) System.out.print(element + ", ");
        System.out.println("}");

        // Set the size of the hash tables in the arrayHash
        int[] arrayHash = new int[]{10, 11, 16, 17, 32, 37, 64, 67};
        System.out.println("Size hash-table: { 10, 11, 16, 17, 32, 37, 64, 67 }");

        // Calculate hashes by dividing
        System.out.println("\n=== Division ===\n");
        for (Integer element : array) {
            int[] tempArray = new int[68]; // used to calculate collisions (68 is the length of the original array)
            for (int hash : arrayHash) {
                System.out.print("number = " + element + " & hash = " + hash + " -> " +
                        Division(element, hash) + " | ");
                tempArray[Division(element, hash)]++;
            }
            System.out.println();
            showCollisions(tempArray);
        }

        // Calculate hashes by multiplying
        System.out.println("\n=== Multiplication ===\n");
        for (Integer element : array) {
            int[] tempArray = new int[68]; // used to calculate collisions (68 is the length of the original array)
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
            if (tempArray[i] > 1) { // The presence of one collision is guaranteed
                System.out.println("\thash = " + i + " -> collisions = " + tempArray[i]);
            }
        }
    }

    private static int Division(int k, int n) {
        return k % n;  // h(key) = key mod value;
    }

    private static int Multiplication(double k, double n) {
        return (int) (n * ((k * 0.6180339887) % 1));
    }

    /* Multiplication method for strings */
    private static int Multiplication(String s) {
        return (int) (13 * ((s.length() * 0.6180339887) % 1));
        // or easier -> return s.length() * 13;
    }

    /* Adding to the list (hash table) values by key */
    private static void addToList(int key, int value) {
        if (!isFull()) {
            if (list[key] == null) {  // chain collision resolution
                List<Integer> tempList = new List<>(Integer.class, 1);
                tempList.add(value);
                list[key] = tempList;
            } else {
                List<Integer> tempList = list[key];
                tempList.add(value);
                list[key] = tempList;
            }
        } else {  // linear collision resolution, if size allows
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
            StringBuilder builder = new StringBuilder();
            for (Object obj : list[i].getArray()) {
                builder.append(obj).append(" ");
            }
            System.out.print(" hash = " + i + " : " + builder.toString() + "|");
        }
    }
}
