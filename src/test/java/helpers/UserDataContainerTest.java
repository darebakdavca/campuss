package helpers;

import cz.vse.campuss.helpers.UserDataContainer;
import cz.vse.campuss.model.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Testovací třída pro UserDataContainer
 */
public class UserDataContainerTest {

    private UserDataContainer container;
    private Student student;

    @Before
    public void setUp() {
        student = new Student( 123456, "Jan", "Novák", "27466136AD", "jan@novak.cz");
        container = UserDataContainer.getInstance();
        container.setStudent(student);
        container.setVesakChecked(true);
        container.setPodlahaChecked(false);
    }

    @Test
    public void testConstructor() {
        assertTrue("Věšák by měl být zaškrnut", container.isVesakChecked());
        assertFalse("Podlaha by neměla být zaškrtnuta", container.isPodlahaChecked());
        assertSame("Student by měl odpovídat studentovi zadaným v konstruktoru", student, container.getStudent());
    }

    @Test
    public void testSetAndGetVesakChecked() {
        container.setVesakChecked(false);
        assertFalse("Věšák by neměl být zaškrtnutý", container.isVesakChecked());
    }

    @Test
    public void testSetAndGetPodlahaChecked() {
        container.setPodlahaChecked(true);
        assertTrue("Podlaha by měla být zaškrtnutá", container.isPodlahaChecked());
    }

    @Test
    public void testSetAndGetStudent() {
        Student newStudent = new Student( 1234567, "Jana", "Nováková", "2723116136TC", "jana@novakova.cz");
        container.setStudent(newStudent);
        assertSame("Student by měl být aktualizovaný z Jana Nováka na Janu Novákovou", newStudent, container.getStudent());
    }

    @Test
    public void testToString() {
        String expected = "UserDataContainer{vesakChecked=true, podlahaChecked=false, student=Student{id=123456, name='Jan', surname='Novák', isic='27466136AD'}}";
        assertEquals("toString by měl vrátit správnou reprezentaci objektu UserDataContainer", expected, container.toString());
    }
}