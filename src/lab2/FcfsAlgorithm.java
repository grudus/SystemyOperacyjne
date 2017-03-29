package lab2;

import java.util.List;

public class FcfsAlgorithm extends Algorithm<DiskRequest> {

    private int distance;

    public FcfsAlgorithm(int diskSize, int headerPosition) {
        super(diskSize, headerPosition);
    }

    @Override
    public void process(List<DiskRequest> objects) {
        distance += Math.abs(headerStartPosition - objects.get(0).getPosition());
        for (int i = 0; i < objects.size() - 1; i++)
            distance += Math.abs(objects.get(i).getPosition() - objects.get(i+1).getPosition());

    }

    @Override
    public int getDistance() {
        return distance;
    }
}
