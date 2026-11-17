import java.util.*;
import java.util.stream.Collectors;

public class Summarizer {

    public static void printSummary(List<TestResult> run) {
        Map<Status, List<String>> tests = run.stream()
                .collect(Collectors.groupingBy(
                        TestResult::status, Collectors.mapping(TestResult::name, Collectors.toList())
                ));

        List<String> longestTimeNames = run.stream()
                .sorted(Comparator.comparingLong(TestResult::durationMs).reversed())
                .limit(3)
                .map(TestResult::name)
                .toList();

        double avgTime = run.stream()
                .filter(testResult -> testResult.status == Status.PASSED)
                .mapToLong(TestResult::durationMs).average().orElse(-1);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("=== Сводка прогона ===").append("\n");

        tests.forEach((testStatus, testNames) -> stringBuilder
                .append(testStatus.name()).append(" (").append(testNames.size()).append("): ")
                .append(String.join(", ", testNames)).append("\n"));

        stringBuilder.append("Топ-3 долгих: ").append(String.join(", ", longestTimeNames)).append("\n");
        stringBuilder.append("Среднее время прошедших: ").append(String.format("%.2f", avgTime)).append(" ms");
        System.out.println(stringBuilder);
    }

    public enum Status { PASSED, FAILED, SKIPPED }

    public record TestResult(String name, Status status, long durationMs) { }
}
