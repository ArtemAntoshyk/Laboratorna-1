public class CPU implements Runnable {

        private static final int minTimeBusy = 50;
        private static final int maxTimeBusy = 250;
        private static final int pauseProcessor = 1000;
        private static int counter = 0;
        private final int id = counter++;
        private final int time;
        private boolean busy;
        private CPUProcess process;
        private CPUProcess lost;
        public CPU() {
            this.time = (int) (Math.random() * (maxTimeBusy - minTimeBusy) + minTimeBusy);
            busy = false;
        }
        public synchronized void setTask(CPUProcess p) {
            setProcess(p);
            setBusy(true);
        }
        public synchronized void setProcess(CPUProcess process) {
            this.process = process;
        }
        public synchronized void setBusy(boolean busy) {
            this.busy = busy;
        }
        public synchronized boolean isBusy() {
            return busy;
        }
        public synchronized void setLost(CPUProcess p) {
            lost = p;
        }
        public synchronized CPUProcess getLost() {
            return lost;
        }
        public synchronized CPUProcess getProcess() {
            if (busy && process != null) {
                return process;
            }
            return null;
        }
        public void run() {
            System.out.println(this + " started");
            while (!Thread.interrupted()) {
                try {
                    if (busy) {
                        if (process == null) {
                            throw new IllegalArgumentException("Error to: " + this);
                        }
                        System.out.println(this + " started processing of:" + process);

                        Thread.sleep(time);
                        System.out.println(this + " finished processing of:" + process);
                        setProcess(null);
                        busy = false;
                    }
                    tick();
                    System.out.println(this + " tick");
                } catch (InterruptedException e) {
                    if(process == null) {  // это запрос на выход
                        System.out.println(this+" request to exit received");
                        break;
                    }
                    setLost(process);
                }
            }

            System.out.println(this + " finished");
        }

        private void tick() throws InterruptedException {
            Thread.sleep(pauseProcessor);
        }


        @Override
        public String toString() {
            return "CPU:" + id + " time:" + time;
        }
    }
