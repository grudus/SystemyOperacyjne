package lab2;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.util.Comparator.comparingInt;

public class EdfAlgorithm extends Algorithm<DiskRequest> {


    protected EdfAlgorithm(int diskSize, int headerStartPosition) {
        super(diskSize, headerStartPosition);
    }

    @Override
    public void process(List<DiskRequest> objects) {
        List<DiskRequest> sorted = new ArrayList<>(objects);
        sorted.sort(comparingInt(DiskRequest::getDeadline));

        distance += abs(headerStartPosition - sorted.get(0).getPosition());

        for (int i = 0; i < sorted.size() - 1; i++)
            distance += abs(sorted.get(i).getPosition() - sorted.get(i+1).getPosition());

    }
}
