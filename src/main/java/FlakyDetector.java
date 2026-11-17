import java.util.*;
import java.util.stream.Collectors;

public class FlakyDetector {
    public Set<String> failed(List<String> run) {
        return run.stream()
                .map(test -> test.split("\\|"))
                .filter(test -> test[1].equals(TestStatus.FAILED.name()))
                .map(test -> test[0])
                .collect(Collectors.toSet());
    }

    public Set<String> passed(List<String> run) {
        return run.stream()
                .map(test -> test.split("\\|"))
                .filter(test -> test[1].equals(TestStatus.PASSED.name()))
                .map(test -> test[0])
                .collect(Collectors.toSet());
    }

    public List<String> flaky(List<String> run1, List<String> run2){
        // FAILED -> PASSED
        List<String> failedFirstPassedSecond = new ArrayList<>(failed(run1));
        failedFirstPassedSecond.retainAll(passed(run2));

        return failedFirstPassedSecond;
    }
    public Map<String, Long> statusSummary(List<String> run) {
        HashMap<String, Long> summary = new HashMap<>();
        summary.put(TestStatus.PASSED.name(), (long) passed(run).size());
        summary.put(TestStatus.FAILED.name(), (long) failed(run).size());

        return summary;
    }

    enum TestStatus {
        PASSED,
        FAILED
    }
}
