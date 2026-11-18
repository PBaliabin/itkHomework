import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        task1();
        System.out.println();

        task2();
        System.out.println();

        task3();
    }

    public static void task1() {
        List<Callable<ParallelRunner.TestResult>> tests = new ArrayList<>();
        long sumTime = 0;
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            long executionTime = random.nextInt(300, 500);
            sumTime += executionTime;

            tests.add(ParallelRunner.fakeTest(
                    "test" + (i + 1),
                    executionTime,
                    ParallelRunner.Status.values()[random.nextInt(ParallelRunner.Status.values().length)]
            ));
        }

        long realTime = System.nanoTime();
        List<ParallelRunner.TestResult> results = ParallelRunner.runTests(tests);
        realTime = (System.nanoTime() - realTime) / 1_000_000;

        long passed = results.stream()
                .map(ParallelRunner.TestResult::status)
                .filter(status -> status == ParallelRunner.Status.PASSED)
                .count();
        long failed = results.stream()
                .map(ParallelRunner.TestResult::status)
                .filter(status -> status == ParallelRunner.Status.FAILED)
                .count();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Прошло: ").append(passed).append(", упало: ").append(failed).append("\n");
        stringBuilder.append("Суммарное время тестов: ").append(sumTime).append(" ms\n");
        stringBuilder.append("Реальное время прогона: ").append(realTime).append(" ms\n");
        stringBuilder.append("Параллельность сэкономила: ").append(sumTime - realTime).append(" ms\n");
        System.out.println(stringBuilder);
    }

    public static void task2() {
        System.out.println(DataAggregator.aggregateProductInfo("Ноутбук"));
    }

    public static void task3() {
        SimpleBlockingQueue<String> simpleBlockingQueue = new SimpleBlockingQueue<>(2);

        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(500);
                simpleBlockingQueue.enqueue("first message");
                Thread.sleep(100);
                simpleBlockingQueue.enqueue("second message");
                Thread.sleep(100);
                simpleBlockingQueue.enqueue("third message");
                Thread.sleep(500);
                simpleBlockingQueue.enqueue("fourth message");
                Thread.sleep(500);
                simpleBlockingQueue.enqueue("fifth message");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("interrupted producer");
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(simpleBlockingQueue.dequeue());
                Thread.sleep(100);
                System.out.println(simpleBlockingQueue.dequeue());
                Thread.sleep(100);
                System.out.println(simpleBlockingQueue.dequeue());
                Thread.sleep(100);
                System.out.println(simpleBlockingQueue.dequeue());
                Thread.sleep(100);
                System.out.println(simpleBlockingQueue.dequeue());
            } catch (InterruptedException e) {
                System.out.println("interrupted consumer");
            }
        });

        try (ExecutorService pool = Executors.newFixedThreadPool(2)){
            pool.submit(consumer);
            pool.submit(producer);
        }
    }
}
