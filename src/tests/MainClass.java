/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import services.PasswordHasher;
import services.SmtpEmailUser;
import services.UserService;
import utils.MyConnection;

import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
public class MainClass {
    public static void main(String[] args) throws MessagingException {
        MyConnection mc = MyConnection.getInstance();
     /*   User user = new User();
        user.setEmail("john.doe@example.com");
        user.setRole("admin");
        user.setPassword("secret");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setSpeciality("Developer");
        user.setLicence("123456");
        user.setLocation("New York");
        user.setPhoneNumber("555-1234");
        user.setDateOfBirth(new Date(2000, 1, 1));
        user.setStatus("active");
        user.setCreatedAt(new Date());
        user.setGender("male");
        user.setLastLogin("2022-04-09 14:30:00");
        user.setAge("22");
        user.setIsVerified(1);
        // us.createUser(user);
        User user3 = us.afficherUser("moudir@mail.com");
        System.out.println(user3.toString());
        user3.setFirstName("moudirjava");
        System.out.println(us.updateUser(user3,"moudir@mail.com"));
        us.updateUser(user3,user3.getEmail());*/
        UserService us = new UserService();
        //System.out.println(us.login("",""));
           if(us.selectAllUsers().isEmpty()){
               System.out.println("mafich");
           }else {
               //yhezni lel interface lokhra;
           }
            //namlou select kbal manajmou namlou update nbadlou chbch nbadlou w baed namlou us.updateUser , namlou select kbal bch mayecrazich el instance lkdima w yhot null fel jdida
        System.out.println(PasswordHasher.hashPassword("zebi"));

    }
}
