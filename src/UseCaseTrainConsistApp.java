import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ========================================================
 * MAIN CLASS - UseCase9TrainConsistMgmt
 * ========================================================
 *
 * Use Case 9: Group Bogies by Type
 *
 * Description:
 * This class groups similar bogies together using
 * Java Stream Collectors.groupingBy().
 *
 * At this stage, the application:
 * - Creates a list of bogies
 * - Streams the list
 * - Groups bogies by name
 * - Stores grouped data in a Map
 * - Displays grouped structure
 *
 * This maps classification logic using groupingBy.
 *
 * @author Developer
 * @version 9.0
 */
public class UseCaseTrainConsistApp {

    // Reusing Bogie model from UC7 / UC8
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
     * Groups a list of bogies by their name/type.
     * Returns a new Map; original list is not modified.
     */
    public static Map<String, List<Bogie>> groupByType(List<Bogie> bogies) {
        return bogies.stream()
                .collect(Collectors.groupingBy(b -> b.name));
    }

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" UC9 - Group Bogies by Type ");
        System.out.println("========================================\n");

        // Create list of bogies
        List<Bogie> bogies = new ArrayList<>();

        bogies.add(new Bogie("Sleeper",     72));
        bogies.add(new Bogie("AC Chair",    56));
        bogies.add(new Bogie("First Class", 24));
        bogies.add(new Bogie("Sleeper",     70));
        bogies.add(new Bogie("AC Chair",    60));

        // Display input bogies
        System.out.println("All Bogies:");
        for (Bogie b : bogies) {
            System.out.println(b);
        }

        // ---- GROUP USING COLLECTORS.GROUPINGBY ----
        Map<String, List<Bogie>> groupedBogies = groupByType(bogies);

        // Display grouped structure
        System.out.println("\nGrouped Bogies:");
        for (Map.Entry<String, List<Bogie>> entry : groupedBogies.entrySet()) {
            System.out.println("\nBogie Type: " + entry.getKey());
            for (Bogie b : entry.getValue()) {
                System.out.println("  Capacity -> " + b.capacity);
            }
        }

        System.out.println("\nUC9 grouping completed...");
    }


    // ========================================================
    // INNER TEST CLASS - UseCase9TrainConsistMgmtTest
    // ========================================================

    public static class UseCase9TrainConsistMgmtTest {

        // Helper: standard mixed bogie list
        private List<Bogie> createBogieList() {
            List<Bogie> bogies = new ArrayList<>();
            bogies.add(new Bogie("Sleeper",     72));
            bogies.add(new Bogie("AC Chair",    56));
            bogies.add(new Bogie("First Class", 24));
            bogies.add(new Bogie("Sleeper",     70));
            bogies.add(new Bogie("AC Chair",    60));
            return bogies;
        }

        public void testGrouping_BogiesGroupedByType() {
            List<Bogie> bogies = createBogieList();
            Map<String, List<Bogie>> result = groupByType(bogies);
            // Each key must only contain bogies of that type
            for (Map.Entry<String, List<Bogie>> entry : result.entrySet()) {
                for (Bogie b : entry.getValue()) {
                    assert b.name.equals(entry.getKey())
                            : "Bogie name must match its group key";
                }
            }
            System.out.println("PASS: testGrouping_BogiesGroupedByType");
        }

        public void testGrouping_MultipleBogiesInSameGroup() {
            List<Bogie> bogies = createBogieList();
            Map<String, List<Bogie>> result = groupByType(bogies);
            // Sleeper and AC Chair each have 2 entries
            assert result.get("Sleeper").size() == 2
                    : "Sleeper group must contain 2 bogies";
            assert result.get("AC Chair").size() == 2
                    : "AC Chair group must contain 2 bogies";
            System.out.println("PASS: testGrouping_MultipleBogiesInSameGroup");
        }

        public void testGrouping_DifferentBogieTypes() {
            List<Bogie> bogies = createBogieList();
            Map<String, List<Bogie>> result = groupByType(bogies);
            // 3 distinct types: Sleeper, AC Chair, First Class
            assert result.size() == 3
                    : "Expected 3 distinct bogie type groups";
            System.out.println("PASS: testGrouping_DifferentBogieTypes");
        }

        public void testGrouping_EmptyBogieList() {
            List<Bogie> bogies = new ArrayList<>();
            Map<String, List<Bogie>> result = groupByType(bogies);
            assert result.isEmpty()
                    : "Grouping empty list must return empty Map";
            System.out.println("PASS: testGrouping_EmptyBogieList");
        }

        public void testGrouping_SingleBogieCategory() {
            List<Bogie> bogies = new ArrayList<>();
            bogies.add(new Bogie("Sleeper", 72));
            bogies.add(new Bogie("Sleeper", 68));
            Map<String, List<Bogie>> result = groupByType(bogies);
            assert result.size() == 1
                    : "Expected only 1 key in Map";
            assert result.containsKey("Sleeper")
                    : "Map must contain key 'Sleeper'";
            System.out.println("PASS: testGrouping_SingleBogieCategory");
        }

        public void testGrouping_MapContainsCorrectKeys() {
            List<Bogie> bogies = createBogieList();
            Map<String, List<Bogie>> result = groupByType(bogies);
            assert result.containsKey("Sleeper")
                    : "Map must contain key 'Sleeper'";
            assert result.containsKey("AC Chair")
                    : "Map must contain key 'AC Chair'";
            assert result.containsKey("First Class")
                    : "Map must contain key 'First Class'";
            System.out.println("PASS: testGrouping_MapContainsCorrectKeys");
        }

        public void testGrouping_GroupSizeValidation() {
            List<Bogie> bogies = createBogieList();
            Map<String, List<Bogie>> result = groupByType(bogies);
            assert result.get("Sleeper").size()     == 2 : "Sleeper must have 2";
            assert result.get("AC Chair").size()    == 2 : "AC Chair must have 2";
            assert result.get("First Class").size() == 1 : "First Class must have 1";
            System.out.println("PASS: testGrouping_GroupSizeValidation");
        }

        public void testGrouping_OriginalListUnchanged() {
            List<Bogie> bogies = createBogieList();
            int originalSize = bogies.size();
            groupByType(bogies);
            // Original list must be untouched after stream grouping
            assert bogies.size() == originalSize
                    : "Original list size must not change";
            assert bogies.get(0).name.equals("Sleeper")
                    : "Index 0 must still be Sleeper";
            assert bogies.get(1).name.equals("AC Chair")
                    : "Index 1 must still be AC Chair";
            assert bogies.get(2).name.equals("First Class")
                    : "Index 2 must still be First Class";
            System.out.println("PASS: testGrouping_OriginalListUnchanged");
        }

        // Run all tests
        public void runAll() {
            System.out.println("\n========================================");
            System.out.println(" UC9 - Running All Tests ");
            System.out.println("========================================");
            testGrouping_BogiesGroupedByType();
            testGrouping_MultipleBogiesInSameGroup();
            testGrouping_DifferentBogieTypes();
            testGrouping_EmptyBogieList();
            testGrouping_SingleBogieCategory();
            testGrouping_MapContainsCorrectKeys();
            testGrouping_GroupSizeValidation();
            testGrouping_OriginalListUnchanged();
            System.out.println("========================================");
            System.out.println(" All Tests Passed Successfully! ");
            System.out.println("========================================");
        }
    }
}