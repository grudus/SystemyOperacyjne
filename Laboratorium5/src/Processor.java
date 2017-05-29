import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.function.Function.identity;

public class Processor {
    private double currentLoad;
    private int currentTime;
    private final double maximumLoad;
    private final double minimumLoad;
    private List<Process> processes;
    private Map<Integer, Double> timeToLoad;

    public Processor(double minimumLoad, double maximumLoad) {
        this.maximumLoad = maximumLoad;
        this.minimumLoad = minimumLoad;
        this.processes = new ArrayList<>();
        timeToLoad = new HashMap<>();
        currentTime = 0;
    }

    public void addLoad(double load) {
        currentLoad += load;
    }

    public void removeLoad(double load) {
        currentLoad -= load;
    }

    public double getCurrentLoad() {
        return currentLoad;
    }

    public double getMaximumLoad() {
        return maximumLoad;
    }

    public double getMinimumLoad() {
        return minimumLoad;
    }

    public boolean canHandleNewProcess() {
        return currentLoad < maximumLoad;
    }

    public void addProcess(Process process) {
        processes.add(process);
        currentLoad += process.getProcessorLoading();
    }

    public void process() {
        currentTime++;
        timeToLoad.put(currentTime, currentLoad);
        processes.forEach(Process::process);
        removeFinishedProcesses();

    }

    private void removeFinishedProcesses() {
        for (Iterator<Process> iterator = processes.iterator(); iterator.hasNext(); ) {
            Process process = iterator.next();
            if (process.isFinished()) {
                currentLoad -= process.getProcessorLoading();
                iterator.remove();
            }
        }
    }

    public double getAverageLoad() {
        return timeToLoad.values().stream().mapToDouble(d -> d)
                .sum() / timeToLoad.size();
    }

    public double getDeviation() {
        final double average = getAverageLoad();
        final double x = timeToLoad.values().stream().mapToDouble(d -> pow(d - average, 2))
                .sum() / timeToLoad.size();
        return sqrt(x);
    }
}
