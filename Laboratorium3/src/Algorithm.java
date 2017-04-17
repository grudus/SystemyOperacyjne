import java.util.List;
import java.util.Queue;

import static java.lang.String.format;

public abstract class Algorithm<T> {

    protected final int frames;

    protected Algorithm(int frames) {
        this.frames = frames;
    }

    public int process(Queue<T> items) {
        if (items.size() <= frames)
            return items.size();
        return calculate(items);
    }

    protected abstract int calculate(Queue<T> items);

    @Override
    public String toString() {
        return format("%s using %d frames", getClass().getSimpleName(), frames);
    }
}
