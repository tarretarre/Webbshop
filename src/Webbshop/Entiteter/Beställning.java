package Webbshop.Entiteter;

import java.util.Date;

public class Beställning {

    protected int id;
    protected String datum;
    protected int kundid;

    public Beställning(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getKundid() {
        return kundid;
    }

    public void setKundid(int kundid) {
        this.kundid = kundid;
    }
}
