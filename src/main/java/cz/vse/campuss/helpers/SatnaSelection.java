package cz.vse.campuss.helpers;


import cz.vse.campuss.model.Satna;

/**
 * Třída pro uchování vybrané šatny
 * Jedná se o singelton návrhový vzor
 */
public class SatnaSelection {
    private static SatnaSelection instance;
    private Satna selectedSatna;

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
    public Satna getSelectedSatna() {
        return selectedSatna;
    }

    /**
     * Metoda pro nastavení vybrané šatny
     * @param selectedSatna Vybraná šatna
     */
    public void setSelectedSatna(Satna selectedSatna) {
        this.selectedSatna = selectedSatna;
    }
}
