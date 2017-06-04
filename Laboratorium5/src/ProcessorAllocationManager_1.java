import java.util.List;
import java.util.Queue;

public class ProcessorAllocationManager_1 extends ProcessorAllocationManager {
    private static final String DESCRIPTION = "x pyta losowo wybr. procesor y o aktualne obciążenie. Jeśli jest mniejsze od progu p, proces jest tam wysyłany." +
            "\nJeśli nie, losujemy i pytamy następny, próbując co najwyżej z razy. Jeśli wszystkie wylosowane są obciążone powyżej p, proces wykonuje się na x";

    private final int maxMigrationRequests;

    public ProcessorAllocationManager_1(List<Processor> processors, int maxMigrationRequests) {
        super(processors);
        this.maxMigrationRequests = maxMigrationRequests;
    }

    @Override
    public ProcessorAllocationStats process(Queue<Process> processes) {
        int migrationRequest = 0;
        int migrations = 0;

        while (!processes.isEmpty()) {
            Process process = processes.poll();
            Processor processor = randomProcessor();
            if (process.isEmpty())
                processAll();

            else {
                boolean processHandled = false;
                for (int i = 0; i < maxMigrationRequests && !processHandled; i++) {
                    ++migrationRequest;
                    Processor nextProcessor = randomProcessor();
                    if (nextProcessor.canHandleNextProcess()) {
                        nextProcessor.addProcess(process);
                        processHandled = true;
                        ++migrations;
                    }
                }
                if (!processHandled)
                    processor.addProcess(process);
            }

            processAll();
        }

        return new ProcessorAllocationStats(DESCRIPTION, averageLoad(), averageDeviation(), migrationRequest, migrations);
    }

    private Processor randomProcessor() {
        return processors.get(random.nextInt(processors.size()));
    }
}
