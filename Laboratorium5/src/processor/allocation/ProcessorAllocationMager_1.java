package processor.allocation;

import java.security.SecureRandom;
import java.util.List;
import java.util.Queue;
import process.Process;
import processor.Processor;
import processor.ProcessorAllocationStats;

public class ProcessorAllocationMager_1 extends ProcessorAllocationManager {

    private final SecureRandom random;
    private final int maxNumberOfRequests;
    private static final String DESCRIPTION = "x pyta losowo wybr. procesor y o aktualne obciążenie. Jeśli jest mniejsze od progu p, proces jest tam wysyłany." +
            "\nJeśli nie, losujemy i pytamy następny, próbując co najwyżej z razy. Jeśli wszystkie wylosowane są obciążone powyżej p, proces wykonuje się na x";

    public ProcessorAllocationMager_1(List<Processor> processors, Queue<Process> processes, int maxNumberOfRequests) {
        super(processors, processes);
        this.maxNumberOfRequests = maxNumberOfRequests;
        random = new SecureRandom();
    }

    @Override
    public ProcessorAllocationStats process() {
        int loadRequests = 0;
        int loadMigrations = 0;
        while (!processes.isEmpty()) {
            Process newProcess = processes.poll();
            if (newProcess != Process.EMPTY) {
                boolean added = false;
                for (int i = 0; i < maxNumberOfRequests && !added; i++) {
                    Processor processor = randomProcessor();
                    loadRequests++;
                    if (processor.canHandleNewProcess()) {
                        processor.addProcess(newProcess);
                        loadMigrations++;
                        added = true;
                    }
                }
                if (!added)
                    randomProcessor().addProcess(newProcess);
            }
            processAll();

        }
        return new ProcessorAllocationStats(DESCRIPTION, calculateAverageLoad(), calculateAverageDeviation(), loadRequests, loadMigrations);
    }

    private double calculateAverageDeviation() {
        return processors.stream().mapToDouble(Processor::getDeviation)
                .sum() / processors.size();
    }

    private double calculateAverageLoad() {
        return processors.stream().mapToDouble(Processor::getAverageLoad)
                .sum() / processors.size();
    }

    private Processor randomProcessor() {
        return processors.get(random.nextInt(processors.size()));
    }

    private void processAll() {
        processors.forEach(Processor::process);
    }
}
