import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Lab5 {
    public static final int NUMBER_OF_PROCESSORS = 75;
    public static final int MAXIMUM_NUMBER_OF_REQUESTS = 15;
    public static final int NUMBER_OF_PROCESSES = 5000;
    public static final double MAXIMUM_LOAD = 0.5;
    public static final double MINIMUM_LOAD = 0.05;

    public static final int MAX_PROCESS_TIME = 250;
    public static final double MAX_PROCESSOR_LOAD = 0.5;

    public static void main(String[] args) {
        System.out.println("Przydział procesorów w syst. rozproszonych");

        List<Processor> processors = new ProcessorGenerator(NUMBER_OF_PROCESSORS, MINIMUM_LOAD, MAXIMUM_LOAD).generate();
        Queue<Process> processes = new ProcessGenerator(NUMBER_OF_PROCESSES, MAX_PROCESSOR_LOAD, MAX_PROCESS_TIME).generate();

        ProcessorAllocationManager[] managers = new ProcessorAllocationManager[] {
                new ProcessorAllocationMager_1(processors, new LinkedList<>(processes), MAXIMUM_NUMBER_OF_REQUESTS)
        };

        for (ProcessorAllocationManager manager : managers) {
            System.out.println(manager.process());
        }


    }
}
