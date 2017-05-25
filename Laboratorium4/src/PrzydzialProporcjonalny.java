
public class PrzydzialProporcjonalny extends PrzydzialRamek {

    public PrzydzialProporcjonalny(int[][] procesy, int iloscRamek) {
        super(procesy, iloscRamek);
    }

    @Override
    public int wykonaj() {
        int lacznaDlugoscProcesow = 0;
        for (AlgorytmLRU aProcesy : procesy)
            lacznaDlugoscProcesow += aProcesy.getTablicaOdwolanDoStron().length;
        for (AlgorytmLRU aProcesy : procesy) {
            int ramekNaProces = iloscRamek * aProcesy.getTablicaOdwolanDoStron().length / lacznaDlugoscProcesow;
            aProcesy.setIleRamek(ramekNaProces);
        }
        for (AlgorytmLRU aProcesy : procesy) {
            aProcesy.wykonajWszystkie();
            licznikBledowStron += aProcesy.getLicznikBledowStron();
        }
        return licznikBledowStron;
    }
}
