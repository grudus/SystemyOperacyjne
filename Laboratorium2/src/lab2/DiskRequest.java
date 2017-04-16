package lab2;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DiskRequest {
    private int position;
    private int arrivalTime;
    private int deadline;

    public DiskRequest(int position) {
        this(position, 0, 0);
    }

    public DiskRequest(int position, int arrivalTime, int deadline) {
        this.position = position;
        this.arrivalTime = arrivalTime;
        this.deadline = deadline;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    @NotNull
    public static DiskRequest random(int maxPosition, int maxArrival, int maxDeadline) {
        Random random = new Random();
        return new DiskRequest(random.nextInt(maxPosition), random.nextInt(maxArrival), random.nextInt(maxDeadline));
    }

    @Override
    public String toString() {
        return "DiskRequest{" +
                "position=" + position +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
}
