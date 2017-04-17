import java.util.*;

public class FifoAlgorithm extends Algorithm<Queue<Page>> {

    public FifoAlgorithm(int frames) {
        super(frames);
        pages = new LinkedList<>();
    }


    @Override
    protected void removePage(PageRequest request) {
        pages.poll();
    }

    @Override
    protected boolean requestAlreadyInFrame(PageRequest request) {
        return pages.stream().anyMatch(page -> page.id == request.pageId);
    }

    protected void addPage(PageRequest request) {
        pages.add(new Page(request.pageId));
    }
}
