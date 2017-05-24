import java.util.Random;

public class Generator {
    private final int iloscProcesow;
    private final int iloscStron;
    private final int minimumOdwolan;
    private final int maximumOdwolan;
    private final int promienLokalnosci;
    private final Random random;

    public Generator(int iloscProcesow, int iloscStron, int minimumOdwolan, int maximumOdwolan, int promienLokalnosci) {
        this.iloscProcesow = iloscProcesow;
        this.iloscStron = iloscStron;
        this.minimumOdwolan = minimumOdwolan;
        this.maximumOdwolan = maximumOdwolan;
        this.promienLokalnosci = promienLokalnosci;
        random = new Random();
    }

    public int[][] generujProcesy() {
        int[][] tablicaProcesow = new int[iloscProcesow][];
        for (int j = 0; j < tablicaProcesow.length; ++j) {
            int ileOdwolan = losuj(minimumOdwolan, maximumOdwolan);
            int[] tablicaOdwolanDoStron = new int[ileOdwolan];
            tablicaOdwolanDoStron[0] = losuj(0, iloscStron);
            for (int i = 1; i < ileOdwolan; ++i) {
                int min = (tablicaOdwolanDoStron[i - 1] - promienLokalnosci > 0)
                        ? tablicaOdwolanDoStron[i - 1] - promienLokalnosci
                        : 0;
                int max = (tablicaOdwolanDoStron[i - 1] + promienLokalnosci < iloscStron)
                        ? tablicaOdwolanDoStron[i - 1] + promienLokalnosci
                        : iloscStron;
                tablicaOdwolanDoStron[i] = losuj(min, max);
            }
            tablicaProcesow[j] = tablicaOdwolanDoStron;
        }
        return tablicaProcesow;
    }

    private  int losuj(int minLiczba, int maxLiczba) {
        if (maxLiczba == 0) return 0;
        return random.nextInt(maxLiczba - minLiczba) + minLiczba;
    }
}
