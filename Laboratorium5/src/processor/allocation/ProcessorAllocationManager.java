package processor.allocation;

import java.util.List;
import java.util.Queue;
import process.Process;
import processor.Processor;
import processor.ProcessorAllocationStats;

import static java.util.Collections.unmodifiableList;

public abstract class ProcessorAllocationManager {
    protected final List<Processor> processors;
    protected final Queue<Process> processes;

    public ProcessorAllocationManager(List<Processor> processors, Queue<Process> processes) {
        this.processors = unmodifiableList(processors);
        this.processes = processes;
    }

    public abstract ProcessorAllocationStats process();
}