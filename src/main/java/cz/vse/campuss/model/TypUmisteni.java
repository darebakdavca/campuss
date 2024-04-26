package cz.vse.campuss.model;

/**
 * Typ umístění věci
 */
public enum TypUmisteni {
    VESAK("věšák"),
    PODLAHA("podlaha"),;

    /**
     * Textová reprezentace typu umístění
     */
    private final String text;

    /**
     * Konstruktor
     * @param text Textová reprezentace typu umístění
     */
    TypUmisteni(String text) {
        this.text = text;
    }

    /**
     * Vrátí textovou reprezentaci typu umístění
     * @return Textová reprezentace typu umístění
     */
    public String getText() {
        return this.text;
    }

    /**
     * Vrátí typ umístění na základě textové reprezentace
     * @param text Textová reprezentace typu umístění
     * @return Typ umístění
     */
    public static TypUmisteni fromString(String text) {
        for (TypUmisteni typUmisteni : TypUmisteni.values()) {
            if (typUmisteni.getText().equalsIgnoreCase(text)) {
                return typUmisteni;
            }
        }
        return null;
    }
}
