import java.util.*;

import static java.lang.System.nanoTime;

public class LeastRecentlyUsedAlgorithm extends Algorithm<PageRequest> {
    List<AbstractMap.SimpleEntry<Page, Long>> pagesWithTimestamp;

    public LeastRecentlyUsedAlgorithm(int frames) {
        super(frames);
        pagesWithTimestamp = new ArrayList<>(frames);
    }

    @Override
    public int calculate(Queue<PageRequest> items) {
        int pagesOut = 0;

        while (!items.isEmpty()) {
            PageRequest request = items.poll();
            if (emptyFramesExists()) {
                addPage(request);
                pagesOut++;
            }
            else {
                if (!requestAlreadyInFrame(request)) {
                    removeLastUsed();
                    addPage(request);
                    ++pagesOut;
                }
                else
                    updatePage(request);
            }
        }

        return pagesOut;
    }

    private void updatePage(PageRequest request) {
        pagesWithTimestamp.stream().filter(entry -> entry.getKey().id == request.pageId)
                .forEach(entry -> entry.setValue(nanoTime()));
    }

    private void removeLastUsed() {
        pagesWithTimestamp.sort(Comparator.comparingLong(AbstractMap.SimpleEntry::getValue));
        pagesWithTimestamp.remove(0);
    }

    private boolean requestAlreadyInFrame(PageRequest request) {
        return pagesWithTimestamp.stream().anyMatch(page -> page.getKey().id == request.pageId);
    }

    private void addPage(PageRequest request) {
        pagesWithTimestamp.add(new AbstractMap.SimpleEntry<>(new Page(request.pageId), nanoTime()));
    }

    private boolean emptyFramesExists() {
        return pagesWithTimestamp.size() < frames;
    }
}
