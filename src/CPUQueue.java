import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Concurrent Linked CPUQueue for Process Objects
 */
public class CPUQueue {
    private Queue<CPUProcess> queue;
    private int maxSize;

    /**
     * Constructor creates empty queue
     */
    public CPUQueue() {
        this.queue = new ConcurrentLinkedQueue<>();
        maxSize = 0;
    }

    public synchronized int getSize() {
        return queue.size();
    }

    public synchronized int getMaxSize() {
        return maxSize;
    }

    public synchronized void add(CPUProcess process) {
        if (queue == null || process == null) {
            throw new IllegalArgumentException();
        }
        queue.add(process);
        if (queue.size() > maxSize) {
            maxSize = queue.size();
        }
    }

    public synchronized CPUProcess remove() {
        if (queue == null || queue.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return queue.remove();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
