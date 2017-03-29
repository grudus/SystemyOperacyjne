package lab2;


import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public class Launcher {
    public static void main(String[] args) {
        final Random RANDOM = new Random();

        final int DISK_SIZE = /*RANDOM.nextInt(1000);*/4999;
        final int HEADER_POSITION = /*RANDOM.nextInt(DISK_SIZE);*/ 143;
        final int PREVIOUS_POSITION = /*RANDOM.nextInt(DISK_SIZE);*/ 125;

        List<DiskRequest> requestList =
//                range(0, 10).mapToObj(i -> DiskRequest.random(DISK_SIZE, 1, DISK_SIZE / 5))
////                .peek(System.out::println)
//                .collect(Collectors.toList())

                asList(new DiskRequest(86),
                        new DiskRequest(1470),
                        new DiskRequest(913),
                        new DiskRequest(1774),
                        new DiskRequest(948),
                        new DiskRequest(1509),
                        new DiskRequest(1022),
                        new DiskRequest(1750),
                        new DiskRequest(130)
                );
        ;

        List<Algorithm<DiskRequest>> algorithms = asList(new FcfsAlgorithm(DISK_SIZE, HEADER_POSITION),
                new SstfAlgorithm(DISK_SIZE, HEADER_POSITION),
                new ScanAlgorithm(DISK_SIZE, HEADER_POSITION, PREVIOUS_POSITION),
                new CScanAlgorithm(DISK_SIZE, HEADER_POSITION, PREVIOUS_POSITION),
                new EdfAlgorithm(DISK_SIZE, HEADER_POSITION));

        algorithms.forEach(algorithm -> {
            algorithm.process(copy(requestList));
            System.out.println(format("%s, distance: %d", algorithm, algorithm.getDistance()));
        });


    }

    private static List<DiskRequest> copy(List<DiskRequest> requestList) {
        return requestList.stream().collect(Collectors.toList());
    }
}
