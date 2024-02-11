package Webbshop.Entiteter;

public class Produkt {

    protected int id;
    protected Märke märke;
    protected String namn;
    protected String färg;
    protected int storlek;
    protected int lagersaldo;
    protected int pris;
    protected int antalBeställda;

    public Produkt(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Märke getMärke() {
        return märke;
    }

    public void setMärke(Märke märke) {
        this.märke = märke;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getFärg() {
        return färg;
    }

    public void setFärg(String färg) {
        this.färg = färg;
    }

    public int getStorlek() {
        return storlek;
    }

    public void setStorlek(int storlek) {
        this.storlek = storlek;
    }

    public int getLagersaldo() {
        return lagersaldo;
    }

    public void setLagersaldo(int lagersaldo) {
        this.lagersaldo = lagersaldo;
    }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }

    public int getAntalBeställda() {
        return antalBeställda;
    }

    public void setAntalBeställda(int antalBeställda) {
        this.antalBeställda = antalBeställda;
    }
}
