package lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.util.Comparator.comparingInt;

public class ScanAlgorithm extends Algorithm<DiskRequest> {

    protected int previousRequestPosition;

    protected ScanAlgorithm(int diskSize, int headerStartPosition, int previousRequestPosition) {
        super(diskSize, headerStartPosition);
        this.previousRequestPosition = previousRequestPosition;
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

        int index = sumByDirection(startIndex, isRight, sorted);

        distance += abs(sorted.get(index).getPosition() - sorted.get(startIndex).getPosition());
        index = sumByDirection(startIndex, !isRight, sorted);
        distance -= abs(sorted.get(index).getPosition() - sorted.get(findNextIndex(index, isRight)).getPosition());
    }


    protected int sumByDirection(int actualIndex, boolean isRightDirection, List<DiskRequest> sorted) {
        int i;
        for (i = actualIndex; stopLoopCondition(i, isRightDirection, sorted); i = findNextIndex(i, isRightDirection)) {
            distance += abs(sorted.get(i).getPosition() - sorted.get(findNextIndex(i, isRightDirection)).getPosition());
        }
        return i;
    }

    protected int findNextIndex(int i, boolean rightDirection) {
        return rightDirection ? i + 1 : i - 1;
    }

    private boolean stopLoopCondition(int i, boolean rightDirection, List<DiskRequest> sorted) {
        return rightDirection ? i < sorted.size() - 1 : i >= 1;
    }
}
