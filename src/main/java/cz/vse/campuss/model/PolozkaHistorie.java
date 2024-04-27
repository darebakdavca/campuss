package cz.vse.campuss.model;

public class PolozkaHistorie {
    private int id;
    private String jmenoStudenta;
    private String prijmeniStudenta;
    private String isicStudenta;
    private String satnaNazev;
    private TypUmisteni umisteniTyp;
    private int umisteniCislo;
    private StavUlozeni stav;
    private String casZmenyStavu;
    private int satnarkaID;

    public PolozkaHistorie(int id, String jmenoStudenta, String prijmeniStudenta, String isicStudenta, String satnaNazev, TypUmisteni umisteniTyp, int umisteniCislo, StavUlozeni stav, String casZmenyStavu, int satnarkaID) {
        this.id = id;
        this.jmenoStudenta = jmenoStudenta;
        this.prijmeniStudenta = prijmeniStudenta;
        this.isicStudenta = isicStudenta;
        this.satnaNazev = satnaNazev;
        this.umisteniTyp = umisteniTyp;
        this.umisteniCislo = umisteniCislo;
        this.stav = stav;
        this.casZmenyStavu = casZmenyStavu;
        this.satnarkaID = satnarkaID;
    }

    public int getId() {
        return id;
    }

    public String getJmenoStudenta() {
        return jmenoStudenta;
    }

    public String getPrijmeniStudenta() {
        return prijmeniStudenta;
    }

    public String getIsicStudenta() {
        return isicStudenta;
    }

    public String getSatnaNazev() {
        return satnaNazev;
    }

    public TypUmisteni getUmisteniTyp() {
        return umisteniTyp;
    }

    public int getUmisteniCislo() {
        return umisteniCislo;
    }

    public StavUlozeni getStav() {
        return stav;
    }

    public String getCasZmenyStavu() {
        return casZmenyStavu;
    }

    public int getSatnarkaID() {
        return satnarkaID;
    }
}
