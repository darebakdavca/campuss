package cz.vse.campuss.helpers;

/**
 * Enum FXMLView obsahuje všechny cesty k FXML souborům
 * Umožňuje jednoduše získat cestu k FXML souboru a případně umožňuje jednodušše implementovat nové cesty
 */
public enum FXMLView {
    HOME {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("home.fxml");
        }
    },
    PRIHLASOVANI {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("prihlasovani.fxml");
        }
    },
    PRIHLASOVANISTUDENT {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("prihlasovaniStudent.fxml");
        }
    },
    HISTORIE {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("historie.fxml");
        }
    },
    POTVRZENI {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("potvrzeni.fxml");
        }
    },
    STUDENT {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("student.fxml");
        }
    },
    USCHOVAT1 {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("uschovat1.fxml");
        }
    },
    USCHOVAT2 {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("uschovat2.fxml");
        }
    },

    VYZVEDNOUT1 {
        @Override
        public String getFXMLResource() { return getFXMLPath("vyzvednout1.fxml"); }
    },
    VYZVEDNOUT2 {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("vyzvednout2.fxml");
        }
    },
    VOLBA_SATNY {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("volbaSatny.fxml");
        }
    }
    ;

    /**
     * Abstraktní metoda která je implementována v každém enumu pro získání kompletní cesty pro každý FXML soubor
     * @return plná cesta k FXML souboru
     */
    public abstract String getFXMLResource();

    /**
     * Metoda pro získání přidání názvu fxml souboru k celé cestě kde se nachází
     * @param resource Název FXML souboru
     * @return Cesta k FXML souboru
     */
    private static String getFXMLPath(String resource) {
        return String.format("file:src/main/resources/cz/vse/campuss/main/fxml/%s", resource) ;
    }
}
