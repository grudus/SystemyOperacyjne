import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.IntStream.range;
import static java.util.stream.Stream.of;

public class Launcher {
    public static void main(String[] args) {
        Random random = new Random();
        final int FRAMES = random.nextInt(10) + 1;


        Queue<PageRequest> requestList =
                range(0, 3000).map(i -> random.nextInt(FRAMES * 3))
                        .mapToObj(PageRequest::new)
                        .collect(toCollection(LinkedList::new));


        Stream.of(
                new FifoAlgorithm(FRAMES),
                new OptimalAlgorithm(FRAMES),
                new LeastRecentlyUsedAlgorithm(FRAMES),
                new SecondChanceAlgorithm(FRAMES),
                new RandomAlgorithm(FRAMES))
                .forEach(alg -> {
                    final int pages = alg.process(new LinkedList<>(requestList));
                    System.out.println(format("%s create %d replacements", alg, pages));
                });
    }

    public static int[] generate(int ileOdwolan, int promien) {
        int tablicaOdwolanDoStron[] = new int[ileOdwolan];
        int ileStron = 10;
        tablicaOdwolanDoStron[0] = losuj(0, ileStron);
        for (int i = 1; i < ileOdwolan; ++i) {
            int min = (tablicaOdwolanDoStron[i - 1] - promien > 0)
                    ? tablicaOdwolanDoStron[i - 1] - promien
                    : 0;
            int max = (tablicaOdwolanDoStron[i - 1] + promien < ileStron)
                    ? tablicaOdwolanDoStron[i - 1] + promien
                    : ileStron;
            tablicaOdwolanDoStron[i] = losuj(min, max);
        }
        return tablicaOdwolanDoStron;
    }

    private static int losuj(int minLiczba, int maxLiczba) {
        if (maxLiczba == 0) return 0;
        return new Random().nextInt(maxLiczba - minLiczba) + minLiczba;
    }
}
