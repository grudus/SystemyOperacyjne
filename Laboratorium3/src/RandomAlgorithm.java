import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RandomAlgorithm extends Algorithm<List<Page>> {

    protected RandomAlgorithm(int frames) {
        super(frames);
        pages = new ArrayList<>(frames);
        removeFunction = (request, requests) -> removeRandomly();
    }

    private void removeRandomly() {
        pages.remove(new Random().nextInt(pages.size()));}

    @Override
    protected boolean requestAlreadyInFrame(PageRequest request) {
        return pages.stream().anyMatch(page -> page.id == request.pageId);
    }

    @Override
    protected void addPage(PageRequest request) {
        pages.add(new Page(request.pageId));
    }
}
