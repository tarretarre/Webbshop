package Webbshop;

import Webbshop.Entiteter.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    public List<Kund> getKund() throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try(Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Kund")
        ) {

            List<Kund> kunder = new ArrayList<>();

            while(rs.next()){
                Kund temp = new Kund();
                int id = rs.getInt("id");
                temp.setId(id);
                String förnamn = rs.getString("förnamn");
                temp.setFörnamn(förnamn);
                String efternamn = rs.getString("efternamn");
                temp.setEfternamn(efternamn);
                String ort = rs.getString("ort");
                temp.setOrt(ort);
                String epost = rs.getString("epost");
                temp.setEpost(epost);
                String lösenord = rs.getString("lösenord");
                temp.setLösenord(lösenord);
                kunder.add(temp);
            }
            return kunder;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Produkt> getProdukt() throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try(Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select p.*, m.namn as märke " +
                            "from Produkt p " +
                            "join Märke m on p.märkeid = m.id")
        ) {

            List<Produkt> produkter = new ArrayList<>(); 

            while(rs.next()){
                Produkt temp = new Produkt();
                int id = rs.getInt("id"); 
                temp.setId(id);
                int märkeid = rs.getInt("märkeid");
                Märke märke = new Märke(märkeid, rs.getString("märke"));
                temp.setMärke(märke);
                String namn = rs.getString("namn");
                temp.setNamn(namn);
                String färg = rs.getString("färg");
                temp.setFärg(färg);
                int storlek = rs.getInt("storlek");
                temp.setStorlek(storlek);
                int lagersaldo = rs.getInt("lagersaldo");
                temp.setLagersaldo(lagersaldo);
                produkter.add(temp);
            }
            return produkter;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Märke> getMärke() throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try(Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Märke")
        ) {

            List<Märke> märken = new ArrayList<>(); 

            while(rs.next()){
                Märke temp = new Märke();
                int id = rs.getInt("id"); 
                temp.setId(id);
                String namn = rs.getString("namn");
                temp.setNamn(namn);
                märken.add(temp);
            }
            return märken;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Kategori> getKategori() throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try(Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Kategori")
        ) {

            List<Kategori> kategorier = new ArrayList<>(); 

            while(rs.next()){
                Kategori temp = new Kategori();
                int id = rs.getInt("id"); 
                temp.setId(id);
                String namn = rs.getString("namn");
                temp.setNamn(namn);
                kategorier.add(temp);
            }
            return kategorier;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Produkt> getProduktIKategori(String kategoriNamn) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try(Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            PreparedStatement pstmt = con.prepareStatement(
                    "select p.*, m.namn as märke from Produkt p " +
                            "join ProduktKategori pk on p.id = pk.produktid " +
                            "join Kategori k on pk.kategoriid = k.id " +
                            "join Märke m on p.märkeid = m.id " +
                            "where k.namn = ?"
            )
        ) {
            pstmt.setString(1, kategoriNamn);
            ResultSet rs = pstmt.executeQuery();

            List<Produkt> produktIKategori = new ArrayList<>();

            while(rs.next()){
                Produkt temp = new Produkt();
                temp.setId(rs.getInt("id"));
                temp.setMärke(new Märke(rs.getInt("märkeid"), rs.getString("märke")));
                temp.setNamn(rs.getString("namn"));
                temp.setFärg(rs.getString("färg"));
                temp.setStorlek(rs.getInt("storlek"));
                temp.setLagersaldo(rs.getInt("lagersaldo"));
                produktIKategori.add(temp);
            }
            return produktIKategori;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Produkt> getProduktIMärke(String märkeNamn) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try(Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            PreparedStatement pstmt = con.prepareStatement(
                    "select p.*, m.namn as märke from Produkt p " +
                            "join Märke m on p.märkeid = m.id " +
                            "where m.namn = ?"
            )
        ) {
            pstmt.setString(1, märkeNamn);
            ResultSet rs = pstmt.executeQuery();

            List<Produkt> produktIMärke = new ArrayList<>();

            while(rs.next()){
                Produkt temp = new Produkt();
                temp.setId(rs.getInt("id"));
                temp.setMärke(new Märke(rs.getInt("märkeid"), rs.getString("märke")));
                temp.setNamn(rs.getString("namn"));
                temp.setFärg(rs.getString("färg"));
                temp.setStorlek(rs.getInt("storlek"));
                temp.setLagersaldo(rs.getInt("lagersaldo"));
                produktIMärke.add(temp);
            }
            return produktIMärke;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addToCart(int SP_KundId, int SP_BeställningId, int SP_ProduktId, int SP_Antal) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try(Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
            CallableStatement cstmt = con.prepareCall("call addToCart(?, ?, ?, ?, ?)");
        ) {
            cstmt.setInt(1, SP_KundId);
            cstmt.setInt(2, SP_BeställningId);
            cstmt.setInt(3, SP_ProduktId);
            cstmt.setInt(4, SP_Antal);
            cstmt.registerOutParameter(5, Types.INTEGER);
            cstmt.execute();

            int lyckadUppdatering = cstmt.getInt(5);
            return lyckadUppdatering == 1;

        } catch (SQLException e) {
            System.out.println("Error:");
        }
        return false;
    }

    public int getKundIdFromDatabase(String användarnamn, String lösenord) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement pstmt = con.prepareStatement(
                     "select Id " +
                             "from Kund " +
                             "where Epost = ? and Lösenord = ?")
        ) {
            pstmt.setString(1, användarnamn);
            pstmt.setString(2, lösenord);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int getBefintligBeställning(String användarnamn) throws SQLException, IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement pstmt = con.prepareStatement("select beställningid " +
                     "from ProduktBeställning pb " +
                     "join Beställning b on pb.beställningid = b.id " +
                     "join Produkt p on pb.produktid = p.id " +
                     "join Kund k on b.kundid = k.id " +
                     "where Epost = ?")
        ) {
            pstmt.setString(1, användarnamn);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("beställningid");
                } else {
                    return 0;
                }
            }
        }
    }

    public List<Produkt> getProduktIKundvagn(int beställningId) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream("C:\\Users\\Tarre\\IdeaProjects\\Webbshop\\src\\Settings.properties"));

        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement pstmt = con.prepareStatement(
                     "select pb.*, p.*, m.namn as märke from ProduktBeställning pb " +
                             "join Produkt p on pb.produktid = p.id " +
                             "join Märke m on p.märkeid = m.id " +
                             "where pb.beställningid = ?")
        ) {
            pstmt.setInt(1, beställningId);
            ResultSet rs = pstmt.executeQuery();

            List<Produkt> produktIKundvagn = new ArrayList<>();

            while (rs.next()) {
                Produkt temp = new Produkt();
                int id = rs.getInt("id");
                temp.setId(id);
                int märkeid = rs.getInt("märkeid");
                Märke märke = new Märke(märkeid, rs.getString("märke"));
                temp.setMärke(märke);
                String namn = rs.getString("namn");
                temp.setNamn(namn);
                String färg = rs.getString("färg");
                temp.setFärg(färg);
                int storlek = rs.getInt("storlek");
                temp.setStorlek(storlek);
                int antal = rs.getInt("antal");
                temp.setAntalBeställda(antal);
                produktIKundvagn.add(temp);
            }
            return produktIKundvagn;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
