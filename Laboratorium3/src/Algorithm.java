import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;

import static java.lang.String.format;

public abstract class Algorithm<T extends Collection<?>> {

    protected final int frames;
    protected T pages;
    protected int pagesOut;
    protected BiConsumer<PageRequest, Queue<PageRequest>> removeFunction;

    protected Algorithm(int frames) {
        this.frames = frames;
        this.removeFunction = (request, requests) -> removePage(request);
    }

    public int process(Queue<PageRequest> requests) {
        if (requests.size() <= frames)
            return requests.size();

        while (!requests.isEmpty()) {
            PageRequest request = requests.poll();
            if (emptyFramesExists()) {
                addPage(request);
                ++pagesOut;
            } else {
                if (!requestAlreadyInFrame(request)) {
                    removeFunction.accept(request, requests);
                    addPage(request);
                    ++pagesOut;
                }
                else
                    cleanPages(request);
            }
        }
        return pagesOut;
    }

    protected void cleanPages(PageRequest request) {}

    protected void removePage(PageRequest request) {}

    protected abstract boolean requestAlreadyInFrame(PageRequest request);

    protected abstract void addPage(PageRequest request);

    private boolean emptyFramesExists() {
        return pages.size() < frames;
    }


    @Override
    public String toString() {
        return format("%s using %d frames", getClass().getSimpleName(), frames);
    }
}
