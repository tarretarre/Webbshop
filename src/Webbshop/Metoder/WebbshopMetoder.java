package Webbshop.Metoder;

import Webbshop.Entiteter.*;
import Webbshop.Entitetskopplingar.ProduktBeställning;
import Webbshop.Repository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class WebbshopMetoder {
    protected Repository rep = new Repository();
    protected Kund kund = new Kund();
    protected Scanner sc = new Scanner(System.in);
    protected int antal = 0;

    public void visaMeny() {
        System.out.println("Välj kategori att filtrera på - ange siffra eller kategorinamn:");
        System.out.println("1. Kategori");
        System.out.println("2. Märke");
        System.out.println("3. Storlek");
        System.out.println("4. Färg");
        System.out.println("5. Allt");
        System.out.println("6. Kundvagn");
    }

    public void visaAllaKategorier(List<Kategori> skokategorier) {
        System.out.println("Välj typ av sko du önskar - ange siffra eller kategorinamn:");
        skokategorier.forEach(kategori -> System.out.println(kategori.getId() + ". " + kategori.getNamn()));
    }

    public List<Produkt> hanteraSkoKategorier(String skokategori) throws IOException {
        switch (skokategori) {
            case "1", "sneakers":
                return rep.getProduktIKategori("Sneakers");
            case "2", "stövlar":
                return rep.getProduktIKategori("Stövlar");
            case "3", "sandaler":
                return rep.getProduktIKategori("Sandaler");
            case "4", "löpning":
                return rep.getProduktIKategori("Löpning");
            case "5", "klättring":
                return rep.getProduktIKategori("Klättring");
            default:
                System.out.println("Inga produkter finns i vald kategori.");
                return null;
        }
    }

    public void visaTillgängligaSkor(List<Produkt> skor, String meddelande) {
        System.out.println(meddelande);

        Collections.sort(skor, (s1, s2) -> Integer.compare(s1.getId(), s2.getId()));

        for (Produkt sko : skor) {
            System.out.println(sko.getId() +
                    ". Namn: " + sko.getMärke().getNamn() + " " + sko.getNamn() +
                    ", Färg: " + sko.getFärg() +
                    ", Storlek: " + sko.getStorlek());
        }
    }

    public void visaAllaMärken(List<Märke> märken) {
        System.out.println("Tillgängliga märken:");
        märken.forEach(märke -> System.out.println(märke.getId() + ". " + märke.getNamn()));
    }

    public List<Produkt> hanteraSkoMärken(String skomärke) throws IOException {
        switch (skomärke) {
            case "1", "ecco":
                return rep.getProduktIMärke("Ecco");
            case "2", "adidas":
                return rep.getProduktIMärke("Adidas");
            case "3", "hunter":
                return rep.getProduktIMärke("Hunter");
            case "4", "ugg":
                return rep.getProduktIMärke("Ugg");
            case "5", "nike":
                return rep.getProduktIMärke("Nike");
            case "6", "birkenstock":
                return rep.getProduktIMärke("Birkenstock");
            case "7", "dr. Martens":
                return rep.getProduktIMärke("Dr. Martens");
            default:
                System.out.println("Inga produkter finns i valda märket.");
                return null;
        }
    }

    public void visaAllaStorlekar(List<Produkt> storlekar) {
        System.out.println("Tillgängliga storlekar:");
        Set<Integer> unikaStorlekar = storlekar.stream().map(Produkt::getStorlek).collect(Collectors.toSet());
        unikaStorlekar.forEach(storlek -> System.out.println(storlek));
    }

    public List<Produkt> visaTillgängligaSkorMedStorlek(List<Produkt> skor, int valdStorlek) {
        List<Produkt> skorMedStorlek = skor.stream()
                .filter(sko -> sko.getStorlek() == valdStorlek)
                .collect(Collectors.toList());

        visaTillgängligaSkor(skorMedStorlek, "Tillgängliga skor i med vald storlek:");

        return skorMedStorlek;
    }

    public void visaAllaFärger(List<Produkt> färger) {
        System.out.println("Tillgängliga färger:");
        Set<String> unikaFärger = färger.stream().map(Produkt::getFärg).collect(Collectors.toSet());
        unikaFärger.forEach(färg -> System.out.println(färg));
    }

    public List<Produkt> visaTillgängligaSkorMedFärg(List<Produkt> färger, String valdFärg) {
        List<Produkt> skorMedFärg = färger.stream()
                .filter(sko -> sko.getFärg().equalsIgnoreCase(valdFärg))
                .collect(Collectors.toList());

        visaTillgängligaSkor(skorMedFärg, "Tillgängliga skor med vald färg:");

        return skorMedFärg;
    }

    public void addToCartWM(int SP_KundId, int SP_BeställningId, int SP_ProduktId, int SP_Antal) throws IOException {
        try {
            boolean addToCartLyckad = rep.addToCart(SP_KundId, SP_BeställningId, SP_ProduktId, SP_Antal);
            if (addToCartLyckad) {
                System.out.println("Produkt tillagd i kundvagnen!");
            } else {
                throw new RuntimeException("Det finns inte tillräckligt i lager av denna produkt.");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void hanteraAnvInput(String anvInput, List<Produkt> produkter, int bestId, int kundId) throws IOException {
        List<Kategori> kategoriLista = rep.getKategori();
        List<Märke> märkeLista = rep.getMärke();
        List<Produkt> produktLista = rep.getProdukt();

        switch (anvInput) {
            case "1": case "kategori":
                visaAllaKategorier(kategoriLista);
                anvInput = sc.nextLine().toLowerCase();

                List<Produkt> produkterIKategori = hanteraSkoKategorier(anvInput);
                visaTillgängligaSkor(produkterIKategori, "Tillgängliga skor i valda kategorin:");
                System.out.println("Ange siffra för skon du vill beställa:");
                int skoInput = sc.nextInt();
                sc.nextLine();

                boolean skoFinnsIKategori = produkterIKategori.stream().anyMatch(sko -> sko.getId() == skoInput);

                if (skoFinnsIKategori) {
                    System.out.println("Ange antal:");
                    antal = sc.nextInt();
                    sc.nextLine();
                    addToCartWM(kund.getId(), bestId, skoInput, antal);
                    System.out.println("------------------------");
                    System.out.println("Detta finns just nu i din kundvagn:");
                    System.out.println();
                    List<Produkt> produktIKundvagn = rep.getProduktIKundvagn(bestId);

                    produktIKundvagn.forEach(sko -> System.out.println(sko.getId() +
                            ". Namn: " + sko.getMärke().getNamn() + " " + sko.getNamn() +
                            ", Färg: " + sko.getFärg() +
                            ", Storlek: " + sko.getStorlek() +
                            ", Antal: " + sko.getAntalBeställda()));
                } else {
                    System.out.println("Ogiltigt val.");
                }
                break;
            case "2": case "märke":
                visaAllaMärken(märkeLista);
                System.out.println("Välj typ av märke du önskar - ange siffra eller märkesnamn:");
                anvInput = sc.next().toLowerCase();

                List<Produkt> produkterIMärke = hanteraSkoMärken(anvInput);
                visaTillgängligaSkor(produkterIMärke, "Tillgängliga skor med valda märket:");
                System.out.println("Ange siffra för skon du vill beställa:");
                int valdSkoIMärke = sc.nextInt();
                sc.nextLine();

                boolean skoFinnsIMärke = produkterIMärke.stream().anyMatch(sko -> sko.getId() == valdSkoIMärke);


                if (skoFinnsIMärke) {
                    System.out.println("Ange antal:");
                    antal = sc.nextInt();
                    sc.nextLine();
                    addToCartWM(kund.getId(), bestId, valdSkoIMärke, antal);
                    System.out.println("------------------------");
                    System.out.println("Detta finns just nu i din kundvagn:");
                    System.out.println();
                    List<Produkt> produktIKundvagn = rep.getProduktIKundvagn(bestId);

                    produktIKundvagn.forEach(sko -> System.out.println(sko.getId() +
                            ". Namn: " + sko.getMärke().getNamn() + " " + sko.getNamn() +
                            ", Färg: " + sko.getFärg() +
                            ", Storlek: " + sko.getStorlek() +
                            ", Antal: " + sko.getAntalBeställda()));
                } else {
                    System.out.println("Ogiltigt val.");
                }
                break;
            case "3": case "storlek":
                visaAllaStorlekar(produktLista);
                System.out.println("Välj önskad storlek - ange siffra:");
                int valdSkoStorlek = sc.nextInt();
                sc.nextLine();

                List<Produkt> skorMedStorlek = visaTillgängligaSkorMedStorlek(produktLista, valdSkoStorlek);
                System.out.println("Ange siffra för skon du vill beställa:");
                int valdSkoIStorlek = sc.nextInt();
                sc.nextLine();

                boolean skoFinnsIStorlek = skorMedStorlek.stream().anyMatch(sko -> sko.getId() == valdSkoIStorlek);

                if (skoFinnsIStorlek) {
                    System.out.println("Ange antal:");
                    antal = sc.nextInt();
                    sc.nextLine();
                    addToCartWM(kund.getId(), bestId, valdSkoIStorlek, antal);
                    System.out.println("------------------------");
                    System.out.println("Detta finns just nu i din kundvagn:");
                    System.out.println();
                    List<Produkt> produktIKundvagn = rep.getProduktIKundvagn(bestId);

                    produktIKundvagn.forEach(sko -> System.out.println(sko.getId() +
                            ". Namn: " + sko.getMärke().getNamn() + " " + sko.getNamn() +
                            ", Färg: " + sko.getFärg() +
                            ", Storlek: " + sko.getStorlek() +
                            ", Antal: " + sko.getAntalBeställda()));
                } else {
                    System.out.println("Ogiltigt val.");
                }
                break;
            case "4": case "färg":
                visaAllaFärger(produktLista);
                System.out.println("Välj önskad färg - ange färgnamn:");
                anvInput = sc.nextLine().toLowerCase();

                List<Produkt> skorMedFärg = visaTillgängligaSkorMedFärg(produktLista, anvInput);
                System.out.println("Ange siffra för skon du vill beställa:");
                int valdSkoIFärg = sc.nextInt();
                sc.nextLine();

                boolean skoFinnsIFärg = skorMedFärg.stream().anyMatch(sko -> sko.getId() == valdSkoIFärg);

                if (skoFinnsIFärg) {
                    System.out.println("Ange antal:");
                    antal = sc.nextInt();
                    sc.nextLine();
                    addToCartWM(kund.getId(), bestId, valdSkoIFärg, antal);
                    System.out.println("------------------------");
                    System.out.println("Detta finns just nu i din kundvagn:");
                    System.out.println();
                    List<Produkt> produktIKundvagn = rep.getProduktIKundvagn(bestId);

                    produktIKundvagn.forEach(sko -> System.out.println(sko.getId() +
                            ". Namn: " + sko.getMärke().getNamn() + " " + sko.getNamn() +
                            ", Färg: " + sko.getFärg() +
                            ", Storlek: " + sko.getStorlek() +
                            ", Antal: " + sko.getAntalBeställda()));
                } else {
                    System.out.println("Ogiltigt val.");
                }
                break;
            case "5": case "allt":
                visaTillgängligaSkor(produktLista, "Tillgängliga skor:");

                System.out.println("Ange siffra för skon du vill beställa:");
                int valdSko = sc.nextInt();
                sc.nextLine();

                System.out.println("Ange antal:");
                antal = sc.nextInt();
                sc.nextLine();
                addToCartWM(kund.getId(), bestId, valdSko, antal);
                System.out.println("------------------------");
                System.out.println("Detta finns just nu i din kundvagn:");
                System.out.println();
                List<Produkt> produktIKundvagn = rep.getProduktIKundvagn(bestId);

                produktIKundvagn.forEach(sko -> System.out.println(sko.getId() +
                        ". Namn: " + sko.getMärke().getNamn() + " " + sko.getNamn() +
                        ", Färg: " + sko.getFärg() +
                        ", Storlek: " + sko.getStorlek() +
                        ", Antal: " + sko.getAntalBeställda()));
                break;
            case "6": case "Kundvagn":
                System.out.println("------------------------");
                System.out.println("Detta finns just nu i din kundvagn:");
                System.out.println();
                produktIKundvagn = rep.getProduktIKundvagn(bestId);

                produktIKundvagn.forEach(sko -> System.out.println(sko.getId() +
                        ". Namn: " + sko.getMärke().getNamn() + " " + sko.getNamn() +
                        ", Färg: " + sko.getFärg() +
                        ", Storlek: " + sko.getStorlek() +
                        ", Antal: " + sko.getAntalBeställda()));
                break;
            default:
                System.out.println("Ogiltigt val.");
                break;
        }
    }
}
