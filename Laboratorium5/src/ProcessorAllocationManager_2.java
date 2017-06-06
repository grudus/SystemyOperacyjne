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

            ProcessResponse response = findProcessorAndProcess(process, processor);
            migrationRequest += response.migrationRequests;
            migrations += response.migrations;

            processAll();
        }

        return new ProcessorAllocationStats(DESCRIPTION, averageLoad(), averageDeviation(), migrationRequest, migrations);
    }

    protected ProcessResponse findProcessorAndProcess(Process process, Processor processor) {
        int migrationRequest = 0;
        int migrations = 0;
        Processor selectedProcessor = processor;

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
                    selectedProcessor = newProcessor;
                    newProcessor.addProcess(process);
                    processHandled = true;
                }
                else
                    processorsToPeek.remove(index);
            }
            if (processorsToPeek.isEmpty() && !processHandled) ;
//                System.err.println("Cannot handle process " + process);
        }
        return new ProcessResponse(migrationRequest, migrations, selectedProcessor);
    }

    protected static class ProcessResponse {
        protected final int migrationRequests;
        protected final int migrations;
        protected final Processor processor;

        public ProcessResponse(int migrationRequests, int migrations, Processor processor) {
            this.migrationRequests = migrationRequests;
            this.migrations = migrations;
            this.processor = processor;
        }
    }

}
