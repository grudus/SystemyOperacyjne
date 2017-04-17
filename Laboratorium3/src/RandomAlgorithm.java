import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RandomAlgorithm extends Algorithm<PageRequest> {

    private List<Page> pages;

    protected RandomAlgorithm(int frames) {
        super(frames);
        pages = new ArrayList<>(frames);
    }

    @Override
    protected int calculate(Queue<PageRequest> items) {
        int pagesOut = 0;

        while (!items.isEmpty()) {
            PageRequest request = items.poll();
            if (emptyFramesExists()) {
                addPage(request);
                pagesOut++;
            }
            else {
                if (!requestAlreadyInFrame(request)) {
                    removeRandomly(items);
                    addPage(request);
                    ++pagesOut;
                }
            }
        }

        return pagesOut;    }

    private void removeRandomly(Queue<PageRequest> requests) {
        pages.remove(new Random().nextInt(pages.size()));}

    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    private boolean requestAlreadyInFrame(PageRequest request) {
        return pages.stream().anyMatch(page -> page.id == request.pageId);
    }

    private void addPage(PageRequest request) {
        pages.add(new Page(request.pageId));
    }

    private boolean emptyFramesExists() {
        return pages.size() < frames;
    }

}
