package com.ecsail;

import com.ecsail.sql.select.SqlEmail;
import com.ecsail.sql.select.SqlPerson;
import com.ecsail.structures.MembershipListDTO;
import com.ecsail.structures.PersonDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Transport;


public class rosterContextMenu extends ContextMenu {
    MenuItem emailClient = new MenuItem("Open in Email Client");
    MenuItem registration = new MenuItem("Send Renewal Link");
    private EmailLinkBuilder linkCreator = new EmailLinkBuilder();
    private String selectedYear;
    private PersonDTO person;
    private String email;


    public rosterContextMenu(MembershipListDTO m, String selectedYear) {
        this.person = SqlPerson.getPersonByPid(m.getPid());
        this.email = SqlEmail.getEmail(this.person);
        this.selectedYear = selectedYear;
        getItems().addAll(emailClient,registration);
//        String link = linkCreator.createLink();

        registration.setOnAction((ActionEvent e) -> {

            String linkData = encodeURI(linkCreator.createLinkData(m, selectedYear));
//            String body = link + linkData;
            // email ID of Recipient.
            String recipient = email;

            String host = "smtp.gmail.com";

            // Getting system properties
            Properties properties = new Properties();

            // Setting up mail server

            properties.setProperty("mail.smtp.host", host);
            properties.put("mail.smtp.port", "587");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true"); // TLS
            // creating session object to get properties
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("mail.smtp.username"),
                            properties.getProperty("mail.smtp.password"));
                }
            });

            // Used to debug SMTP issues
            session.setDebug(true);

            try
            {
                // MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From Field: adding senders email to from field.
//                message.setFrom(new InternetAddress("perry.lee.cameron@gmail.com"));
                message.setFrom(new InternetAddress("membership@ecsail.org"));

                // Set To Field: adding recipient's email to from field.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

                // Set Subject: subject of the email
                message.setSubject("2022 Membership Dues");

                // set body of the email.
                message.setText("https://eaglecreeksailing.com/url/member" + String.valueOf(m.getMembershipId()));

                // Send email.
                Transport.send(message);
                System.out.println("Mail successfully sent");
            }
            catch (MessagingException mex)
            {
                mex.printStackTrace();
            }

        });

        emailClient.setOnAction((ActionEvent e) -> {
            System.out.println("Selected Year=" + selectedYear);
            String URL = linkCreator.createLinkData(m, selectedYear);
            new HTTPRequestBuilder(URL,"member" + String.valueOf(m.getMembershipId()));
            // this will be the short link
            String body = "https://eaglecreeksailing.com/url/member" + String.valueOf(m.getMembershipId());
            // encodes so I can have spaces
            String subject = encodeURI("2022 Membership Renewal");
            System.out.println(body);

            // link created with urlBuilder, why the fuck does it keep giving him 15 work credits?
            System.out.println(URL);

            Desktop desktop = Desktop.getDesktop();

            String message = "mailto:" + email + "?subject="+subject+"&body=" + body;
            URI uri = URI.create(message);

            try {
                desktop.mail(uri);
            } catch (IOException f) {
                f.printStackTrace();
            }
        });
    }

    public static String encodeURI(String s) {
        String result;
        try {
            result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        } // This exception should never occur.
        catch (Exception e) {
            result = s;
        }

        return result;
    }

}
