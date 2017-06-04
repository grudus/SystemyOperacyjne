import static java.lang.String.format;

public class ProcessorAllocationStats {
    private final String description;
    private final double averageLoad;
    private final double averageDeviation;
    private final int loadRequests;
    private final int loadMigrations;

    public ProcessorAllocationStats(String description, double averageLoad, double averageDeviation, int loadRequests, int loadMigrations) {
        this.description = description;
        this.averageLoad = averageLoad;
        this.averageDeviation = averageDeviation;
        this.loadRequests = loadRequests;
        this.loadMigrations = loadMigrations;
    }

    @Override
    public String toString() {
        return format("%s:\n1) Average load: %.2f\n2) Average deviation: %.2f\n3) Number of load requests: %d\n4) Number of load migrations: %d\n",
                description, averageLoad, averageDeviation, loadRequests, loadMigrations);
    }
}