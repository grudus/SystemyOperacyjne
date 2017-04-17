import java.util.*;

public class SecondChanceAlgorithm extends Algorithm<PageRequest> {

    private Queue<AbstractMap.SimpleEntry<Page, Boolean>> pagesUsed;

    protected SecondChanceAlgorithm(int frames) {
        super(frames);
        pagesUsed = new LinkedList<>();
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
                    removePageWithoutChance();
                    addPage(request);
                    ++pagesOut;
                }
                else
                    markAsNotUsed(request);
            }
        }

        return pagesOut;
    }

    private void markAsNotUsed(PageRequest request) {
        pagesUsed.stream().filter(entry -> entry.getKey().id == request.pageId)
                .forEach(entry -> entry.setValue(false));
    }

    private void addPage(PageRequest request) {
        pagesUsed.add(new AbstractMap.SimpleEntry<>(new Page(request.pageId), false));
    }

    private void removePageWithoutChance() {
        while (true) {
            AbstractMap.SimpleEntry<Page, Boolean> entry = pagesUsed.poll();
            if (!entry.getValue())
                pagesUsed.add(new AbstractMap.SimpleEntry<>(entry.getKey(), true));
            else
                break;
        }
    }


    private boolean requestAlreadyInFrame(PageRequest request) {
        return pagesUsed.stream().anyMatch(entry -> entry.getKey().id == request.pageId);
    }

    private boolean emptyFramesExists() {
        return pagesUsed.size() < frames;
    }
}
