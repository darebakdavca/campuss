package cz.vse.campuss.model;

public class Student {
    private final int id;
    private final String name;
    private final String surname;
    private final String isic;

    public Student(int id, String name, String surname, String isic) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.isic = isic;
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
