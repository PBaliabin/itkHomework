import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TestLogParser testLogParser = new TestLogParser();
        TestLogParser.FinalResult parseResult;

        try (Scanner sc = new Scanner(new FileInputStream("src/main/resources/run.log"))){
            List<String> logs = new ArrayList<>();
            while (sc.hasNextLine()) {
                logs.add(sc.nextLine());
            }
             parseResult = testLogParser.parse(logs);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        // print test results
        StringBuilder outputMessage = new StringBuilder();
        outputMessage.append("Результаты:\n");
        parseResult.results().forEach(testResult -> outputMessage.append(" ").append(testResult.toString()).append("\n"));

        // print errors
        outputMessage.append("Битые строки:\n");
        parseResult.errors().forEach(testResult -> outputMessage.append(" ").append(testResult.toString()).append("\n"));

        // first failed
        outputMessage.append("Первый упавший: ");
        TestLogParser.TestResult firstFailed = testLogParser.firstFailed(parseResult.results()).orElse(null);
        outputMessage.append(firstFailed == null ? "Упавших тестов нет" : firstFailed.name());

        System.out.println(outputMessage);

        System.out.println();
        SimpleStringBuilder simpleStringBuilder = new SimpleStringBuilder();
        simpleStringBuilder.append("Hello");
        simpleStringBuilder.append(", ");
        simpleStringBuilder.append("world");
        System.out.println(simpleStringBuilder.toString());
        simpleStringBuilder.undo();
        System.out.println(simpleStringBuilder.toString());
        simpleStringBuilder.append("AQA!");
        System.out.println(simpleStringBuilder.toString());
    }
}