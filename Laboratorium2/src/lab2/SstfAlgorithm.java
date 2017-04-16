package lab2;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.util.Comparator.comparingInt;

public class SstfAlgorithm extends Algorithm<DiskRequest> {


    protected SstfAlgorithm(int diskSize, int headerStartPosition) {
        super(diskSize, headerStartPosition);
    }

    @Override
    public void process(List<DiskRequest> objects) {
        List<DiskRequest> sorted = new ArrayList<>(objects);
        sorted.add(new DiskRequest(headerStartPosition));
        sorted.sort(comparingInt(DiskRequest::getPosition));

        int startIndex = (int) sorted.stream().filter(i -> i.getPosition() < headerStartPosition).count();

        boolean rightDirection = (startIndex != sorted.size() - 1)
                && (startIndex == 0 || abs(sorted.get(startIndex).getPosition() - headerStartPosition) > abs(sorted.get(startIndex + 1).getPosition() - headerStartPosition));

        int index = sumByDirection(startIndex, rightDirection, sorted);
        sumByDirection(index, !rightDirection, sorted);
    }

    private int sumByDirection(int actualIndex, boolean isRightDirection, List<DiskRequest> sorted) {
        int i;
        for (i = actualIndex; stopLoopCondition(i, isRightDirection, sorted); i = findNextIndex(i, isRightDirection)) {
            distance += abs(sorted.get(i).getPosition() - sorted.get(findNextIndex(i, isRightDirection)).getPosition());
        }
        return i;
    }

    private int findNextIndex(int i, boolean rightDirection) {
        return rightDirection ? i + 1 : i - 1;
    }

    private boolean stopLoopCondition(int i, boolean rightDirection, List<DiskRequest> sorted) {
        return rightDirection ? i < sorted.size() - 1 : i >= 1;
    }
}
