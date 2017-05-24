
public class PrzydzialRowny extends PrzydzialRamek {


    public PrzydzialRowny(int[][] procesy, int iloscRamek) {
        super(procesy, iloscRamek);
    }

    public int wykonaj() {
        int ramekNaProces = iloscRamek / procesy.length;
        for (AlgorytmLRU aTablicaProcesow : procesy) aTablicaProcesow.setIleRamek(ramekNaProces);
        for (int i = 0; i < procesy.length; ++i) {
            procesy[i].wykonajWszystkie();
            licznikBledowStron += procesy[i].getLicznikBledowStron();
        }
        return licznikBledowStron;
    }
}
