import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ========================================================
 * MAIN CLASS - UseCase8TrainConsistMgmt
 * ========================================================
 *
 * Use Case 8: Filter Passenger Bogies Using Streams
 *
 * Description:
 * This class filters passenger bogies based on seating
 * capacity using Java Stream API.
 *
 * At this stage, the application:
 * - Creates a list of bogies
 * - Converts list into stream
 * - Applies filter condition
 * - Collects filtered result
 * - Displays qualifying bogies
 *
 * This maps functional filtering using Streams.
 *
 * @author Developer
 * @version 8.0
 */
public class UseCaseTrainConsistApp {

    // Reusing Bogie model from UC7
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
     * Filters bogies from the given list where capacity > threshold.
     * Returns a new list; original list is not modified.
     */
    public static List<Bogie> filterByCapacity(List<Bogie> bogies, int threshold) {
        return bogies.stream()
                .filter(b -> b.capacity > threshold)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC8 - Filter Passenger Bogies Using Streams ");
        System.out.println("========================================\n");

        // Create list of passenger bogies (same style as UC7)
        List<Bogie> bogies = new ArrayList<>();

        bogies.add(new Bogie("Sleeper", 72));
        bogies.add(new Bogie("AC Chair", 56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("General", 90));

        // ---- Display all bogies ----
        System.out.println("All Bogies:");
        for (Bogie bogie : bogies) {
            System.out.println(bogie);
        }

        // ---- Filter bogies with capacity > 60 ----
        List<Bogie> filteredBogies = filterByCapacity(bogies, 60);

        System.out.println("\nFiltered Bogies (Capacity > 60):");
        for (Bogie bogie : filteredBogies) {
            System.out.println(bogie);
        }

        System.out.println("\nUC8 filtering completed...");
    }


    // ========================================================
    // INNER TEST CLASS - UseCase8TrainConsistMgmtTest
    // ========================================================

    public static class UseCase8TrainConsistMgmtTest {

        // Helper to create a standard bogie list
        private List<Bogie> createBogieList() {
            List<Bogie> bogies = new ArrayList<>();
            bogies.add(new Bogie("Sleeper", 72));
            bogies.add(new Bogie("AC Chair", 56));
            bogies.add(new Bogie("First Class", 24));
            bogies.add(new Bogie("General", 90));
            return bogies;
        }

        public void testFilter_CapacityGreaterThanThreshold() {
            List<Bogie> bogies = createBogieList();
            List<Bogie> result = filterByCapacity(bogies, 70);
            // Sleeper(72) and General(90) qualify
            assert result.size() == 2 : "Expected 2 bogies with capacity > 70";
            assert result.stream().allMatch(b -> b.capacity > 70)
                    : "All results must have capacity > 70";
            System.out.println("PASS: testFilter_CapacityGreaterThanThreshold");
        }

        public void testFilter_CapacityEqualToThreshold() {
            List<Bogie> bogies = createBogieList();
            // Sleeper(72) is NOT > 72, so excluded; only General(90) qualifies
            List<Bogie> result = filterByCapacity(bogies, 72);
            assert result.size() == 1 : "Expected 1 bogie with capacity > 72";
            assert result.get(0).name.equals("General")
                    : "Only General should qualify";
            System.out.println("PASS: testFilter_CapacityEqualToThreshold");
        }

        public void testFilter_CapacityLessThanThreshold() {
            List<Bogie> bogies = createBogieList();
            List<Bogie> result = filterByCapacity(bogies, 90);
            assert result.isEmpty() : "Expected no bogies with capacity > 90";
            System.out.println("PASS: testFilter_CapacityLessThanThreshold");
        }

        public void testFilter_MultipleBogiesMatching() {
            List<Bogie> bogies = createBogieList();
            // AC Chair(56), Sleeper(72), General(90) all qualify
            List<Bogie> result = filterByCapacity(bogies, 50);
            assert result.size() == 3 : "Expected 3 bogies with capacity > 50";
            System.out.println("PASS: testFilter_MultipleBogiesMatching");
        }

        public void testFilter_NoBogiesMatching() {
            List<Bogie> bogies = createBogieList();
            List<Bogie> result = filterByCapacity(bogies, 100);
            assert result.isEmpty() : "Expected empty list when threshold is 100";
            System.out.println("PASS: testFilter_NoBogiesMatching");
        }

        public void testFilter_AllBogiesMatching() {
            List<Bogie> bogies = createBogieList();
            // All bogies have capacity > 0
            List<Bogie> result = filterByCapacity(bogies, 0);
            assert result.size() == 4 : "Expected all 4 bogies when threshold is 0";
            System.out.println("PASS: testFilter_AllBogiesMatching");
        }

        public void testFilter_EmptyBogieList() {
            List<Bogie> bogies = new ArrayList<>();
            List<Bogie> result = filterByCapacity(bogies, 60);
            assert result.isEmpty() : "Expected empty list for empty input";
            System.out.println("PASS: testFilter_EmptyBogieList");
        }

        public void testFilter_OriginalListUnchanged() {
            List<Bogie> bogies = createBogieList();
            int originalSize = bogies.size();
            filterByCapacity(bogies, 60);
            // Original list must remain untouched after stream operation
            assert bogies.size() == originalSize : "Original list size must not change";
            assert bogies.get(0).name.equals("Sleeper")   : "Index 0 must be Sleeper";
            assert bogies.get(1).name.equals("AC Chair")  : "Index 1 must be AC Chair";
            assert bogies.get(2).name.equals("First Class"): "Index 2 must be First Class";
            assert bogies.get(3).name.equals("General")   : "Index 3 must be General";
            System.out.println("PASS: testFilter_OriginalListUnchanged");
        }

        // Run all tests
        public void runAll() {
            System.out.println("\n========================================");
            System.out.println(" UC8 - Running All Tests ");
            System.out.println("========================================");
            testFilter_CapacityGreaterThanThreshold();
            testFilter_CapacityEqualToThreshold();
            testFilter_CapacityLessThanThreshold();
            testFilter_MultipleBogiesMatching();
            testFilter_NoBogiesMatching();
            testFilter_AllBogiesMatching();
            testFilter_EmptyBogieList();
            testFilter_OriginalListUnchanged();
            System.out.println("========================================");
            System.out.println(" All Tests Passed Successfully! ");
            System.out.println("========================================");
        }
    }
}