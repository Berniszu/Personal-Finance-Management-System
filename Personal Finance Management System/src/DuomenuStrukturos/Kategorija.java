package DuomenuStrukturos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Kategorija implements Serializable{
    private ArrayList<PiniguJudejimas> judejimas = new ArrayList();
    private String pavadinimas, aprasymas;

    Kategorija(String pav, String aprasas) {
        pavadinimas = pav;
        this.aprasymas = aprasas;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }
    public ArrayList<PiniguJudejimas> getJudejimas() {
        return judejimas;
    }
    @Override
    public String toString() {
        return "Kategorija " + "pavadinimas = " + pavadinimas + ", aprasymas = " + aprasymas ;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.pavadinimas);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kategorija other = (Kategorija) obj;
        if (!Objects.equals(this.pavadinimas, other.pavadinimas)) {
            return false;
        }
        return true;
    }
    
}
