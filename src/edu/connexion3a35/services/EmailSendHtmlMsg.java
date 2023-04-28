package edu.connexion3a35.services;
import edu.connexion3a35.entities.Produit;
import edu.connexion3a35.entities.User;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class EmailSendHtmlMsg {

    public static String htmlVerifyAccount(Produit prod , String websiteTeam) {
        String html = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Verify your Account</title>\n"
                + "<style type=\"text/css\">body{margin:0;padding:0;background-color:#f7f7f7;font-family:Arial,sans-serif;font-size:14px;line-height:1.5;color:#333;}h1{margin-top:0;font-size:28px;font-weight:bold;color:#333;text-align:center;}p{margin-top:0;margin-bottom:1em;}a{color:#337ab7;text-decoration:none;font-weight:bold;}.container{max-width:600px;margin:0 auto;padding:20px;background-color:#fff;border:1px solid #ddd;box-shadow:0 2px 6px rgba(0,0,0,0.1);}.logo{margin-bottom:20px;text-align:center;}.logo img{max-width:200px;}.signature{margin-top:30px;text-align:right;}.signature p{margin:0;font-size:14px;color:#777;}</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div class=\"container\">\n"
                + "<div class=\"logo\">\n"
                + "<img src=\"https://pbs.twimg.com/media/FumhXnOWcAAnTJi?format=png&name=small\" alt=\"Logo\">\n"
                + "</div>\n"
                + "<h1>Prouit Commandée</h1>\n"
                + "<p>Dear " + "UserName" + ",</p>\n"
                + "<p>Your product " + prod.getNom_produit() + " has been chosen by a client. Don't hesitate to contact them to continue the selling procedures.</p>\n"
                + "<p>Please contact our office if this email is mistaken.</p>\n"
                + "<p>Thank you for choosing AidMe.</p>\n"
                + "<div class=\"signature\">\n"
                + "<p>The " + websiteTeam + " Team</p>\n"
                // + "<p><a href=\"%s\">Visit our website</a></p>\n"
                + "</div>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";


        return html;

    }




}

