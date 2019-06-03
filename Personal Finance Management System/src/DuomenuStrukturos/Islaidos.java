package DuomenuStrukturos;

import java.util.Date;

public class Islaidos extends PiniguJudejimas{
    private String cekioNr;
    
    public Islaidos(double suma, Kategorija kategorija, String aprasymas, Date data, String nr, String tipas){
        super(suma,kategorija,aprasymas,data,nr,tipas);
        cekioNr = nr;
    }
    public double gautiSuma(){
        return -suma;
    }

    @Override
    public String toString() {
        return "Islaidos{" + "suma= " + -suma + ", aprasymas= " + aprasymas + ", data= " + data + ", cekis= " + cekioNr + '}';
    }
    
}
