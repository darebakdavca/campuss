package cz.vse.campuss.model;

/**
 * Třída reprezentující šatnu
 */
public class Satna {
    private final int id;
    private final String satnaNazev;

    /**
     * Konstruktor třídy Satna
     * @param id ID šatny
     * @param satnaNazev Název šatny
     */
    public Satna(int id, String satnaNazev) {
        this.id = id;
        this.satnaNazev = satnaNazev;
    }

    public int getId() {
        return id;
    }
    public String getSatnaNazev() {
        return satnaNazev;
    }
}
