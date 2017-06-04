import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ProcessorAllocationManager_2 extends ProcessorAllocationManager {
    private static final String DESCRIPTION = "Jesli obciążenie x przekracza wartość progową p, proces zostaje wysłany na losowo wybrany procesor y o obciążeniu mniejszym od p\n" +
            " (jeśli wylosowany y ma obc.>p, losowanie powtarza się do skutku). Jeśli nie przekracza - proces wykonuje się na x.";

    public ProcessorAllocationManager_2(List<Processor> processors) {
        super(processors);
    }

    @Override
    public ProcessorAllocationStats process(Queue<Process> processes) {
        int migrationRequest = 0;
        int migrations = 0;

        while (!processes.isEmpty()) {
            Process process = processes.poll();
            Processor processor = randomProcessor();

            if (process.isEmpty()) {
                processAll();
            }
            else if (processor.canHandleNextProcess()) {
                processor.addProcess(process);
            }
            else {
                List<Processor> processorsToPeek = new ArrayList<>(processors);
                boolean processHandled = false;
                while (!processorsToPeek.isEmpty() && !processHandled) {
                    int index = random.nextInt(processorsToPeek.size());
                    Processor newProcessor = processorsToPeek.get(index);
                    ++migrationRequest;

                    if (newProcessor.canHandleNextProcess()) {
                        ++migrations;
                        newProcessor.addProcess(process);
                        processHandled = true;
                    }
                    else
                        processorsToPeek.remove(index);
                }
                if (processorsToPeek.isEmpty() && !processHandled)
                    System.err.println("Cannot handle process " + process);
            }

            processAll();
        }

        return new ProcessorAllocationStats(DESCRIPTION, averageLoad(), averageDeviation(), migrationRequest, migrations);
    }

    private Processor randomProcessor() {
        return processors.get(random.nextInt(processors.size()));
    }
}
