package DuomenuStrukturos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class PiniguJudejimas implements Serializable, Comparable<PiniguJudejimas>{
    ArrayList<Kategorija> kat = new ArrayList();
    double suma;
    Kategorija kategorija;
    String aprasymas;
    Date data;
    String nr;
    String tipas;
    
    public PiniguJudejimas(double suma, Kategorija kategorija, String aprasymas, Date data, String nr, String tipas){
        this.suma=suma;
        this.kategorija=kategorija;
        this.aprasymas=aprasymas;
        this.data=data;
        this.nr=nr;
        this.tipas=tipas;
    }
    public String gautiKategorijosPavadinima(){
        
        return kategorija.getPavadinimas();
    }
    public double gautiSuma(){
            return suma;
        }

    public double getSuma() {
        return suma;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public Date getData() {
        return data;
    }

    public String getNr() {
        return nr;
    }

    public String getTipas() {
        return tipas;
    }
    @Override
    public String toString() {
        return "Pajamos{" + "suma= " + suma + ", aprasymas= " + aprasymas + ", data= " + data + '}';
    }
    
    @Override
    public int compareTo(PiniguJudejimas o){
        if(this.suma==o.suma){
            return o.aprasymas.compareTo(aprasymas);
        }else{
            return Double.compare(suma,o. suma);
        }
    }
}
