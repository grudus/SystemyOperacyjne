import java.util.List;
import java.util.Queue;

import static java.util.Collections.unmodifiableList;

public abstract class ProcessorAllocationManager {
    protected final List<Processor> processors;

    public ProcessorAllocationManager(List<Processor> processors) {
        this.processors = unmodifiableList(processors);
    }

    public abstract ProcessorAllocationStats process(Queue<Process> processes);


    protected double averageDeviation() {
        return processors.stream().mapToDouble(Processor::averageDeviation)
                .sum() / processors.size();
    }

    protected double averageLoad() {
        return processors.stream().mapToDouble(Processor::averageLoad)
                .sum() / processors.size();
    }

    protected void processAll() {
        processors.forEach(Processor::process);
    }
}
