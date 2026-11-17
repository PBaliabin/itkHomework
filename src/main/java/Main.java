import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HashMap<String, List<String>> runs = new HashMap<>();
        runs.put("Прогон 1", List.of(
                "LoginTest|PASSED",
                "CreateTaskTest|FAILED",
                "CommentTest|FAILED",
                "LogoutTest|PASSED"
        ));
        runs.put("Прогон 2", List.of(
                "LoginTest|PASSED",
                "CreateTaskTest|PASSED",
                "CommentTest|FAILED",
                "LogoutTest|PASSED"
        ));

        FlakyDetector flakyDetector = new FlakyDetector();
        List<String> flaky = flakyDetector.flaky(runs.get("Прогон 1"), runs.get("Прогон 2"));

        Map.Entry<String, List<String>> summaryEntry = runs.entrySet().stream()
                .filter(entry -> entry.getKey().equals("Прогон 2")).findFirst().orElseThrow();
        Map<String, Long> summary = flakyDetector.statusSummary(runs.get(summaryEntry.getKey()));

        String answer = "Flaky-кандидаты: " + flaky.toString() + "\n" +
                summaryEntry.getKey() + ", сводка: " + summary.toString();
        System.out.println(answer);
        System.out.println();

        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        String[] words = {"up", "down", "up", "up", "left"};
        System.out.println(frequencyAnalyzer.frequency(words));

        Integer[] numbers = {3, 1, 3, 3, 7, 1};
        System.out.println(frequencyAnalyzer.frequency(numbers));
    }
}
