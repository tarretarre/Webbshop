package Webbshop.Entitetskopplingar;

public class ProduktKategori {

    protected int id;
    protected int produktid;
    protected int kategoriid;

    public ProduktKategori(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduktid() {
        return produktid;
    }

    public void setProduktid(int produktid) {
        this.produktid = produktid;
    }

    public int getKategoriid() {
        return kategoriid;
    }

    public void setKategoriid(int kategoriid) {
        this.kategoriid = kategoriid;
    }
}
