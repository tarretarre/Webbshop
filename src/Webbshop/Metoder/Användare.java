package Webbshop.Metoder;

import Webbshop.Entiteter.Kund;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Användare {
    protected  String användarnamn;
    protected String lösenord;

    public Användare(){}

    public Användare(String användarnamn, String lösenord) {
        this.användarnamn = användarnamn;
        this.lösenord = lösenord;
    }

    public List<Användare> användareFrånKundList(List<Kund> kundLista) {
        List<Användare> anvLista = kundLista.stream().map(kund -> new Användare(kund.getEpost(), kund.getLösenord()))
                .collect(Collectors.toList());

        return anvLista;
    }

    public String getAnvändarnamn() {
        return användarnamn;
    }

    public String getLösenord() {
        return lösenord;
    }

    public boolean matchar(String användarnamn, String lösenord) {
        return this.användarnamn.equals(användarnamn) && this.lösenord.equals(lösenord);
    }
}
