import java.util.List;
import java.util.Queue;

public class ProcessorAllocationManager_3 extends ProcessorAllocationManager_2 {
    private static final String DESCRIPTION = ".Jak w pkt 2, z tym że procesory o obciążeniu mniejszym od minimalnego progu r pytają losowo wybrane\n" +
            " procesory i jesli obc. zapytanego jest większe od p, pytający przejmuje część jego zadań (założyć jaką).\n" +
            "oraz  że procesory o obciążeniu mniejszym od minimalnego progu r pytają losowo wybrane procesory i jesli obc. zapytanego jest większe od p, pytający przejmuje część jego zadań";

    private final int MAX_LUCKY_REQUESTS = 15;

    public ProcessorAllocationManager_3(List<Processor> processors) {
        super(processors);
    }


    @Override
    public ProcessorAllocationStats process(Queue<Process> processes) {
        int migrationRequest = 0;
        int migrations = 0;
        int luckyRequests;

        while (!processes.isEmpty()) {
            Process process = processes.poll();
            Processor processor = randomProcessor();

            ProcessResponse response = findProcessorAndProcess(process, processor);
            migrationRequest += response.migrationRequests;
            migrations += response.migrations;
            Processor selectedProcessor = response.processor;

            luckyRequests = 0;
            while (selectedProcessor.isBelowMinimumLoad() && luckyRequests++ < MAX_LUCKY_REQUESTS) {
                Processor luckyProcessor = randomProcessor();
                ++migrationRequest;
                if (!luckyProcessor.canHandleNextProcess()) {
                    ++migrations;
                    selectedProcessor.addProcess(luckyProcessor.removeProcess());
                }
            }

            processAll();
        }

        return new ProcessorAllocationStats(DESCRIPTION, averageLoad(), averageDeviation(), migrationRequest, migrations);

    }
}
