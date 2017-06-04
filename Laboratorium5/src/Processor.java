import java.util.*;

import static java.lang.Math.*;

public class Processor {
    private final double maxLoad;
    private List<Process> processes;

    private double currentLoad;
    private int time;
    private Map<Integer, Double> loadInTime;

    public Processor(double maxLoad) {
        this.maxLoad = maxLoad;
        this.processes = new ArrayList<>();
        this.loadInTime = new HashMap<>();
    }

    public void addProcess(Process process) {
        processes.add(process);
        currentLoad += process.getProcessorLoad();
    }

    public boolean canHandleNextProcess() {
        return currentLoad < maxLoad;
    }

    public void process() {
        loadInTime.put(++time, currentLoad);
        processes.forEach(Process::reduceTime);
        cleanEmptyProcesses();
    }

    public double averageLoad() {
        double totalLoad = loadInTime.values().stream().reduce((d1, d2) -> d1 + d2)
                .orElseThrow(IllegalStateException::new);

        return totalLoad / loadInTime.size();
    }

    public double averageDeviation() {
        final double mean = averageLoad();
        double deviation = loadInTime.values().stream()
                .mapToDouble(load -> pow(load - mean, 2))
                .sum();
        double averageDeviation = deviation / loadInTime.size();
        return sqrt(averageDeviation);
    }

    private void cleanEmptyProcesses() {
        for(Iterator<Process> iterator = processes.iterator(); iterator.hasNext();) {
            Process process = iterator.next();
            if (process.isFinished()) {
                currentLoad -= process.getProcessorLoad();
                iterator.remove();
            }
        }
    }

    @Override
    public String toString() {
        return loadInTime.toString();
    }
}
