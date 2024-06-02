package cz.vse.campuss.model;

public class Satna {
    private final int id;
    private final String satnaNazev;

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
