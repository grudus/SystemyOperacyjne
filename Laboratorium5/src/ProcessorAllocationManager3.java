import java.util.List;
import java.util.Queue;

public class ProcessorAllocationManager3 extends ProcessorAllocationManager {
    private static final String DESCRIPTION = ".Jak w pkt 2, z tym że procesory o obciążeniu mniejszym od minimalnego progu r pytają losowo wybrane\n" +
            " procesory i jesli obc. zapytanego jest większe od p, pytający przejmuje część jego zadań (założyć jaką).";


    public ProcessorAllocationManager3(List<Processor> processors) {
        super(processors);
    }


    @Override
    public ProcessorAllocationStats process(Queue<Process> processes) {
       return null;
    }
}
