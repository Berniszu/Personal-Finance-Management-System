package DuomenuStrukturos;

import java.io.Serializable;
import java.util.Date;

public class Pajamos extends PiniguJudejimas implements Serializable{

    public Pajamos(double suma, Kategorija kategorija, String aprasymas, Date data, String nr,String tipas){
        super(suma,kategorija,aprasymas,data,nr,tipas);
    }

    
}
