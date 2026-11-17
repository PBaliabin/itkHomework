import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Summarizer.TestResult> run = List.of(
                new Summarizer.TestResult("LoginTest",        Summarizer.Status.PASSED, 120),
                new Summarizer.TestResult("CreateTaskTest",   Summarizer.Status.PASSED, 940),
                new Summarizer.TestResult("UpdateStatusTest", Summarizer.Status.FAILED, 2100),
                new Summarizer.TestResult("CommentTest",      Summarizer.Status.PASSED, 380),
                new Summarizer.TestResult("DeleteTaskTest",   Summarizer.Status.FAILED, 760),
                new Summarizer.TestResult("StatsTest",        Summarizer.Status.SKIPPED, 0),
                new Summarizer.TestResult("LogoutTest",       Summarizer.Status.PASSED, 90)
        );

        Summarizer.printSummary(run);

        System.out.println();
        ScheduleManager.test();
    }
}
