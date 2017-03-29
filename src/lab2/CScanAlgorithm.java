package lab2;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.util.Comparator.comparingInt;

public class CScanAlgorithm extends ScanAlgorithm {


    protected CScanAlgorithm(int diskSize, int headerStartPosition, int previousRequestPosition) {
        super(diskSize, headerStartPosition, previousRequestPosition);
    }

    @Override
    public void process(List<DiskRequest> objects) {
        List<DiskRequest> sorted = new ArrayList<>(objects);
        sorted.add(new DiskRequest(headerStartPosition));
        sorted.add(new DiskRequest(0));
        sorted.add(new DiskRequest(diskSize));
        sorted.sort(comparingInt(DiskRequest::getPosition));
        boolean isRight = headerStartPosition - previousRequestPosition >= 0;

        int startIndex = (int) sorted.stream().filter(i -> i.getPosition() < headerStartPosition).count();

        sumByDirection(startIndex, isRight, sorted);

        distance += diskSize;
        List<DiskRequest> range = isRight ? sorted.subList(0, startIndex) : sorted.subList(startIndex, sorted.size());
        sumByDirection(isRight ? 0 : range.size() - 1, isRight, range);
    }
}
