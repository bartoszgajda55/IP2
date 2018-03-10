package group14.caledonian.ac.uk.quizapp.Helper;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by Allan on 10/03/2018.
 */

public class EmailHandler {
    public void sendMail(String recipient, String subject, String content){
        final String password = "1o44UX2tn4Ah4";
        final String email = "noreply.quizzy@gmail.com";
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        Session session = Session.getInstance(prop, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(email,password);
            }
        });

        try{
            String logo = "res/drawable/logo_fullsize.png";
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setContent("<h:body style=background-color:white;font-size:200%;color:black;padding:10px;>"+
                    "<div style=background-color:#f44336;color:white;font-size:300%;height:20%;width:100%;text-align:center;> <br />Quizzy<br /><br />" +
                    "</div> <br /> <img style=display:block;margin:auto; src="+logo+"><br /><br />" + content + "<br /><br /><br /><em>-this is an automated email</em>" +
                    "</body>", "text/html; charset=utf-8");
            Transport.send(message);

            System.out.print("sent");
        }
        catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }
}
