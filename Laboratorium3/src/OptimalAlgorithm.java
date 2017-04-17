import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.System.nanoTime;

public class OptimalAlgorithm extends Algorithm<PageRequest> {

    private List<Page> pages;

    public OptimalAlgorithm(int frames) {
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
                    removeLast(items);
                    addPage(request);
                    ++pagesOut;
                }
            }
        }

        return pagesOut;    }

    private void removeLast(Queue<PageRequest> requests) {
        List<Integer> pagesIds = pages.stream().map(page -> page.id).collect(Collectors.toList());
        List<Page> pagesByTime = requests.stream()
                .filter(req -> pagesIds.contains(req.pageId))
                .filter(distinctByKey(request -> request.pageId))
                .collect(Collectors.toMap(request -> new Page(request.pageId), request -> System.nanoTime()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingLong(Map.Entry::getValue))
                .filter(distinctByKey(entry -> entry.getKey().id))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        Page toRemove = pagesByTime.size() < frames ?
                pages.stream().filter(page -> !pagesByTime.contains(page)).findFirst().orElse(pages.get(0)) :
                pagesByTime.get(pagesByTime.size() - 1);

        pages = pages.stream().filter(page -> page.id != toRemove.id)
                .collect(Collectors.toList());
    }

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
