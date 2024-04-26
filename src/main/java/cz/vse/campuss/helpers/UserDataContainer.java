package cz.vse.campuss.helpers;

import cz.vse.campuss.model.Student;

/**
 * Třída pro uchování dat o stavu checkboxů a studenta
 */
public class UserDataContainer {
    // Stav checkboxů a studenta
    private boolean vesakChecked;
    private boolean podlahaChecked;
    private Student student;

    /**
     * Konstruktor třídy
     * @param vesakChecked Stav checkboxu vesak
     * @param podlahaChecked Stav checkboxu podlaha
     * @param student Student
     */
    public UserDataContainer(boolean vesakChecked, boolean podlahaChecked, Student student) {
        this.vesakChecked = vesakChecked;
        this.podlahaChecked = podlahaChecked;
        this.student = student;
    }

    public boolean isVesakChecked() {
        return vesakChecked;
    }

    public boolean isPodlahaChecked() {
        return podlahaChecked;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setVesakChecked(boolean vesakChecked) {
        this.vesakChecked = vesakChecked;
    }

    public void setPodlahaChecked(boolean podlahaChecked) {
        this.podlahaChecked = podlahaChecked;
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
