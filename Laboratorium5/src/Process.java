import static java.lang.String.format;

public class Process {
    public static final Process EMPTY = new Process(0.0, 0);
    private final double processorLoading;
    private int timeToFinish;

    public Process(double processorLoading, int timeToFinish) {
        this.processorLoading = processorLoading;
        this.timeToFinish = timeToFinish;
    }

    public double getProcessorLoading() {
        return processorLoading;
    }

    public boolean isFinished() {
        return timeToFinish <= 0;
    }

    public void process() {
        --timeToFinish;
    }

    @Override
    public String toString() {
        return this == EMPTY ? "empty" : format("%f (%d)", processorLoading, timeToFinish);
    }
}
