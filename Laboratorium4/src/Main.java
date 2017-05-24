import java.util.Arrays;

public class Main {
    public static final int ILOSC_PROCESOW = 10;
    public static final int ILOSC_STRON = 1000;
    public static final int MINIMUM_ODWOLAN = 30;
    public static final int MAXIMUM_ODWOLAN = 300;
    public static final int PROMIEN_LOKALNOSCI = 10;
    public static final int ILOSC_RAMEK = 100;

    public static void main(String[] args) {
        int[][] procesy = new Generator(
                ILOSC_PROCESOW,
                ILOSC_STRON,
                MINIMUM_ODWOLAN,
                MAXIMUM_ODWOLAN,
                PROMIEN_LOKALNOSCI
        ).generujProcesy();


        PrzydzialRamek[] przydzialyRamek = new PrzydzialRamek[] {
                new PrzydzialRowny(copy(procesy), ILOSC_RAMEK),
                new PrzydzialProporcjonalny(copy(procesy), ILOSC_RAMEK),
                new PrzydzialSterowanyCzestosciaBledowStrony(copy(procesy), ILOSC_RAMEK),
                new PrzydzialModelStrefowy(copy(procesy), ILOSC_RAMEK)
        };


        System.out.println("____________ Wyniki globalne: _______________");
        for (PrzydzialRamek przydzialRamek : przydzialyRamek)
            System.out.println(przydzialRamek.getClass().getSimpleName() + ": " + przydzialRamek.wykonaj());


        for (int i = 0; i < procesy.length; ++i) {
            System.out.println("\n_______________ Wyniki dla procesu " + i + ":");
            for (PrzydzialRamek przydzialRamek : przydzialyRamek)
                System.out.println(przydzialRamek.getClass().getSimpleName() + ": " + przydzialRamek.getWynikProcesu(i));
        }
    }

    private static int[][] copy(int[][] array) {
        return Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
    }
}
