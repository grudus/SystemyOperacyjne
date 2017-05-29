package processor.allocation;

import process.Process;
import processor.Processor;
import processor.ProcessorAllocationStats;

import java.util.List;
import java.util.Queue;

public class ProcessAllocationManager_2 extends ProcessorAllocationManager {

    public ProcessAllocationManager_2(List<Processor> processors, Queue<Process> processes) {
        super(processors, processes);
    }

    @Override
    public ProcessorAllocationStats process() {
        return null;
    }
}
