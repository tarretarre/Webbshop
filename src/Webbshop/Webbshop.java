package Webbshop;

import Webbshop.Entiteter.*;
import Webbshop.Metoder.Användare;
import Webbshop.Entitetskopplingar.ProduktBeställning;
import Webbshop.Metoder.WebbshopMetoder;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Webbshop {
    private Repository rep = new Repository();
    private Användare a = new Användare();
    private WebbshopMetoder wm = new WebbshopMetoder();
    private Kund kund = new Kund();
    private ProduktBeställning pb = new ProduktBeställning();

    public Webbshop() throws IOException, SQLException {
        List<Kund> kundLista = rep.getKund();
        List<Produkt> produktLista = rep.getProdukt();

        List<Användare> användareList = a.användareFrånKundList(kundLista);
        användareList.forEach(al -> System.out.println(al.getAnvändarnamn()+" "+al.getLösenord()));
        System.out.println("------------------------");

        Scanner sc = new Scanner(System.in);
        System.out.println("Ange användarnamn (Epost)");
        String användarnamn = sc.nextLine();
        System.out.println("Ange lösenord");
        String lösenord = sc.nextLine();

        boolean inloggad = användareList.stream().anyMatch(anv -> {
            if (anv.matchar(användarnamn,lösenord)) {
                try {
                    int kundIdFromDatabase = rep.getKundIdFromDatabase(användarnamn, lösenord);
                    kund.setId(kundIdFromDatabase);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return true;
            }
            return false;
        });

        int bestId = 0;
        String fortsätt;

        if (inloggad) {
            System.out.println("Välkommen in!");
            wm.visaMeny();

            bestId = rep.getBefintligBeställning(användarnamn);
            pb.setBeställningid(bestId);

            String anvInput = sc.nextLine().toLowerCase();
            wm.hanteraAnvInput(anvInput, produktLista, bestId, kund.getId());

            do {
                System.out.println();
                System.out.println("Vill du lägga till fler skor i kundvagnen?");
                fortsätt = sc.nextLine().toLowerCase();
                if (fortsätt.equals("ja")) {
                    wm.visaMeny();
                    anvInput = sc.nextLine().toLowerCase();
                    wm.hanteraAnvInput(anvInput, produktLista, bestId, kund.getId());
                }
            } while (fortsätt.equals("ja"));
            System.out.println("Tack och på återseende!");
        } else {
            System.out.println("Fel användarnamn eller lösenord.");
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        Webbshop ws = new Webbshop();
    }
}
