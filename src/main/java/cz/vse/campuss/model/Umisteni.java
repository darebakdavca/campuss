package cz.vse.campuss.model;

public class Umisteni {
    private final int id;
    private final int cislo;
    private final String typUmisteni;
    private final String student;
    private final int satna;

    public Umisteni(int id, int cislo, String typUmisteni, String student, int satna) {
        this.id = id;
        this.cislo = cislo;
        this.typUmisteni = typUmisteni;
        this.student = student;
        this.satna = satna;
    }

    public int getId() {
        return id;
    }

    public int getCislo() {
        return cislo;
    }

    public String getTypUmisteni() {
        return typUmisteni;
    }

    public String getStudent() {
        return student;
    }


    @Override
    public String toString() {
        return "Umisteni{" +
                "id=" + id +
                ", cislo='" + cislo + '\'' +
                ", typUmisteni='" + typUmisteni + '\'' +
                ", student='" + student + '\'' +
                ", satna='" + satna +
                '}';
    }
}
