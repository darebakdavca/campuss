package cz.vse.campuss.helpers;

import cz.vse.campuss.model.PolozkaHistorie;
import cz.vse.campuss.model.TypUmisteni;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
     * @param useSSL Použití SSL
     * @param polozkaHistorie Záznam v historii
     */
    public static void sendEmail(String to, String subject, String htmlFilePath, boolean useSSL, PolozkaHistorie polozkaHistorie) {

        // nastavení základních hodnot pro případ, že není záznam v historii
        String cisloVesak = "Oblečení neuloženo.";
        String cisloPodlaha = "Zavazadlo neuloženo.";

        // pokud se jedná o věšák
        if (polozkaHistorie.getUmisteniTyp() == TypUmisteni.VESAK) {
            cisloVesak = String.valueOf(polozkaHistorie.getUmisteniCislo());
        }

        // pokud se jedná o podlahu
        if (polozkaHistorie.getUmisteniTyp() == TypUmisteni.PODLAHA){
            cisloPodlaha = String.valueOf(polozkaHistorie.getUmisteniCislo());
        }

        // nastavení názvu šatny
        String nazevSatny = polozkaHistorie.getSatnaNazev();


        // nastavení mapování údajů
        Map<String, String> replacements = Map.of(
                "cisloVesak", cisloVesak,
                "cisloPodlaha", cisloPodlaha,
                "nazevSatny", nazevSatny
        );

        // načtení html obsahu i s doplněnými údaji
        String htmlBody = HtmlTemplateReader.readHtmlTemplate(htmlFilePath, replacements);


        // Načtení autentizačních údajů z konfiguračního souboru
        Properties config = new Properties();
        try (InputStream input = new FileInputStream("config.properties")) {
            config.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }

        // přiřazení autentizačních údajů
        String from = config.getProperty("email.from");
        String password = config.getProperty("email.password");


        // SMTP host a port
        String host = "smtp.seznam.cz";
        int port = useSSL ? 465 : 587; // Use port 465 for SSL/TLS or 587 for STARTTL

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        if (useSSL) {
            // SSL/TLS configurace
            properties.put("mail.smtp.socketFactory.port", port);
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");
        } else {
            // STARTTLS configurace
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.ssl.trust", host);
        }

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
}
