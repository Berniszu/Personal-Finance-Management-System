package DuomenuStrukturos;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class AsmeniniuFinansuValdymas implements Serializable {

    private ArrayList<Kategorija> kategorijos = new ArrayList();
    private ArrayList<PiniguJudejimas> judejimas = new ArrayList();
    Connection conn;
    Statement stmt;

    public boolean pridetiKategorija(String pav, String aprasas) {
        //Kategorija nauja = new Kategorija(pav, aprasas);
        try {
            this.prisijungtiPrieDB();
            String uzklausa = "INSERT INTO kategorija (pavadinimas,aprasymas)" + "VALUES ('" + pav + "', '" + aprasas + "')";
            stmt.execute(uzklausa);
            this.atsijungtiNuoDB();
            return true;

        } catch (Exception e) {
            return false;
        }

        /*if (!kategorijos.contains(nauja)) {
            kategorijos.add(nauja);
            return true;
        } else {
            System.out.println("Tokia kategorija jau egzistuoja");
            return false;
        }*/
    }

    public double balansoGavimas() {
        double balansas = 0;
        for (PiniguJudejimas j : judejimas) {
            balansas += j.gautiSuma();
        }
        return balansas;
    }

    public boolean pridetiIslaida(double suma, Date data, String aprasymas, String kat, String nr) {
        try {
            this.prisijungtiPrieDB();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dat = simpleDateFormat.format(data);
            String uzklausa = "INSERT INTO pinigujud (kategorija,pavadinimas,data,nr,suma,tipas)" + "VALUES ('" + kat + "', '" + aprasymas + "', '" + dat + "', '" + nr + "', '" + suma + "', '" + "Islaidos" + "')";
            //System.out.println("veikiaaa");
            stmt.execute(uzklausa);
            
            this.atsijungtiNuoDB();
            
            return true;
        } catch (Exception e) {
            return false;
            
        }
    }

    public boolean pridetiPajama(double suma, String kat, String aprasymas, Date data, String nr) {
        try {
            this.prisijungtiPrieDB();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dat = simpleDateFormat.format(data);
            String uzklausa = "INSERT INTO pinigujud (Kategorija,Pavadinimas,Data,Nr,Suma,Tipas)" + "VALUES ('" + kat + "', '" + aprasymas + "', '" + dat + "', '" + nr + "', '" + suma + "', '" + "Pajamos" + "')";
            stmt.execute(uzklausa);
            this.atsijungtiNuoDB();
            return true;

        } catch (Exception e) {
            return false;
        }

    }

    public boolean salintiKategorija(String katPav) {
        //this.prisijungtiPrieDB();
        try {
            this.prisijungtiPrieDB();
            String uzklausa = "DELETE FROM kategorija " + "WHERE pavadinimas ='" + katPav + "'";
            stmt.execute(uzklausa);
            this.atsijungtiNuoDB();
            return true;

        } catch (Exception e) {
            return false;
        }
        //this.atsijungtiNuoDB();
        //Kategorija sal = new Kategorija(katPav, null);
        //kategorijos.remove(sal);
    }
    public boolean salintiJudejima(String nr){
        try {
            this.prisijungtiPrieDB();
            String uzklausa = "DELETE FROM pinigujud " + "WHERE nr ='" + nr + "'";
            stmt.execute(uzklausa);
            this.atsijungtiNuoDB();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean keistiKategorijosPavadinima(String kokiaKat, String naujasPav) {
        try {
            this.prisijungtiPrieDB();
            String uzklausa = "UPDATE kategorija " + " SET pavadinimas = '" + naujasPav + "'" + "WHERE pavadinimas = '" + kokiaKat + "'";
            stmt.execute(uzklausa);
            this.atsijungtiNuoDB();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public Kategorija rastiKategorijaPagalPavadinima(String kategorija) {
        for (Kategorija k : kategorijos) {
            if (k.getPavadinimas().equals(kategorija)) {
                return k;
            }
        }
        return null;
    }

    public ArrayList<Kategorija> gautikategorijuSarasa() {
        try {
            prisijungtiPrieDB();
            kategorijos.clear();
            String sql = "SELECT * FROM kategorija";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String pav = rs.getString(1);
                String apr = rs.getString("aprasymas");
                kategorijos.add(new Kategorija(pav, apr));
            }
            rs.close();
            atsijungtiNuoDB();
        } catch (Exception e) {
            System.out.println("Klaida prisijungiant prie DB:");
            e.printStackTrace();
        }
        return kategorijos;
    }

    public ArrayList<PiniguJudejimas> gautiJudejimus() {
        try {
            prisijungtiPrieDB();
            judejimas.clear();
            String sql = "SELECT * FROM pinigujud";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String kat = rs.getString(1);
                String pav = rs.getString(2);
                Date data = rs.getDate(3);
                String nr = rs.getString(4);
                Double suma = rs.getDouble(5);
                String tipas = rs.getString(6);
                Kategorija k = rastiKategorijaPagalPavadinima(kat);
                judejimas.add(new PiniguJudejimas(suma, k,pav,data,nr,tipas));
                //System.out.println(kat+pav+data+nr+suma+tipas);
            }
            rs.close();
            atsijungtiNuoDB();
        } catch (Exception e) {
            System.out.println("Klaida prisijungiant prie DB:");
            e.printStackTrace();
        }
        return judejimas;
    }

    public void spausdintiKategorijosIrasus(String kat) {
        Kategorija k = rastiKategorijaPagalPavadinima(kat);
        if (k != null) {
            int nr = 1;
            for (PiniguJudejimas p : k.getJudejimas()) {
                System.out.println(nr + ". " + p);
                nr++;
            }
        } else {
            System.out.println("Tokia kategorija neegzistuoja");
        }
    }

    public void spausdintiVisusIrasus() {
        int nr = 1;
        for (PiniguJudejimas p : this.judejimas) {
            System.out.println(nr + ". " + p);
            nr++;
        }
    }

    public void surikiuotiPajamas() {
        Collections.sort(judejimas);
    }

    public void surikiuotiPajamas(String kat) {
        Kategorija k = rastiKategorijaPagalPavadinima(kat);
        if (k != null) {
            Collections.sort(k.getJudejimas());
        } else {
            System.out.println("Tokia kategorija neegzistuoja");
        }
    }

    public void saugotiPajamas(String kat) {
        Kategorija k = rastiKategorijaPagalPavadinima(kat);
        if (k != null) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("pajamos.txt"));
                out.writeObject(k);
                out.close();

            } catch (Exception e) {

            }
        } else {
            System.out.println("Tokia kategorija neegzistuoja");
        }
    }

    public void uzkrautiPajamas() {
        try {
            ObjectInputStream out = new ObjectInputStream(new FileInputStream("pajamos.txt"));
            Kategorija kat = (Kategorija) out.readObject();
            System.out.println(kat);
            if (!kategorijos.contains(kat)) {
                kategorijos.add(kat);
                System.out.println("Uzkrauta");
            }
            out.close();
        } catch (Exception e) {
            System.out.println("Nenuskaityti duomenis");
        }
    }

    public void saugotiIslaidas(String kat) {
        Kategorija k = rastiKategorijaPagalPavadinima(kat);
        if (k != null) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("islaidos.txt"));
                out.writeObject(k);
                out.close();
                System.out.println("Issaugota");
            } catch (Exception e) {

            }
        } else {
            System.out.println("Tokia kategorija neegzistuoja");
        }
    }

    public void uzkrautiIslaidas() {
        try {
            ObjectInputStream out = new ObjectInputStream(new FileInputStream("islaidos.txt"));
            Kategorija kat = (Kategorija) out.readObject();
            if (!kategorijos.contains(kat)) {
                kategorijos.add(kat);
                System.out.println("Loading...");
            }
            out.close();
        } catch (Exception e) {
            System.out.println("Nenuskaityti duomenis");
        }
    }

    public void prisijungtiPrieDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String DB_URL = "jdbc:mysql://localhost/duombaze";
            String USER = "root";
            String PASS = "";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            //System.out.println("Prisijungta prie DB:");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void atsijungtiNuoDB() {
        if (stmt != null && conn != null) {
            try {
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
