package process;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.Queue;

public class ProcessGenerator {
    private final int size;
    private final double maxProcessorLoad;
    private final int maxProcessTime;
    private final SecureRandom random;

    public ProcessGenerator(int size, double maxProcessorLoad, int maxProcessTime) {
        this.size = size;
        this.maxProcessorLoad = maxProcessorLoad;
        this.maxProcessTime = maxProcessTime;
        random = new SecureRandom();
    }

    public Queue<Process> generate() {
        Queue<Process> queue = new LinkedList<>();
        int processNumber = 0;
        while (processNumber < size) {
            Process process = random.nextInt(3) % 2 == 0
                    ? Process.EMPTY
                    : new Process(random.nextDouble() % maxProcessorLoad, random.nextInt(maxProcessTime));
            queue.add(process);
            ++processNumber;
        }
        return queue;
    }
}
