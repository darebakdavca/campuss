package cz.vse.campuss.model;

/**
 * Stavy uložení oblečení/zavazadla
 */
public enum StavUlozeni {
    USCHOVANO("uschováno"),
    VYZVEDNUTO("vyzvednuto"),;

    private final String text;

    /**
     * Konstruktor
     *
     * @param text textová podoba
     */
    StavUlozeni(String text) {
        this.text = text;
    }

    /**
     * Getter pro textovou podobu
     *
     * @return textová podoba
     */
    public String getText() {
        return text;
    }

    /**
     * Vrátí stav uložení podle textové podoby
     *
     * @param text textová podoba
     * @return korespondující stav uložení
     */
    public static StavUlozeni fromString(String text) {
        for (StavUlozeni stavUlozeni : StavUlozeni.values()) {
            if (stavUlozeni.getText().equalsIgnoreCase(text)) {
                return stavUlozeni;
            }
        }
        return null;
    }
}
