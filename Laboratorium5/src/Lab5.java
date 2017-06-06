import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static java.util.stream.Collectors.toList;
import static java.util.stream.LongStream.range;

public class Lab5 {
    private static final int NUMBER_OF_PROCESSORS = 75; // N
    private static final int NUMBER_OF_PROCESSES = 10_000;

    private static final double MAX_PROCESSOR_LOAD = 0.5; //p
    private static final double MIN_PROCESSOR_LOAD = 0.3; //r

    private static final double PROCESS_MIN_LOAD = 0.2;
    private static final double PROCESS_MAX_LOAD = 0.6;
    private static final int PROCESS_MIN_TIME = 50;
    private static final int PROCESS_MAX_TIME = 200;

    private static final int MAX_MIGRATION_REQUESTS = 15; // z


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
                new ProcessorAllocationManager_1(copyProcessors(processors), MAX_MIGRATION_REQUESTS),
                new ProcessorAllocationManager_2(copyProcessors(processors))
        };

        for (ProcessorAllocationManager manager : managers)
            System.out.println(manager.process(processesQueue(processes)));
    }

    private static List<Processor> generateProcessors(int numberOfProcessors) {
        return range(0, numberOfProcessors)
                .mapToObj(i -> new Processor(MIN_PROCESSOR_LOAD, MAX_PROCESSOR_LOAD))
                .collect(toList());
    }

    private static Queue<Process> processesQueue(Process[] processes) {
        Queue<Process> copy = new LinkedList<>();
        for (Process process : processes)
            copy.add(new Process(process));
        return copy;
    }

    public static List<Processor> copyProcessors(List<Processor> processors) {
        List<Processor> copy = new ArrayList<>(processors.size());
        for (Processor processor : processors) {
            copy.add(new Processor(processor));
        }
        return copy;
    }

}
