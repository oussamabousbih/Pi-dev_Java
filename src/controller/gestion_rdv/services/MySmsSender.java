/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gestion_rdv.services;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import entities.DummyUser;
import entities.gestion_rdv.DummyPatientAppointmentDoctorSide;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author rbaih
 */
public class MySmsSender {

    // Twilio API credentials (Replace with your actual credentials)
    private static final String ACCOUNT_SID;
    private static final String AUTH_TOKEN;
    // Sender 
    private static final String FROM_PHONE_NUMBER; // Replace with your Twilio phone number
    //message used for sending;
    private static Message message;

    static {
        //lIVE CREDENTIALS
        ACCOUNT_SID = "AC07fb453c672a4dad519197150901c5eb"; // Replace with your actual account SID
        AUTH_TOKEN = "cd4435072a98cb550f8beba2421fea4e"; // Replace with your actual auth token
        
        //PHONE PROVIDED FOR SENDING
        FROM_PHONE_NUMBER = "+16204558740"; // Replace with your Twilio phone number

        // Initialize Twilio with API credentials
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    //************Dummy USER*********************************************************************************
    public static boolean sendEmailtoDummyUsers(DummyUser[] users, String commonMsg) {

        // Initialize Twilio with API credentials
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Collection<Boolean> isSent = new ArrayList<>();

        for (DummyUser user : users) {

            // Message body and recipient phone number
            String messageBody = "\nDear " + user.getFirst_name().toUpperCase() + "\n"
                    + commonMsg
                    + ".\nthanks for chosing us.";

            //Creating msg ready
            MessageCreator msgCreated = Message.creator(new PhoneNumber("+216" + user.getPhone_number()),new PhoneNumber(FROM_PHONE_NUMBER),
                    messageBody);

            try {
                // Send SMS
                message = msgCreated.create();
            } catch (ApiException e) {
                // Handle Twilio ApiException here
                System.err.println("Must Upgrage Trillo Account For sms eception showed and handled" + e.getMessage());
            }

            // test if sent 
            isSent.add(message.getStatus() == Message.Status.SENT);
            // Print SMS debug purpose
            System.out.println("Sent SMS SID: " + message.getSid());
            System.out.println("Sent SMS Status: " + message.getStatus());

        }// end loop send multiple

        System.out.println("isSent result : " + isSent.toString());

        if (isSent.contains(Boolean.TRUE)) {
            System.out.println(isSent.contains(Boolean.FALSE) ? "some sms are sent and some are not ." : "all sms are sent .");
            return true;
        }

        return false;

    }

    //************DummyPatientAppointment*********************************************************************************
    public static boolean sendEmailToDummyPatientsByDoctor(DummyPatientAppointmentDoctorSide[] Patientsappointment, String commonMsg) {

        Collection<Boolean> isSent = new ArrayList<>();

        for (DummyPatientAppointmentDoctorSide appUser : Patientsappointment) {

            // Message body and recipient phone number
            String messageBody = "\nDear" + appUser.getFirst_name() + "\n"
                    + commonMsg + ""
                    + " on " + appUser.getDate().format(DateTimeFormatter.ISO_DATE)
                    + " at " + appUser.getTime().format(DateTimeFormatter.ISO_LOCAL_TIME)
                    + ".\nthanks fo choosing us.";

            //Creating msg ready
            MessageCreator msgCreated = Message.creator(new PhoneNumber("+216" + appUser.getPhone_number()),
                    new PhoneNumber(FROM_PHONE_NUMBER),
                    messageBody);

            try {
                // Send SMS
                message = msgCreated.create();
            } catch (ApiException e) {
                // Handle Twilio ApiException here
                System.err.println("Must Upgrage Trillo Account For sms eception showed and handled" + e.getMessage());
            }

            // test if sent 
            if (message != null);
            isSent.add(message.getStatus() == Message.Status.SENT);
            // Print SMS debug purpose
            System.out.println("Sent SMS SID: " + message.getSid());
            System.out.println("Sent SMS Status: " + message.getStatus());

        }// end loop send multiple

        System.out.println("isSent result : " + isSent.toString());

        if (isSent.contains(Boolean.TRUE)) {
            System.out.println(isSent.contains(Boolean.FALSE) ? "some sms are sent and some are not ." : "all sms are sent .");
            return true;
        }

        return false;

    }

    //********Send To Numbers *********************************************************************
    private static boolean sendEmail(String[] numbers, String commonMsg) {

        Collection<Boolean> isSent = new ArrayList<>();

        for (String number : numbers) {

            // Message body and recipient phone number
            String messageBody = commonMsg;

            //Creating msg ready
            MessageCreator msgCreated = Message.creator(new PhoneNumber("+216" +number ) ,new PhoneNumber(FROM_PHONE_NUMBER),
                    messageBody);

            try {
                // Send SMS
                message = msgCreated.create();
            } catch (ApiException e) {
                // Handle Twilio ApiException here
                System.err.println("Must Upgrage Trillo Account For sms eception showed and handled" + e.getMessage());
            }

            // test if sent 
            isSent.add(message.getStatus() == Message.Status.SENT);
            // Print SMS debug purpose
            System.out.println("Sent SMS SID: " + message.getSid());
            System.out.println("Sent SMS Status: " + message.getStatus());

        }// end loop send multiple

        System.out.println("isSent result : " + isSent.toString());

        if (isSent.contains(Boolean.TRUE)) {
            System.out.println(isSent.contains(Boolean.FALSE) ? "some sms are sent and some are not ." : "all sms are sent .");
            return true;
        }

        return false;
    }

    
    
    //main for testing
//    public static void main(String[] args) {
//
//        //Just To a number and write full msg;
//        MySmsSender.sendEmail(new String[]{ "28197096"}  , "hellow world \n good boy ");
//        
//        
//        // to Users Collection
//        Collection<DummyUser> users = new ArrayList<>();
//        users.add(new DummyUser(0, "", "aPerson", "", "28197096", ""));
//        MySmsSender.sendEmailtoDummyUsers(  users  , "we infor me you that you have an appointmet ");
//        
//        //To appointment Collection
//        DummyPatientAppointmentDoctorSide appPatient = new  DummyPatientAppointment();
//        appPatient.setFirst_name(" aPerson");
//        appPatient.setPhone_number(" aPerson");
//        appPatient.setDate(LocalDate.now());
//        appPatient.setTime(LocalTime.now());
//        Collection<DummyPatientAppointmentDoctorSide> appointmentsUsers = new ArrayList<>();
//        MySmsSender.sendEmailToDummyPatientsByDoctor( appointmentsUsers , "hellow world \n good boy ");
//        
//    }
    
    
}
