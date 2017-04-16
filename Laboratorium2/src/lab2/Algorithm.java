package lab2;


import java.util.List;

public abstract class Algorithm<T> {

    protected int distance;
    protected final int diskSize;
    protected final int headerStartPosition;

    protected Algorithm(int diskSize, int headerStartPosition) {
        this.diskSize = diskSize;
        this.headerStartPosition = headerStartPosition;
    }

    public abstract void process(List<T> objects);

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public int getDistance() {
        return distance;
    }
}
