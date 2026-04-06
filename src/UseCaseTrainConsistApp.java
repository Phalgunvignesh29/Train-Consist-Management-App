import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * ========================================================
 * MAIN CLASS - UseCase7TrainConsistMgmt
 * ========================================================
 *
 * Use Case 7: Sort Bogies by Capacity (Comparator)
 *
 * Description:
 * This class sorts passenger bogies based on seating
 * capacity using a custom Comparator.
 *
 * At this stage, the application:
 * - Creates bogie objects
 * - Stores them in a List
 * - Displays unsorted data
 * - Sorts using Comparator logic
 * - Displays sorted result
 *
 * This maps custom ordering using Comparator.
 *
 * @author Developer
 * @version 7.0
 */
public class UseCaseTrainConsistApp {

    // Inner Bogie class to model passenger bogies
    static class Bogie {
        String name;
        int capacity;

        // Constructor
        Bogie(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }

        // toString for display
        @Override
        public String toString() {
            return name + " -> " + capacity;
        }
    }

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC7 - Sort Bogies by Capacity (Comparator) ");
        System.out.println("========================================\n");

        // Create list of passenger bogies
        List<Bogie> bogies = new ArrayList<>();

        // ---- Add bogie objects with name and capacity ----
        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("General", 90));

        // ---- Display before sorting ----
        System.out.println("Before Sorting:");
        for (Bogie bogie : bogies) {
            System.out.println(bogie);
        }

        // ---- Sort using Comparator by capacity (ascending) ----
        bogies.sort(Comparator.comparingInt(bogie -> bogie.capacity));

        // ---- Display after sorting ----
        System.out.println("\nAfter Sorting by Capacity:");
        for (Bogie bogie : bogies) {
            System.out.println(bogie);
        }

        System.out.println("\nUC7 sorting completed...");
    }
}