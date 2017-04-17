import static java.lang.String.format;

public class PageRequest {
    public final int pageId;

    public PageRequest(int pageId) {
        this.pageId = pageId;
    }

    @Override
    public String toString() {
        return format("[%d]", pageId);
    }
}
