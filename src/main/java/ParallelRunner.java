import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelRunner {

    public static List<TestResult> runTests(List<Callable<TestResult>> tests) {
        List<TestResult> results = new ArrayList<>();
        try (ExecutorService executorService = Executors.newFixedThreadPool(4)) {
            results = executorService.invokeAll(tests).stream()
                    .map(testFuture -> {;
                        try {
                            return testFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println("error while extracting results");
                        }
                        return null;
                    })
                    .toList();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("interrupted while executing tests");
        }

        return results;
    }

    public static Callable<TestResult> fakeTest(String name, long durationMs, Status status) {
        return () -> {
            Thread.sleep(durationMs);
            return new TestResult(name, status, durationMs);
        };
    }

    public enum Status { PASSED, FAILED }

    public record TestResult(String name, Status status, long durationMs) { }
}
