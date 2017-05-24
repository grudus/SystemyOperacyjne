
import java.util.LinkedList;

public class AlgorytmLRU {
    private int licznikBledowStron;
    private int[] tablicaOdwolanDoStron;
    private int ileRamek;
    private LinkedList<Integer> listaRamek;
    private int ileWykonano = 0;

    public AlgorytmLRU(int[] tablicaOdwolanDoStron) {
        this.tablicaOdwolanDoStron = tablicaOdwolanDoStron;
        this.licznikBledowStron = 0;
        this.ileRamek = 0;
        listaRamek = new LinkedList<>();
    }

    public int getOstatnioUzyty() {
        return tablicaOdwolanDoStron[ileWykonano - 1];
    }

    public int getIleRamek() {
        return ileRamek;
    }

    public void setIleRamek(int ileRamek) {
        this.ileRamek = ileRamek;
    }

    public int getLicznikBledowStron() {
        return licznikBledowStron;
    }

    public int[] getTablicaOdwolanDoStron() {
        return tablicaOdwolanDoStron;
    }

    public boolean czyKoniec() {
        return ileWykonano >= tablicaOdwolanDoStron.length;
    }

    public void dodajRamke() {
        ileRamek++;
    }

    public void usunRamke() {
        ileRamek--;
    }

    public boolean wykonajWszystkie() {
        boolean wynik = false;
        while (ileWykonano < tablicaOdwolanDoStron.length) wynik = wykonajJeden();
        return wynik;
    }

    public boolean wykonajJeden() {
        while (listaRamek.size() > ileRamek) listaRamek.removeFirst();
        if (ileWykonano < tablicaOdwolanDoStron.length) {
            if (ileRamek == 0) return false;
            if (!listaRamek.contains(tablicaOdwolanDoStron[ileWykonano])) {
                licznikBledowStron++;
                if (listaRamek.size() < ileRamek) listaRamek.addLast(tablicaOdwolanDoStron[ileWykonano]);
                else {
                    listaRamek.removeFirst();
                    listaRamek.addLast(tablicaOdwolanDoStron[ileWykonano]);
                }
            } else {
                int x = listaRamek.remove(listaRamek.indexOf(tablicaOdwolanDoStron[ileWykonano]));
                listaRamek.addLast(x);
            }
            ileWykonano++;
            return false;
        }
        return true;
    }
}
