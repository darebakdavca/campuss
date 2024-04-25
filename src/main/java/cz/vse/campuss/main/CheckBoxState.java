package cz.vse.campuss.main;
/**
 * Třída pro uchování stavu checkboxů
 */
public class CheckBoxState {
    // Stav checkboxů
    public boolean obleceniChecked;
    public boolean zavazadloChecked;

    /**
     * Konstruktor třídy
     * @param obleceniChecked Stav checkboxu oblečení
     * @param zavazadloChecked Stav checkboxu zavazadlo
     */
    public CheckBoxState(boolean obleceniChecked, boolean zavazadloChecked) {
        this.obleceniChecked = obleceniChecked;
        this.zavazadloChecked = zavazadloChecked;
    }
    /**
     * Metoda pro získání textové reprezentace checkboxů
     * @return String textová reprezentace checkboxů
     */
    @Override
    public String toString() {
        return "CheckBoxState{" +
                "obleceniChecked=" + obleceniChecked +
                ", zavazadloChecked=" + zavazadloChecked +
                '}';
    }
}
