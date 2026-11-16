import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class TestLogParser {

    public FinalResult parse(List<String> lines) {
        LinkedHashMap<String, TestResult> results = new LinkedHashMap<>();
        List<Error> errors = new ArrayList<>();

        for (String line : lines) {
            String[] values = line.split("[^a-zA-Z0-9]");

            if (values.length != 3) {
                errors.add(new Error(line, LogError.SEPARATION_ERROR));
                continue;
            }

            Status status;
            try {
                status = Status.valueOf(values[1]);
            } catch (Exception e) {
                errors.add(new Error(line, LogError.INCORRECT_STATUS));
                continue;
            }

            long durationMs;
            try {
                durationMs = Long.parseLong(values[2]);
            } catch (Exception e) {
                errors.add(new Error(line, LogError.TIME_IS_NAN));
                continue;
            }
            if (durationMs < 0) {
                errors.add(new Error(line, LogError.NEGATIVE_TIME));
                continue;
            }

            TestResult testResult = new TestResult(values[0], status, durationMs);
            // Record автоматически генерирует Equals и HashCode по всем полям записи,
            // поэтому при одинаковых значениях полей записи будет сгенерирован одинаковый HashCode
            results.put(testResult.name, testResult);
        }

        return new FinalResult(results.values().stream().toList(), errors);
    }

    public Optional<TestResult> firstFailed(List<TestResult> results) {
        Optional<TestResult> result = Optional.empty();

        for (TestResult testResult : results) {
            if (testResult.status == Status.FAILED) {
                result = Optional.of(testResult);
                break;
            }
        }

        return result;
    }

    public record FinalResult(List<TestResult> results, List<Error> errors) {}

    public record TestResult(String name, Status status, long durationMs) {
        @Override
        public String toString() {
            return name + " - " + status + " (" + durationMs + " ms)";
        }
    }

    record Error(String logEntry, LogError error) {
        @Override
        public String toString() {
            return logEntry + " - " + error.message;
        }
    }

    enum Status { PASSED, FAILED, SKIPPED }

    enum LogError {
        SEPARATION_ERROR("Некорректное разделение значений"),
        INCORRECT_STATUS("Некорректный статус"),
        TIME_IS_NAN("Не число в длительности"),
        NEGATIVE_TIME("Отрицательное время выполнения");

        public final String message;
        LogError(String message) {
            this.message = message;
        }
    }

}
