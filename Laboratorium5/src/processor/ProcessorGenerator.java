package processor;

import java.util.List;

import static java.util.stream.Collectors.*;
import static java.util.stream.IntStream.range;

public class ProcessorGenerator {
    private final int size;
    private final double processorMinimumLoad;
    private final double processorMaximumLoad;

    public ProcessorGenerator(int size, double processorMinimumLoad, double processorMaximumLoad) {
        this.size = size;
        this.processorMinimumLoad = processorMinimumLoad;
        this.processorMaximumLoad = processorMaximumLoad;
    }

    public List<Processor> generate() {
        return range(0, size)
                .mapToObj(i -> new Processor(processorMinimumLoad, processorMaximumLoad))
                .collect(toList());
    }
}
