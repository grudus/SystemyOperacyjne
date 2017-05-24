
public class PrzydzialProporcjonalny extends PrzydzialRamek {

    public PrzydzialProporcjonalny(int[][] procesy, int iloscRamek) {
        super(procesy, iloscRamek);
    }

    @Override
    public int wykonaj() {
        int lacznaDlugoscProcesow = 0;
        for (int i = 0; i < procesy.length; ++i)
            lacznaDlugoscProcesow += procesy[i].getTablicaOdwolanDoStron().length;
        for (int i = 0; i < procesy.length; ++i) {
            int ramekNaProces = iloscRamek * procesy[i].getTablicaOdwolanDoStron().length / lacznaDlugoscProcesow;
            procesy[i].setIleRamek(ramekNaProces);
        }
        for (int i = 0; i < procesy.length; ++i) {
            procesy[i].wykonajWszystkie();
            licznikBledowStron += procesy[i].getLicznikBledowStron();
        }
        return licznikBledowStron;
    }
}
