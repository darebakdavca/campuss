package cz.vse.campuss.model;

public class Student {
    private int id;
    private String name;
    private String surname;
    private String isic;

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
}
