import java.util.*;

public class SecondChanceAlgorithm extends Algorithm<Queue<AbstractMap.SimpleEntry<Page, Boolean>>> {
    

    protected SecondChanceAlgorithm(int frames) {
        super(frames);
        pages = new LinkedList<>();
    }

    @Override
    protected void cleanPages(PageRequest request) {
        pages.stream().filter(entry -> entry.getKey().id == request.pageId)
                .forEach(entry -> entry.setValue(false));
    }

    @Override
    protected void addPage(PageRequest request) {
        pages.add(new AbstractMap.SimpleEntry<>(new Page(request.pageId), false));
    }

    @Override
    protected void removePage(PageRequest request) {
        while (true) {
            AbstractMap.SimpleEntry<Page, Boolean> entry = pages.poll();
            if (!entry.getValue())
                pages.add(new AbstractMap.SimpleEntry<>(entry.getKey(), true));
            else
                break;
        }
    }

    @Override
    protected boolean requestAlreadyInFrame(PageRequest request) {
        return pages.stream().anyMatch(entry -> entry.getKey().id == request.pageId);
    }
}
