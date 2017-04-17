import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Stream.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlgorithmsTest {

    private Queue<PageRequest> requests;

    @BeforeEach
    public void init() {
        requests = of(1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5)
                .map(PageRequest::new)
                .collect(toCollection(LinkedList::new));
    }

    @Test
    public void fifoWith3FramesTest() {
        assertCalculation(9, new FifoAlgorithm(3));
    }


    @Test
    public void fifoWith4FramesTest() {
        assertCalculation(10, new FifoAlgorithm(4));
    }


    @Test
    public void lruWith4FramesTest() {
        assertCalculation(8, new LeastRecentlyUsedAlgorithm(4));
    }


    @Test
    public void scWith4FramesTest() {
        Queue<PageRequest> requests =  of(1, 2, 3, 4, 5, 3, 1, 5, 2)
                .map(PageRequest::new).collect(toCollection(LinkedList::new));

        assertCalculation(7, new LeastRecentlyUsedAlgorithm(4), requests);
    }


    @Test
    public void scWith3FramesTest() {
        Queue<PageRequest> requests =  of(2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2)
                .map(PageRequest::new).collect(toCollection(LinkedList::new));

        assertCalculation(7, new LeastRecentlyUsedAlgorithm(4), requests);
    }

    @Test
    public void optWith4FramesTest() {
        assertCalculation(6, new OptimalAlgorithm(4));
    }

    private void assertCalculation(int pagesOut, Algorithm<?> algorithm, Queue<PageRequest> requests) {
        assertEquals(pagesOut, algorithm.process(requests));
    }


    private void assertCalculation(int pagesOut, Algorithm<?> algorithm) {
        assertCalculation(pagesOut, algorithm, this.requests);
    }

}
