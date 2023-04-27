package controller.gestion_rdv.services;

import entities.DummyUser;
import entities.gestion_rdv.CalandarDay;
import entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

public class EmailSendHtmlMsg {

    public static String[] htmlBodyNotifyCancelHtmlMsgReturn_plus_rawText(String patient, String doctor, String stringDate, String websiteTeam) {
        String html;
        html = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Appointment Canceled</title>\n"
                + "<style type=\"text/css\">body{margin:0;padding:0;background-color:#f7f7f7;font-family:Arial,sans-serif;font-size:14px;line-height:1.5;color:#333;}h1{margin-top:0;font-size:28px;font-weight:bold;color:#333;text-align:center;}p{margin-top:0;margin-bottom:1em;}a{color:#337ab7;text-decoration:none;font-weight:bold;}.container{max-width:600px;margin:0 auto;padding:20px;background-color:#fff;border:1px solid #ddd;box-shadow:0 2px 6px rgba(0,0,0,0.1);}.logo{margin-bottom:20px;text-align:center;}.logo img{max-width:200px;}.signature{margin-top:30px;text-align:right;}.signature p{margin:0;font-size:14px;color:#777;}</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div class=\"container\">\n"
                + "<div class=\"logo\">\n"
                + "<img src=\"https://www.apple.com/newsroom/images/values/corporate/standard/Apple_google-partner-on-covid-19-contact-tracing-technology_04102020_LP_hero.jpg.og.jpg\" alt=\"Logo\">\n"
                + "</div>\n"
                + "<h1>Appointment Canceled</h1>\n"
                + "<p>Dear " + patient + ",</p>\n"
                + "<p>We regret to inform you that your appointment with Dr. " + doctor + " on " + stringDate + "  has been canceled.</p>\n"
                + "<p>Please contact our office to reschedule your appointment.</p>\n"
                + "<p>Thank you for choosing our clinic.</p>\n"
                + "<div class=\"signature\">\n"
                + "<p>The " + websiteTeam + " Team</p>\n"
                + //                     "<p><a href=\"%s\">Visit our website</a></p>\n" +
                "</div>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";

        String text = "Appointment Canceled\n"
                + "Dear " + patient + ",\n"
                + "We regret to inform you that your appointment with Dr. " + doctor + " on " + stringDate + "  has been canceled.\n"
                + "Please contact our office to reschedule your appointment.\n"
                + "Thank you for choosing our clinic.\n"
                + "The " + websiteTeam + " Team\n";

        return new String[]{html, text};

    }

    public static String[] htmlBodyNotifyReminderHtmlMsgReturn_plus_rawText(String patient, String doctor, String stringDate, String websiteTeam) {
        String html = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Appointment reminder</title>\n"
                + "<style type=\"text/css\">body{margin:0;padding:0;background-color:#f7f7f7;font-family:Arial,sans-serif;font-size:14px;line-height:1.5;color:#333;}h1{margin-top:0;font-size:28px;font-weight:bold;color:#333;text-align:center;}p{margin-top:0;margin-bottom:1em;}a{color:#337ab7;text-decoration:none;font-weight:bold;}.container{max-width:600px;margin:0 auto;padding:20px;background-color:#fff;border:1px solid #ddd;box-shadow:0 2px 6px rgba(0,0,0,0.1);}.logo{margin-bottom:20px;text-align:center;}.logo img{max-width:200px;}.signature{margin-top:30px;text-align:right;}.signature p{margin:0;font-size:14px;color:#777;}</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<div class=\"container\">\n"
                + "<div class=\"logo\">\n"
                + "<img src=\"https://www.apple.com/newsroom/images/values/corporate/standard/Apple_google-partner-on-covid-19-contact-tracing-technology_04102020_LP_hero.jpg.og.jpg\" alt=\"Logo\">\n"
                + "</div>\n"
                + "<h1>Appointment reminder</h1>\n"
                + "<p>Dear " + patient + ",</p>\n"
                + "<p>reminder, you have an appointment with Dr. " + doctor + " on " + stringDate + "  .</p>\n"
                + "<p>Please contact our office to confirm your appointment .</p>\n"
                + "<p>Thank you for choosing our clinic.</p>\n"
                + "<div class=\"signature\">\n"
                + "<p>The " + websiteTeam + " Team</p>\n"
                + //                     "<p><a href=\"%s\">Visit our website</a></p>\n" +
                "</div>\n"
                + "</div>\n"
                + "</body>\n"
                + "</html>";

        String text = "Appointment reminder\n"
                + "Dear " + patient + ",\n"
                + "reminder, you have an appointment with Dr. " + doctor + " on " + stringDate + "  .\n"
                + "Please contact our office to confirm your appointment .\n"
                + "Thank you for choosing our clinic.\n"
                + "The " + websiteTeam + " Team\n";

        return new String[]{html, text};

    }

    
    
    
    
