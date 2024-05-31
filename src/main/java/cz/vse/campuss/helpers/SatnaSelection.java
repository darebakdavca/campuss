package cz.vse.campuss.helpers;


/**
 * Třída pro uchování vybrané šatny
 */
public class SatnaSelection {
    private static SatnaSelection instance;
    private String selectedSatna;

    /**
     * Privátní konstruktor třídy
     */
    private SatnaSelection() {}

    /**
     * Metoda pro získání instance třídy
     * @return Instance třídy
     */
    public static SatnaSelection getInstance() {
        if (instance == null) {
            instance = new SatnaSelection();
        }
        return instance;
    }

    /**
     * Metoda pro získání vybrané šatny
     * @return Vybraná šatna
     */
    public String getSelectedSatna() {
        return selectedSatna;
    }

    /**
     * Metoda pro nastavení vybrané šatny
     * @param selectedSatna Vybraná šatna
     */
    public void setSelectedSatna(String selectedSatna) {
        this.selectedSatna = selectedSatna;
    }
}
