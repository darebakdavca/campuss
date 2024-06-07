package cz.vse.campuss.helpers;

import cz.vse.campuss.model.Student;

/**
 * Třída pro uchování dat o stavu checkboxů a studenta napříc aplikací
 * Jedná se o singelton návrhový vzor
 */
public class UserDataContainer {
    private static UserDataContainer instance;
    // Stav checkboxů a studenta
    private boolean vesakChecked;
    private boolean podlahaChecked;
    private Student student;


    /**
     * Privátní konstruktor třídy
     */
    private UserDataContainer() {}

    /**
     * Metoda pro získání instance singleton třídy
     * @return Instance třídy
     */
    public static UserDataContainer getInstance() {
        if (instance == null) {
            instance = new UserDataContainer();
        }
        return instance;
    }

    /**
     * Metoda pro získání stavu checkboxu pro vesak
     * @return Stav checkboxu pro vesak
     */
    public boolean isVesakChecked() {
        return vesakChecked;
    }

    /**
     * Metoda pro nastavení stavu checkboxu pro vesak
     * @param vesakChecked Stav checkboxu pro vesak
     */
    public void setVesakChecked(boolean vesakChecked) {
        this.vesakChecked = vesakChecked;
    }

    /**
     * Metoda pro získání stavu checkboxu pro podlahu
     * @return Stav checkboxu pro podlahu
     */
    public boolean isPodlahaChecked() {
        return podlahaChecked;
    }

    /**
     * Metoda pro nastavení stavu checkboxu pro podlahu
     * @param podlahaChecked Stav checkboxu pro podlahu
     */
    public void setPodlahaChecked(boolean podlahaChecked) {
        this.podlahaChecked = podlahaChecked;
    }

    /**
     * Metoda pro získání studenta
     * @return Student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Metoda pro nastavení studenta
     * @param student Student
     */
    public void setStudent(Student student) {
        this.student = student;
    }


    /**
     * Metoda pro získání textové reprezentace objektu
     * @return Textová reprezentace objektu
     */
    @Override
    public String toString() {
        return "UserDataContainer{" +
                "vesakChecked=" + vesakChecked +
                ", podlahaChecked=" + podlahaChecked +
                ", student=" + student +
                '}';
    }
}