    //-- Single User email Delete notification
    public static void sendEmail_Notification_Delete(DummyPatientAppointmentDoctorSide patientandAppointmentData,
            CalandarDay selectedCalandar, DummyUser loggedUser) {

        try {

            if (patientandAppointmentData == null || selectedCalandar == null || loggedUser == null) {
                throw new NullPointerException(" either patientandAppointmentData == null || selectedCalandar==null || loggedUser ==null  is null or all are null");
            }

            // case patient already selected and exist
            String calandarDate = selectedCalandar.getDate().format(DateTimeFormatter.ISO_DATE);
            String doctorName = loggedUser.getLast_name();
            String patientName = patientandAppointmentData.getFirst_name();
            String patientEmail = patientandAppointmentData.getEmail();
            // doctor company link or static our Website link
            String companyLink = "<a href=\"facebook.com\"> Useless-Clininc-Corp </a>";

            // default email to use better make this inside an outer file for outside access
            MySmtpEmail smtp = new MySmtpEmail("2a21group5@gmail.com", "cotlmerzeszyfwuu");
            // setting up emails in this case one only thats why i passed in array this style
            smtp.set_emailsToSentTo(new String[]{patientEmail});

            // returns array [0]=>htmlVersion [1]=>textVersion
            String[] emailBody = EmailSendHtmlMsg.htmlBodyNotifyCancelHtmlMsgReturn_plus_rawText(patientName,
                    doctorName, calandarDate, companyLink);
            // no text version is provided only [0]==>html text version is set to "".
            smtp.setEmailSubject_andBody("Your Appointment has been canceled of Date: "+calandarDate, "", emailBody[0]);
            smtp.sendEmail();

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    //-- Single User email Delete notification
    public static void sendEmail_Notification_Recall(DummyPatientAppointmentDoctorSide patientandAppointmentData,
            CalandarDay selectedCalandar, DummyUser loggedUser) {

        try {

            if (patientandAppointmentData == null || selectedCalandar == null || loggedUser == null) {
                throw new NullPointerException(" either patientandAppointmentData == null || selectedCalandar==null || loggedUser ==null  is null or all are null");
            }

            // case patient already selected and exist
            String calandarDate = selectedCalandar.getDate().format(DateTimeFormatter.ISO_DATE);
            String doctorName = loggedUser.getLast_name();
            String patientName = patientandAppointmentData.getFirst_name();
            String patientEmail = patientandAppointmentData.getEmail();
            // doctor company link or static our Website link
            String companyLink = "<a href=\"facebook.com\"> Useless-Clininc-Corp </a>";

            // default email to use better make this inside an outer file for outside access
            MySmtpEmail smtp = new MySmtpEmail("2a21group5@gmail.com", "cotlmerzeszyfwuu");
            // setting up emails in this case one only thats why i passed in array this style
            smtp.set_emailsToSentTo(new String[]{patientEmail});

            // returns array [0]=>htmlVersion [1]=>textVersion
            String[] emailBody = EmailSendHtmlMsg.htmlBodyNotifyReminderHtmlMsgReturn_plus_rawText(patientName,
                    doctorName, calandarDate, companyLink);
            // no text version is provided only [0]==>html text version is set to "".
            smtp.setEmailSubject_andBody("Notification to attend Appointment on "+calandarDate, "", emailBody[0]);
            smtp.sendEmail();

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    //--****** Group notification  Email Delete
    public static void sendEmail_Notification_DeleteForAllUsersPassed(Collection<DummyPatientAppointmentDoctorSide> patientandAppointmentsData, CalandarDay selectedCalandar, DummyUser loggedUser) {
        try {
            
            //case 1
            if (patientandAppointmentsData== null || selectedCalandar == null || loggedUser == null) {
                throw new NullPointerException(" either patientandAppointmentData == null || selectedCalandar==null || loggedUser ==null  is null or all are null");
            }
            //case 2
            if(patientandAppointmentsData.isEmpty() ){
                System.out.println("No email has been passed to send to .");
                return;
            }
            
    // Assuming all emails Are Valid During User Subscription
            
            // default email to use better make this inside an outer file for outside access
            MySmtpEmail smtp = new MySmtpEmail("2a21group5@gmail.com", "cotlmerzeszyfwuu");
            
            // comman Data no looping needed
            String calandarDate = selectedCalandar.getDate().format(DateTimeFormatter.ISO_DATE);
            String doctorName = loggedUser.getLast_name();
            String companyLink = "<a href=\"facebook.com\"> Useless-Clininc-Corp </a>";
            
            // Generating Emails by looping 
            for(DummyPatientAppointmentDoctorSide patient : patientandAppointmentsData){
                
                String patientName = patient.getFirst_name();
                String patientEmail = patient.getEmail();
                 // setting up emails in this case one only thats why i passed in array this style
                smtp.set_emailsToSentTo(new String[]{patientEmail});

                // returns array [0]=>htmlVersion [1]=>textVersion
                String[] emailBody = EmailSendHtmlMsg.htmlBodyNotifyCancelHtmlMsgReturn_plus_rawText(patientName,
                    doctorName, calandarDate, companyLink);
                // no text version is provided only [0]==>html text version is set to "".
                smtp.setEmailSubject_andBody("Your appointment has been canceled on Date: "+calandarDate, "", emailBody[0]);
                smtp.sendEmail();
            }

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
    
    //--****** Group notification  Email
    public static void sendEmail_Notification_Recall_ForAllUsersPassed(Collection<DummyPatientAppointmentDoctorSide> patientandAppointmentsData, CalandarDay selectedCalandar, DummyUser loggedUser) {
        try {
            
            //case 1
            if (patientandAppointmentsData== null || selectedCalandar == null || loggedUser == null) {
                throw new NullPointerException(" either patientandAppointmentData == null || selectedCalandar==null || loggedUser ==null  is null or all are null");
            }
            //case 2
            if(patientandAppointmentsData.isEmpty() ){
                System.out.println("No email has been passed to send to .");
                return;
            }
            
    // Assuming all emails Are Valid During User Subscription
            
            // default email to use better make this inside an outer file for outside access
            MySmtpEmail smtp = new MySmtpEmail("2a21group5@gmail.com", "cotlmerzeszyfwuu");
            
            // comman Data no looping needed
            String calandarDate = selectedCalandar.getDate().format(DateTimeFormatter.ISO_DATE);
            String doctorName = loggedUser.getLast_name();
            String companyLink = "<a href=\"facebook.com\"> Useless-Clininc-Corp </a>";
            
            // Generating Emails by looping 
            for(DummyPatientAppointmentDoctorSide patient : patientandAppointmentsData){
                
                String patientName = patient.getFirst_name();
                String patientEmail = patient.getEmail();
                 // setting up emails in this case one only thats why i passed in array this style
                smtp.set_emailsToSentTo(new String[]{patientEmail});

                // returns array [0]=>htmlVersion [1]=>textVersion
                String[] emailBody = EmailSendHtmlMsg.htmlBodyNotifyReminderHtmlMsgReturn_plus_rawText(patientName,
                    doctorName, calandarDate, companyLink);
                // no text version is provided only [0]==>html text version is set to "".
                smtp.setEmailSubject_andBody("Reminder you have an appointment on "+calandarDate, "", emailBody[0]);
                smtp.sendEmail();
            }

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
    
//-------Custom Emails 1
    public static void customEmailSend_withBasicSystemAccount(String[] emailsArrayToSendTo, String subject, String HtmlBody) {

        if (emailsArrayToSendTo.length == 0 || subject == null || HtmlBody == null) {
            throw new NullPointerException(" either emailsArrayToSendTo.length ==0 || subject==null || HtmlBody ==nul  is null or all are null or wron email to send to ");
        }

        try {

            // default email to use better make this inside an outer file for outside access
            MySmtpEmail smtp = new MySmtpEmail("2a21group5@gmail.com", "cotlmerzeszyfwuu");
            // setting up emails in this case one only thats why i passed in array this style
            smtp.set_emailsToSentTo(emailsArrayToSendTo);

            smtp.setEmailSubject_andBody(subject, "", HtmlBody);

            smtp.sendEmail();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }
    
    
    
//custom email 2
    public static void customEmailSend_withYourPrivateAccount( String myPrivateEmailAccount , String myPrivatePasswordAccount, 
                                        String[] emailsArrayToSendTo, String subject, String HtmlBody) {

        if (emailsArrayToSendTo.length == 0 || subject == null || HtmlBody == null) {
            throw new NullPointerException(" either emailsArrayToSendTo.length ==0 || subject==null || HtmlBody ==nul  is null or all are null or wron email to send to ");
        }

        try {

            // default email to use better make this inside an outer file for outside access
            MySmtpEmail smtp = new MySmtpEmail( myPrivateEmailAccount , myPrivatePasswordAccount);
            // setting up emails in this case one only thats why i passed in array this style
            smtp.set_emailsToSentTo(emailsArrayToSendTo);

            smtp.setEmailSubject_andBody(subject, "", HtmlBody);

            smtp.sendEmail();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    
    
    
}
