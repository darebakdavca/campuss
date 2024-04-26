package cz.vse.campuss.model;

/**
 * Třída reprezentující šatnářku
 */
public class Satnarka {
    private final int id;
    private final String jmeno;
    private final String prijmeni;
    private final int id_satny;

    /**
     * Konstruktor třídy Satnarka
     * @param id ID šatnářky
     * @param jmeno Jméno šatnářky
     * @param prijmeni Příjmení šatnářky
     * @param id_satny ID šatny, ve které šatnářka pracuje
     */
    public Satnarka(int id, String jmeno, String prijmeni, int id_satny) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.id_satny = id_satny;
    }

    public int getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getId_satny() {
        return id_satny;
    }
}
