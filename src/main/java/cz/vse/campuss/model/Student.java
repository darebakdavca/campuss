package cz.vse.campuss.model;

/**
 * Třída reprezentující studenta
 */
public class Student {
    private final int id;
    private final String name;
    private final String surname;
    private final String isic;
    private final String email;

    /**
     * Konstruktor třídy Student
     * @param id ID studenta
     * @param name Jméno studenta
     * @param surname Příjmení studenta
     * @param isic ISIC studenta
     * @param email Email studenta
     */
    public Student(int id, String name, String surname, String isic, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.isic = isic;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getJmeno() {
        return name;
    }

    public String getPrijmeni() {
        return surname;
    }

    public String getIsic() {
        return isic;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", isic='" + isic + '\'' +
                '}';
    }
}
