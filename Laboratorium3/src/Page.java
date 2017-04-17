import static java.lang.String.format;

public class Page {
    public final int id;

    public Page(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return format("{%d}", id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        return id == page.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
