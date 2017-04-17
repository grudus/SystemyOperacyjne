import java.util.*;

import static java.lang.System.nanoTime;

public class LeastRecentlyUsedAlgorithm extends Algorithm<List<AbstractMap.SimpleEntry<Page, Long>> > {

    public LeastRecentlyUsedAlgorithm(int frames) {
        super(frames);
        pages = new ArrayList<>(frames);
    }

    @Override
    protected void cleanPages(PageRequest request) {
        pages.stream().filter(entry -> entry.getKey().id == request.pageId)
                .forEach(entry -> entry.setValue(nanoTime()));
    }

    @Override
    protected void removePage(PageRequest request) {
        pages.sort(Comparator.comparingLong(AbstractMap.SimpleEntry::getValue));
        pages.remove(0);
    }

    @Override
    protected boolean requestAlreadyInFrame(PageRequest request) {
        return pages.stream().anyMatch(page -> page.getKey().id == request.pageId);
    }

    @Override
    protected void addPage(PageRequest request) {
        pages.add(new AbstractMap.SimpleEntry<>(new Page(request.pageId), nanoTime()));
    }
}
