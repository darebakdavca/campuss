package cz.vse.campuss.helpers;


/**
 * Třída pro uchování vybrané šatny
 */
public class SatnaSelection {
    private static SatnaSelection instance;
    private String selectedSatna;

    private SatnaSelection() {}

    public static SatnaSelection getInstance() {
        if (instance == null) {
            instance = new SatnaSelection();
        }
        return instance;
    }

    public String getSelectedSatna() {
        return selectedSatna;
    }

    public void setSelectedSatna(String selectedSatna) {
        this.selectedSatna = selectedSatna;
    }
}
