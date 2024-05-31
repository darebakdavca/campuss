package cz.vse.campuss.helpers;

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
    PRIHLASOVANI2 {
        @Override
        public String getFXMLResource() {
            return getFXMLPath("prihlasovani2.fxml");
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

    public abstract String getFXMLResource();

    private static String getFXMLPath(String resource) {
        return String.format("file:src/main/resources/cz/vse/campuss/main/fxml/%s", resource) ;
    }
}
