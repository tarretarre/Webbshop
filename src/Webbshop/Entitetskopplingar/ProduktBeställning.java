package Webbshop.Entitetskopplingar;

public class ProduktBeställning {
    protected int id;
    protected int beställningid;
    protected int produktid;
    protected int antal;

    public ProduktBeställning(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBeställningid() {
        return beställningid;
    }

    public void setBeställningid(int beställningid) {
        this.beställningid = beställningid;
    }

    public int getProduktid() {
        return produktid;
    }

    public void setProduktid(int produktid) {
        this.produktid = produktid;
    }

    public int getAntal() {
        return antal;
    }

    public void setAntal(int antal) {
        this.antal = antal;
    }
}
