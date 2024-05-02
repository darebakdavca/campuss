package cz.vse.campuss.helpers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Třída pro čtení HTML šablon
 */
public class HtmlTemplateReader {

    /**
     * Metoda pro načtení HTML šablony
     * @param filePath Cesta k souboru
     * @return Obsah souboru
     */
    public static String readHtmlTemplate(String filePath, Map<String, String> values) {
        try {
            String content = Files.readString(Paths.get(filePath));
            for (Map.Entry<String, String> entry : values.entrySet()) {
                content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}