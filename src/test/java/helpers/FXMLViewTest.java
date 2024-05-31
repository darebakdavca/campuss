package helpers;

import cz.vse.campuss.helpers.FXMLView;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Testovací třída pro FXMLView
 */
public class FXMLViewTest {

    @Test
    public void testHomeFXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/home.fxml";
        assertEquals(expected, FXMLView.HOME.getFXMLResource());
    }

    @Test
    public void testPrihlasovaniFXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/prihlasovani.fxml";
        assertEquals(expected, FXMLView.PRIHLASOVANI.getFXMLResource());
    }

    @Test
    public void testPrihlasovani2FXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/prihlasovani2.fxml";
        assertEquals(expected, FXMLView.PRIHLASOVANI2.getFXMLResource());
    }

    @Test
    public void testHistorieFXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/historie.fxml";
        assertEquals(expected, FXMLView.HISTORIE.getFXMLResource());
    }

    @Test
    public void testPotvrzeniFXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/potvrzeni.fxml";
        assertEquals(expected, FXMLView.POTVRZENI.getFXMLResource());
    }

    @Test
    public void testStudentFXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/student.fxml";
        assertEquals(expected, FXMLView.STUDENT.getFXMLResource());
    }

    @Test
    public void testUschovat1FXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/uschovat1.fxml";
        assertEquals(expected, FXMLView.USCHOVAT1.getFXMLResource());
    }

    @Test
    public void testUschovat2FXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/uschovat2.fxml";
        assertEquals(expected, FXMLView.USCHOVAT2.getFXMLResource());
    }

    @Test
    public void testVyzvednout1FXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/vyzvednout1.fxml";
        assertEquals(expected, FXMLView.VYZVEDNOUT1.getFXMLResource());
    }

    @Test
    public void testVyzvednout2FXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/vyzvednout2.fxml";
        assertEquals(expected, FXMLView.VYZVEDNOUT2.getFXMLResource());
    }

    @Test
    public void testVolbaSatnyFXMLResource() {
        String expected = "file:src/main/resources/cz/vse/campuss/main/fxml/volbaSatny.fxml";
        assertEquals(expected, FXMLView.VOLBA_SATNY.getFXMLResource());
    }
}