package cz.vse.campuss.helpers;

import cz.vse.campuss.model.PolozkaHistorie;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.mail.internet.*;
import javax.mail.*;
import java.util.Map;


/**
 * Třída pro odesílání emailů
 */
public class MailHelper {

    /**
     * Metoda pro odeslání emailu
     * @param to Adresa příjemce
     * @param subject Předmět emailu
     * @param htmlFilePath Cesta k HTML šabloně
     * @param polozkaHistorieList Záznamy v historii z jednoho potvrzení
     */
    public static void sendEmail(String to, String subject, String htmlFilePath, List<PolozkaHistorie> polozkaHistorieList) throws IOException {

        // získání kompletního HTML obsahu
        String htmlBody = getCompleteHtmlBody(htmlFilePath, polozkaHistorieList);

        // Načtení autentizačních údajů z konfiguračního souboru
        Properties config = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            config.load(input);
        }

        // přiřazení autentizačních údajů
        String from = config.getProperty("email.from");
        String password = config.getProperty("email.password");


        // SMTP host a port
        String host = "smtp.seznam.cz";
        int port = 465;
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);


        // SSL/TLS configurace
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");


        // Vytvoření session s novým autentikátorem
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // vytvoření nového emailu
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html; charset=utf-8");

            // poslání emailu
            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

//TODO: načítání informací o umístění z tabulky Umístění, nikoliv z PolozkaHistorie

    /**
     * Metoda pro nastavení mapování údajů do HTML šablony
     * @param htmlFilePath Cesta k HTML šabloně
     * @param polozkaHistorieList Záznam v historii
     * @return HTML obsah s doplněnými údaji
     */
    private static String getCompleteHtmlBody(String htmlFilePath, List<PolozkaHistorie> polozkaHistorieList) {
        // nastavení základních hodnot pro případ, že není záznam v historii
        String cisloVesak = "Oblečení neuloženo.";
        String cisloPodlaha = "Zavazadlo neuloženo.";
        String nazevSatny = null;

        // získání položek historie
        PolozkaHistorie polozkaHistorieVesak = polozkaHistorieList.getFirst();
        PolozkaHistorie polozkaHistoriePodlaha = polozkaHistorieList.get(1);

        // nastavení čísla věšáku
        if (polozkaHistorieVesak != null) {
            cisloVesak = "č." + polozkaHistorieVesak.getUmisteniCislo();
            nazevSatny = polozkaHistorieVesak.getSatnaNazev();
        }

        // nastavení čísla podlahy
        if (polozkaHistoriePodlaha != null) {
            cisloPodlaha = "č." + polozkaHistoriePodlaha.getUmisteniCislo();
            nazevSatny = polozkaHistoriePodlaha.getSatnaNazev();
        }



        // nastavení mapování údajů
        Map<String, String> replacements = Map.of(
                "cisloVesak", cisloVesak,
                "cisloPodlaha", cisloPodlaha,
                "nazevSatny", nazevSatny
        );

        // načtení html obsahu i s doplněnými údaji
        return HtmlTemplateReader.readHtmlTemplate(htmlFilePath, replacements);
    }

    /**
     * Metoda pro získání položek historie pro odeslání emailu
     * @param idVesak ID záznamu v historii pro uložení do vesaku
     * @param idPodlaha ID záznamu v historii pro uložení na podlahu
     * @return List záznamů v historii
     */
    public static List<PolozkaHistorie> getUschovaniInfo(int idVesak, int idPodlaha) {
        PolozkaHistorie polozkaHistorieVesak = DatabaseHelper.fetchActiveUschovani(idVesak);
        PolozkaHistorie polozkaHistoriePodlaha = DatabaseHelper.fetchActiveUschovani(idPodlaha);
        return Arrays.asList(polozkaHistorieVesak, polozkaHistoriePodlaha);
    }

}
