package cz.vse.campuss.helpers;

public enum FXMLView {
    HOME {
        @Override
        String getFXMLResource() {
            return getFXMLPath("home.fxml");
        }
    },
    PRIHLASOVANI {
        @Override
        String getFXMLResource() {
            return getFXMLPath("prihlasovani.fxml");
        }
    },
    PRIHLASOVANI2 {
        @Override
        String getFXMLResource() {
            return getFXMLPath("prihlasovani2.fxml");
        }
    },
    HISTORIE {
        @Override
        String getFXMLResource() {
            return getFXMLPath("historie.fxml");
        }
    },
    POTVRZENI {
        @Override
        String getFXMLResource() {
            return getFXMLPath("potvrzeni.fxml");
        }
    },
    STUDENT {
        @Override
        String getFXMLResource() {
            return getFXMLPath("student.fxml");
        }
    },
    USCHOVAT1 {
        @Override
        String getFXMLResource() {
            return getFXMLPath("uschovat1.fxml");
        }
    },
    USCHOVAT2 {
        @Override
        String getFXMLResource() {
            return getFXMLPath("uschovat2.fxml");
        }
    },
    VYZVEDNOUT {
        @Override
        String getFXMLResource() {
            return getFXMLPath("vyzvednout.fxml");
        }
    },
    VOLBA_SATNY {
        @Override
        String getFXMLResource() {
            return getFXMLPath("volbaSatny.fxml");
        }
    }
    ;

    abstract String getFXMLResource();

    private static String getFXMLPath(String resource) {
        return String.format("file:src/main/resources/cz/vse/campuss/main/fxml/%s", resource) ;
    }
}
