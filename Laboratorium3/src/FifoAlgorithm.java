import java.util.*;

public class FifoAlgorithm extends Algorithm<PageRequest> {

    private Queue<Page> pages;

    public FifoAlgorithm(int frames) {
        super(frames);
        pages = new LinkedList<>();
    }


    @Override
    public int calculate(Queue<PageRequest> requests) {
        int pagesOut = 0;

        while (!requests.isEmpty()) {
            PageRequest request = requests.poll();
            if (emptyFramesExists()) {
                pages.add(new Page(request.pageId));
                ++pagesOut;
            }
            else {
                if (!requestAlreadyInFrame(request)) {
                    pages.poll();
                    pages.add(new Page(request.pageId));
                    ++pagesOut;
                }
            }
        }

        return pagesOut;
    }

    private boolean requestAlreadyInFrame(PageRequest request) {
        return pages.stream().anyMatch(page -> page.id == request.pageId);
    }

    private boolean emptyFramesExists() {
        return pages.size() < frames;
    }
}
