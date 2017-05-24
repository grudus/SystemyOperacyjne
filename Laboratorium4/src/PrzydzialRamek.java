
public abstract class PrzydzialRamek {
    protected AlgorytmLRU[] procesy;
    protected int iloscRamek;
    protected int licznikBledowStron;

    public PrzydzialRamek(int[][] procesy, int iloscRamek) {
        this.iloscRamek = iloscRamek;
        this.procesy = new AlgorytmLRU[procesy.length];
        for (int i = 0; i < procesy.length; ++i) {
            this.procesy[i] = new AlgorytmLRU(procesy[i]);
        }
    }

    public abstract int wykonaj();

    public int getWynikProcesu(int nrProcesu) {
        return procesy[nrProcesu].getLicznikBledowStron();
    }
}
