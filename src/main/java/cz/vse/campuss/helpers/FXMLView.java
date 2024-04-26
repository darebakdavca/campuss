package cz.vse.campuss.helpers;

public enum FXMLView {
    HOME {
        @Override
        String getFXMLResource() {
            return getFXMLPath("home.fxml");
        }

        @Override
        String getTitle() {
            return "Campuss - Domů";
        }
    },
    PRIHLASOVANI {
        @Override
        String getFXMLResource() {
            return getFXMLPath("prihlasovani.fxml");
        }

        @Override
        String getTitle() {
            return "Campuss - Přihlášení";
        }
    },
    HISTORIE {
        @Override
        String getFXMLResource() {
            return getFXMLPath("historie.fxml");
        }

        @Override
        String getTitle() {
            return "Campuss - Historie";
        }
    },
    POTVRZENI {
        @Override
        String getFXMLResource() {
            return getFXMLPath("potvrzeni.fxml");
        }

        @Override
        String getTitle() {
            return "Campuss - Potvrzení";
        }
    },
    STUDENT {
        @Override
        String getFXMLResource() {
            return getFXMLPath("student.fxml");
        }

        @Override
        String getTitle() {
            return "Campuss - Student";
        }
    },
    USCHOVAT1 {
        @Override
        String getFXMLResource() {
            return getFXMLPath("uschovat1.fxml");
        }

        @Override
        String getTitle() {
            return "Campuss - Uchování";
        }
    },
    USCHOVAT2 {
        @Override
        String getFXMLResource() {
            return getFXMLPath("uschovat2.fxml");
        }

        @Override
        String getTitle() {
            return "Campuss - Uchování";
        }
    },
    VYZVEDNOUT {
        @Override
        String getFXMLResource() {
            return getFXMLPath("vyzvednout.fxml");
        }

        @Override
        String getTitle() {
            return "Campuss - Vyzvednutí";
        }
    };

    abstract String getFXMLResource();
    abstract String getTitle();

    private static String getFXMLPath(String resource) {
        return String.format("file:src/main/resources/cz/vse/campuss/main/fxml/%s", resource) ;
    }
}
