package cz.vse.campuss.model;

/**
 * Třída Umisteni reprezentuje umístění studenta v satně.
 * Každé umístění má své id, číslo, typ umístění, jméno studenta a id satny.
 */
public class Umisteni {
    private final int id;
    private final int cislo;
    private final TypUmisteni typUmisteni;
    private final String student;
    private final int satna;

   /**
     * Konstruktor třídy Umisteni
     * @param id id umístění
     * @param cislo číslo umístění
     * @param typUmisteni typ umístění
     * @param student jméno studenta
     * @param satna id satny
     */
    public Umisteni(int id, int cislo, TypUmisteni typUmisteni, String student, int satna) {
        this.id = id;
        this.cislo = cislo;
        this.typUmisteni = typUmisteni;
        this.student = student;
        this.satna = satna;
    }

    /**
     * Metoda getId vrací id umístění
     * @return id umístění
     */
    public int getId() {
        return id;
    }

    /**
     * Metoda getCislo vrací číslo umístění
     * @return číslo umístění
     */
    public int getCislo() {
        return cislo;
    }

    /**
     * Metoda getTypUmisteni vrací typ umístění
     * @return typ umístění
     */
    public String getTypUmisteni() {
        return typUmisteni.getText();
    }

    /**
     * Metoda getStudent vrací isic studenta
     * @return student
     */
    public String getStudent() {
        return student;
    }


    /**
     * Stringová reprezentace umístění
     * @return
     */
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
