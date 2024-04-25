package cz.vse.campuss.main;

import cz.vse.campuss.model.Student;

/**
 * Stav studenta
 *
 */
public class StudentState {
    // Student
    public Student student;

    /**
     * Konstruktor třídy
     * @param student Student
     */
    public StudentState(Student student) {
        this.student = student;
    }

    /**
     * Metoda pro získání textové reprezentace studenta
     * @return Textová reprezentace studenta
     */
    @Override
    public String toString() {
        return "StudentState{" +
                "student=" + student +
                '}';
    }
}
