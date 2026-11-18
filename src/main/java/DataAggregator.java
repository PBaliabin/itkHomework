import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class DataAggregator {

    public static ProductInfo aggregateProductInfo(String productName) {
        CompletableFuture<Double> fetchPrice = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(1000, 3000));
                        if (Math.random() < 0.2) throw new RuntimeException();
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                        System.out.println("Прервано получение цены");
                        throw new RuntimeException();
                    }
                    return ((double)((int)(Math.random() * 10_000 * 100)))/100;
                })
                .exceptionally(ex -> 0.0);

        CompletableFuture<String> fetchDescription = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(1000, 3000));
                        if (Math.random() < 0.2) throw new RuntimeException();
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                        System.out.println("Прервано получение описания");
                        throw new RuntimeException();
                    }
                    return "Описание " + productName;
                })
                .exceptionally(ex -> "Нет данных");

        CompletableFuture<Double> fetchRating = CompletableFuture
                .supplyAsync(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(1000, 3000));
                        if (Math.random() < 0.2) throw new RuntimeException();
                    } catch (InterruptedException exception) {
                        Thread.currentThread().interrupt();
                        System.out.println("Прервано получение рейтинга");
                        throw new RuntimeException();
                    }
                    return ((double)((int)(Math.random() * 5 * 10)))/10 ;
                })
                .exceptionally(ex -> 0.0);


        return CompletableFuture
                .allOf(fetchPrice, fetchDescription, fetchRating)
                .thenApply(v -> new ProductInfo(
                        productName,
                        fetchPrice.join(),
                        fetchDescription.join(),
                        fetchRating.join()
                ))
                .join();
    }

    public record ProductInfo(String name, double price, String description, double rating) {}
}
