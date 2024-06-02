package helpers;

import cz.vse.campuss.helpers.SatnaSelection;
import cz.vse.campuss.model.Satna;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testy pro třídu SatnaSelection
 */
public class SatnaSelectionTest {

    @Test
    public void testSingletonUniqueInstance() {
        SatnaSelection firstInstance = SatnaSelection.getInstance();
        SatnaSelection secondInstance = SatnaSelection.getInstance();
        assertSame("Obě instance by měly být stejné", firstInstance, secondInstance);
    }

    @Test
    public void testSingletonConsistency() {
        SatnaSelection instance = SatnaSelection.getInstance();
        Satna satna = new Satna(1, "Šatna 101");
        instance.setSelectedSatna(satna);
        assertEquals("Zvolená šatna by měla být 'Šatna 101'", satna, instance.getSelectedSatna());
    }

    @Test
    public void testSingletonThreadSafety() throws InterruptedException {
        SatnaSelection[] instances = new SatnaSelection[1];
        Thread thread1 = new Thread(() -> instances[0] = SatnaSelection.getInstance());
        Thread thread2 = new Thread(() -> instances[0] = SatnaSelection.getInstance());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertNotNull("Instance by něměla být null", instances[0]);
        assertSame("Instance získané z jiných vláken by měly být stejné", SatnaSelection.getInstance(), instances[0]);
    }
}