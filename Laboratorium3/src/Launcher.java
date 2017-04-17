import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.of;

public class Launcher {
    public static void main(String[] args) {
        Random random = new Random();
        final int FRAMES = random.nextInt(20) + 1;


        Queue<PageRequest> requestList =
                range(0, 3000).map(i -> random.nextInt(FRAMES * 3))
                        .mapToObj(PageRequest::new)
                        .collect(toCollection(LinkedList::new));


        Stream.of(
                new FifoAlgorithm(FRAMES),
                new LeastRecentlyUsedAlgorithm(FRAMES),
                new OptimalAlgorithm(FRAMES),
                new RandomAlgorithm(FRAMES),
                new SecondChanceAlgorithm(FRAMES))
                .forEach(alg -> {
                    final int pages = alg.process(new LinkedList<>(requestList));
                    System.out.println(format("%s uses %d create replacements", alg, pages));
                });
    }
}
