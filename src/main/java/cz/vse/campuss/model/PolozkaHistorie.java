package cz.vse.campuss.model;

/**
 * Třída PolozkaHistorie představuje jednu položku historie uložení v systému.
 * Obsahuje informace o studentovi, šatně, umístění, stavu uložení a čase změny stavu.
 */
public class PolozkaHistorie {
    private final int id;
    private final String jmenoStudenta;
    private final String prijmeniStudenta;
    private final String isicStudenta;
    private final String satnaNazev;
    private final TypUmisteni umisteniTyp;
    private final int umisteniCislo;
    private final StavUlozeni stav;
    private final String casZmenyStavu;
    private final int satnarkaID;
    private final int umisteniID;

    /**
     * Konstruktor třídy PolozkaHistorie
     * @param id ID záznamu
     * @param jmenoStudenta Jméno studenta
     * @param prijmeniStudenta Příjmení studenta
     * @param isicStudenta ISIC studenta
     * @param satnaNazev Název šatny
     * @param umisteniTyp Typ umístění
     * @param umisteniCislo Číslo umístění
     * @param stav Stav uložení
     * @param casZmenyStavu Čas změny stavu
     * @param satnarkaID ID satnárky
     * @param umisteniID ID umístění
     */
    public PolozkaHistorie(int id, String jmenoStudenta, String prijmeniStudenta, String isicStudenta, String satnaNazev, TypUmisteni umisteniTyp, int umisteniCislo, StavUlozeni stav, String casZmenyStavu, int satnarkaID, int umisteniID) {
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
        this.umisteniID = umisteniID;
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
