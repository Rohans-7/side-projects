import java.util.*;
import java.util.concurrent.*;

class Main {
    public static void main(String[] args) {

        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Enter key and value (or -1 to exit):");

            int key = sc.nextInt();
            if (key == -1) break;

            int value = sc.nextInt();
            map.put(key, value);

            System.out.println("Inserted: " + key + " -> " + value);
        }

        System.out.println("Final Map: " + map);
    }
}
