package helpers;

import cz.vse.campuss.helpers.HtmlTemplateReader;
import org.junit.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class HtmlTemplateReaderTest {

    private String testFilePath = Paths.get("src/test/resources/test.html").toString();

    @Before
    public void setUp() {
        // Create a sample HTML file with placeholders in the test resources directory
    }

    @Test
    public void testReadHtmlTemplate() {
        Map<String, String> values = new HashMap<>();
        values.put("name", "David");
        values.put("date", "25/05/2024");

        String expectedContent = "<html><head></head><body>Jméno: David, Datum: 25/05/2024</body></html>";
        String content = HtmlTemplateReader.readHtmlTemplate(testFilePath, values);

        assertEquals("The content should match expected with placeholders replaced", expectedContent, content);
    }

    @Test
    public void testFileNotFound() {
        String content = HtmlTemplateReader.readHtmlTemplate("nonexistent/path.html", new HashMap<>());
        assertNull("Content should be null if file is not found", content);
    }
}