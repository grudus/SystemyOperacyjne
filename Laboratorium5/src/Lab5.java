import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.*;
import static java.util.stream.LongStream.range;

public class Lab5 {
    private static final int NUMBER_OF_PROCESSORS = 75;
    private static final int NUMBER_OF_PROCESSES = 10_000;

    private static final double MAX_PROCESSOR_LOAD = 0.8;

    private static final double PROCESS_MIN_LOAD = 0.05;
    private static final double PROCESS_MAX_LOAD = 0.5;
    private static final int PROCESS_MIN_TIME = 10;
    private static final int PROCESS_MAX_TIME = 100;

    private static final int MAX_MIGRATION_REQUESTS = 10;


    public static void main(String[] args) {
        System.out.println("Przydział procesorów w syst. rozproszonych\n");

        List<Processor> processors = generateProcessors(NUMBER_OF_PROCESSORS);
        Process[] processes = new ProcessGenerator(
                PROCESS_MIN_LOAD,
                PROCESS_MAX_LOAD,
                PROCESS_MIN_TIME,
                PROCESS_MAX_TIME
        ).generate(NUMBER_OF_PROCESSES);

        ProcessorAllocationManager[] managers = new ProcessorAllocationManager[] {
                new ProcessorAllocationManager_1(processors, MAX_MIGRATION_REQUESTS)
        };

        for (ProcessorAllocationManager manager : managers)
            System.out.println(manager.process(processesQueue(processes)));
    }

    private static List<Processor> generateProcessors(int numberOfProcessors) {
        return range(0, numberOfProcessors)
                .mapToObj(i -> new Processor(MAX_PROCESSOR_LOAD))
                .collect(toList());
    }

    private static Queue<Process> processesQueue(Process[] processes) {
        return new LinkedList<>(asList(Arrays.copyOf(processes, processes.length)));
    }

}
