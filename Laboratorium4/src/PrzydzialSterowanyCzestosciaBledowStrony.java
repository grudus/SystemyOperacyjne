
public class PrzydzialSterowanyCzestosciaBledowStrony extends PrzydzialRamek {


    public PrzydzialSterowanyCzestosciaBledowStrony(int[][] procesy, int iloscRamek) {
        super(procesy, iloscRamek);
    }

    @Override
    public int wykonaj() {
        int ramekNaProces = iloscRamek / procesy.length;
        for (int i = 0; i < procesy.length; ++i) procesy[i].setIleRamek(ramekNaProces);
        boolean czyWykonanoWszystkie = false;
        int licznik = 0;
        int[] tablicaBledowStron = new int[procesy.length];
        int[] tablicaPriorytetow = new int[procesy.length];
        int ileDostepnych = 0;
        while (!czyWykonanoWszystkie) {
            licznik++;
            czyWykonanoWszystkie = true;
            for (int i = 0; i < procesy.length; ++i) {
                if (!procesy[i].wykonajJeden()) czyWykonanoWszystkie = false;
            }
            if (licznik >= ramekNaProces) {
                for (int i = 0; i < procesy.length; ++i) {
                    tablicaPriorytetow[i] = procesy[i].getLicznikBledowStron() - tablicaBledowStron[i];
                    tablicaBledowStron[i] = procesy[i].getLicznikBledowStron();
                    if (tablicaPriorytetow[i] <= licznik / 3 && procesy[i].getIleRamek() > 1) {
                        procesy[i].usunRamke();
                        ileDostepnych++;
                    }
                }
                while (ileDostepnych > 0 && licznik > 0) {
                    for (int i = 0; i < procesy.length && ileDostepnych > 0; ++i) {
                        if (tablicaPriorytetow[i] >= licznik) {
                            procesy[i].dodajRamke();
                            ileDostepnych--;
                        }
                    }
                    licznik--;
                }
                licznik = 0;
            }

        }
        for (int i = 0; i < procesy.length; ++i) {
            licznikBledowStron += procesy[i].getLicznikBledowStron();
        }
        return licznikBledowStron;
    }
}
