import java.util.ArrayList;
import java.util.List;

/**
 * ========================================================
 * MAIN CLASS - UseCase10TrainConsistMgmt
 * ========================================================
 *
 * Use Case 10: Count Total Seats in Train
 *
 * Description:
 * This class aggregates seating capacity of all bogies
 * into a single total using Stream reduce().
 *
 * At this stage, the application:
 * - Creates bogie list
 * - Maps bogies to capacity
 * - Reduces values into total
 * - Displays total seat count
 *
 * This maps aggregation logic using reduce().
 *
 * @author Developer
 * @version 10.0
 */
public class UseCaseTrainConsistApp {

    // Reusing Bogie model
    static class Bogie {
        String name;
        int capacity;

        Bogie(String name, int capacity) {
            this.name = name;
            this.capacity = capacity;
        }

        @Override
        public String toString() {
            return name + " -> " + capacity;
        }
    }

    /**
     * Aggregates total seating capacity of all bogies.
     * Uses map() to extract capacity, reduce() to sum.
     * Returns 0 for empty list. Original list unchanged.
     */
    public static int totalSeatingCapacity(List<Bogie> bogies) {
        return bogies.stream()
                .map(b -> b.capacity)
                .reduce(0, Integer::sum);
    }

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC10 - Count Total Seats in Train ");
        System.out.println("========================================\n");

        // Create list of bogies
        List<Bogie> bogies = new ArrayList<>();

        bogies.add(new Bogie("Sleeper",     72));
        bogies.add(new Bogie("AC Chair",    56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("Sleeper",     70));

        // Display bogies
        System.out.println("Bogies in Train:");
        for (Bogie b : bogies) {
            System.out.println(b);
        }

        // ---- AGGREGATE USING REDUCE ----
        // map() extracts capacity field from Bogie object
        int totalCapacity = totalSeatingCapacity(bogies);

        System.out.println("\nTotal Seating Capacity of Train: " + totalCapacity);
        System.out.println("\nUC10 aggregation completed...");
    }


    // ========================================================
    // INNER TEST CLASS - UseCase10TrainConsistMgmtTest
    // ========================================================

    public static class UseCase10TrainConsistMgmtTest {

        // Helper: standard bogie list (total = 72+56+24+70 = 222)
        private List<Bogie> createBogieList() {
            List<Bogie> bogies = new ArrayList<>();
            bogies.add(new Bogie("Sleeper",     72));
            bogies.add(new Bogie("AC Chair",    56));
            bogies.add(new Bogie("First Class", 24));
            bogies.add(new Bogie("Sleeper",     70));
            return bogies;
        }

        public void testReduce_TotalSeatCalculation() {
            List<Bogie> bogies = createBogieList();
            int total = totalSeatingCapacity(bogies);
            // 72 + 56 + 24 + 70 = 222
            assert total == 222
                    : "Expected total 222, got: " + total;
            System.out.println("PASS: testReduce_TotalSeatCalculation");
        }

        public void testReduce_MultipleBogiesAggregation() {
            List<Bogie> bogies = new ArrayList<>();
            bogies.add(new Bogie("Sleeper",  50));
            bogies.add(new Bogie("AC Chair", 50));
            bogies.add(new Bogie("General",  100));
            int total = totalSeatingCapacity(bogies);
            // 50 + 50 + 100 = 200
            assert total == 200
                    : "Expected total 200, got: " + total;
            System.out.println("PASS: testReduce_MultipleBogiesAggregation");
        }

        public void testReduce_SingleBogieCapacity() {
            List<Bogie> bogies = new ArrayList<>();
            bogies.add(new Bogie("First Class", 24));
            int total = totalSeatingCapacity(bogies);
            // Single bogie — total must equal its own capacity
            assert total == 24
                    : "Expected total 24, got: " + total;
            System.out.println("PASS: testReduce_SingleBogieCapacity");
        }

        public void testReduce_EmptyBogieList() {
            List<Bogie> bogies = new ArrayList<>();
            int total = totalSeatingCapacity(bogies);
            // Identity value of reduce(0, ...) must be returned
            assert total == 0
                    : "Expected total 0 for empty list, got: " + total;
            System.out.println("PASS: testReduce_EmptyBogieList");
        }

        public void testReduce_CorrectCapacityExtraction() {
            List<Bogie> bogies = new ArrayList<>();
            bogies.add(new Bogie("Sleeper", 72));
            bogies.add(new Bogie("Sleeper", 72));
            int total = totalSeatingCapacity(bogies);
            // map() must extract 72 from each bogie — 72 + 72 = 144
            assert total == 144
                    : "Expected total 144, got: " + total;
            System.out.println("PASS: testReduce_CorrectCapacityExtraction");
        }

        public void testReduce_AllBogiesIncluded() {
            List<Bogie> bogies = createBogieList();
            int total = totalSeatingCapacity(bogies);
            // Manual sum to verify every bogie is counted
            int expected = bogies.stream()
                    .mapToInt(b -> b.capacity)
                    .sum();
            assert total == expected
                    : "Total must match sum of all bogies. Expected: "
                    + expected + ", got: " + total;
            System.out.println("PASS: testReduce_AllBogiesIncluded");
        }

        public void testReduce_OriginalListUnchanged() {
            List<Bogie> bogies = createBogieList();
            int originalSize = bogies.size();
            totalSeatingCapacity(bogies);
            // Stream must not mutate the source list
            assert bogies.size() == originalSize
                    : "Original list size must not change";
            assert bogies.get(0).name.equals("Sleeper")
                    : "Index 0 must still be Sleeper";
            assert bogies.get(1).name.equals("AC Chair")
                    : "Index 1 must still be AC Chair";
            assert bogies.get(2).name.equals("First Class")
                    : "Index 2 must still be First Class";
            assert bogies.get(3).name.equals("Sleeper")
                    : "Index 3 must still be Sleeper";
            System.out.println("PASS: testReduce_OriginalListUnchanged");
        }

        // Run all tests
        public void runAll() {
            System.out.println("\n========================================");
            System.out.println(" UC10 - Running All Tests ");
            System.out.println("========================================");
            testReduce_TotalSeatCalculation();
            testReduce_MultipleBogiesAggregation();
            testReduce_SingleBogieCapacity();
            testReduce_EmptyBogieList();
            testReduce_CorrectCapacityExtraction();
            testReduce_AllBogiesIncluded();
            testReduce_OriginalListUnchanged();
            System.out.println("========================================");
            System.out.println(" All Tests Passed Successfully! ");
            System.out.println("========================================");
        }
    }
}