import java.util.HashSet;

public class PrzydzialModelStrefowy extends PrzydzialRamek {

    public PrzydzialModelStrefowy(int[][] procesy, int iloscRamek) {
        super(procesy, iloscRamek);
    }

    @Override
    public int wykonaj() {
        int ramekNaProces = iloscRamek / procesy.length;

        HashSet<Integer>[] tablicaZbiorowOstatnichOdwolan = new HashSet[procesy.length];
        for (int i = 0; i < procesy.length; ++i) {
            procesy[i].setIleRamek(ramekNaProces);
            tablicaZbiorowOstatnichOdwolan[i] = new HashSet<>();
        }
        boolean czyWykonanoWszystkie = false;
        int licznik = 0;
        int ileDostepnych = 0;
        while (!czyWykonanoWszystkie) {
            licznik++;
            czyWykonanoWszystkie = true;
            for (int i = 0; i < procesy.length; ++i) {
                if (!procesy[i].wykonajJeden()) {
                    tablicaZbiorowOstatnichOdwolan[i].add(procesy[i].getOstatnioUzyty());
                    czyWykonanoWszystkie = false;
                }
            }
            if (licznik >= 2 * ramekNaProces) {
                for (int i = 0; i < procesy.length; ++i) {
                    if (procesy[i].czyKoniec()) {
                        ileDostepnych += procesy[i].getIleRamek();
                        procesy[i].setIleRamek(0);
                    }
                    for (int j = tablicaZbiorowOstatnichOdwolan[i].size() - procesy[i].getIleRamek(); j > 0 && procesy[i].getIleRamek() > 1; --j) {
                        ileDostepnych++;
                        procesy[i].usunRamke();
                    }
                }
                while (ileDostepnych > 0 && licznik > 0) {
                    for (int i = 0; i < procesy.length && ileDostepnych > 0; ++i) {
                        int ilePotrzebnych = tablicaZbiorowOstatnichOdwolan[i].size() - procesy[i].getIleRamek();
                        if (ilePotrzebnych > 0) {
                            if (ileDostepnych < ilePotrzebnych) {
                                ileDostepnych += procesy[i].getIleRamek();
                                procesy[i].setIleRamek(0);
                            } else {
                                procesy[i].dodajRamke();
                                ileDostepnych--;
                            }
                        }
                    }
                    licznik--;
                }
                licznik = 0;
                for (int i = 0; i < tablicaZbiorowOstatnichOdwolan.length; ++i)
                    if (procesy[i].getIleRamek() > 0 || procesy[i].czyKoniec())
                        tablicaZbiorowOstatnichOdwolan[i].clear();
            }

        }
        for (int i = 0; i < procesy.length; ++i) {
            licznikBledowStron += procesy[i].getLicznikBledowStron();
        }
        return licznikBledowStron;
    }
}
