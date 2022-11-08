public class ProcessFlow implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static final int MIN_TIME_TO_NEXT = 10;     // time to next process started
    private static final int MAX_TIME_TO_NEXT = 500;
    private final int n;
    private CPUQueue queue;  // by default makes it's own queue
    private boolean finished;

    /**
     * Constructor of Process flow generator
     * @param n number of process to generate
     */
    public ProcessFlow(int n) {
        if(n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;

    }

    public void setQueue(CPUQueue queue) {
        if(queue == null ) {
            throw new IllegalArgumentException();
        }
        this.queue = queue;
    }

    /**
     * Creates new Process with time to next and duration parameters
     * Generates random time to start next process
     *
     * @return generated process object
     */
    public CPUProcess genProcess() {
        int timeToNext = (int) (Math.random() * (MAX_TIME_TO_NEXT - MIN_TIME_TO_NEXT) + MIN_TIME_TO_NEXT);
        return new CPUProcess(timeToNext, id);
    }

    /**
     * Generates  n number of processes
     * waits process.getTime() milliseconds after
     * generated process
     */
    @Override
    public void run() {
        System.out.println(this + " started");
        if(queue == null) {
            throw new IllegalArgumentException();
        }
        try {
            for (int i = 0; i < n; i++) {
                CPUProcess p = genProcess();
                queue.add(p);               // add process to queue
                System.out.println(String.format("%s  %s time:%4d",p,this, p.getTime()));

                Thread.sleep(p.getTime());
            }


        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + " finished");
    }

    public synchronized int getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("ProcessFlow:%2d",id);
    }


}