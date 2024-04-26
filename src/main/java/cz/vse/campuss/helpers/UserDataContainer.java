package cz.vse.campuss.helpers;

/**
 * Třída pro uchování dat o stavu checkboxů a studenta
 */
public class UserDataContainer {
    // Stav checkboxů a studenta
    private CheckBoxState checkBoxState;
    private StudentState studentState;

    /**
     * Konstruktor třídy
     * @param checkBoxState Stav checkboxů
     * @param studentState Stav studenta
     */
    public UserDataContainer(CheckBoxState checkBoxState, StudentState studentState) {
        this.checkBoxState = checkBoxState;
        this.studentState = studentState;
    }

    /**
     * Metoda pro získání stavu checkboxů
     * @return Stav checkboxů
     */
    public CheckBoxState getCheckBoxState() {
        return checkBoxState;
    }

    /**
     * Metoda pro získání stavu studenta
     * @return Stav studenta
     */
    public StudentState getStudentState() {
        return studentState;
    }

    /**
     * Metoda pro nastavení stavu checkboxů
     * @param checkBoxState Stav checkboxů
     */
    public void setCheckBoxState(CheckBoxState checkBoxState) {
        this.checkBoxState = checkBoxState;
    }

    /**
     * Metoda pro nastavení stavu studenta
     * @param studentState Stav studenta
     */
    public void setStudentState(StudentState studentState) {
        this.studentState = studentState;
    }

    /**
     * Metoda pro získání textové reprezentace objektu
     * @return Textová reprezentace objektu
     */
    @Override
    public String toString() {
        return "UserDataContainer{" +
                "checkBoxState=" + checkBoxState +
                ", stageState=" + studentState +
                '}';
    }
}
