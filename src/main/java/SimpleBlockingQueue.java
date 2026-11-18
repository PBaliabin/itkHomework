import java.util.LinkedList;
import java.util.Queue;

public class SimpleBlockingQueue<T> {

    private final Object lock = new Object();
    private final Queue<T> queue = new LinkedList<>();
    private final int maxQueueSize;

    public SimpleBlockingQueue(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
    }

    public void enqueue(T item) {
        try {
            synchronized (lock) {
                while (queue.size() >= maxQueueSize) {
                    System.out.println("Queue is full");
                    lock.wait();
                }

                queue.add(item);
                lock.notify();
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
    }

    public T dequeue() {
        try {
            synchronized (lock) {
                while (queue.isEmpty()) {
                    System.out.println("Queue is empty");
                    lock.wait();
                }

                var element = queue.poll();
                lock.notify();
                return element;
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }

        return null;
    }

    public int size() {
        return queue.size();
    }
}
