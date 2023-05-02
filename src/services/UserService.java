package services;

import entities.User;
import interfaces.UserCrud;
import utils.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserService implements UserCrud {


    @Override
    public boolean createUser(User u) {
        String requete = "INSERT INTO user " +
                "(`email`, `roles`, `password`, `first_name`, `last_name`, `speciality`, " +
                "`licence`, `location`, `phone_number`, `date_of_birth`, `status`, `created_at`, " +
                "`gender`, `last_login`,`age`," +
                " `is_verified`, `banned`)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            MyConnection connex = MyConnection.getInstance();
            PreparedStatement pst = connex.getCnx().prepareStatement(requete);
            pst.setString(1, u.getEmail());
            pst.setString(2, u.getRole());
            pst.setString(3, PasswordHasher.hashPassword(u.getPassword()));
            pst.setString(4, u.getFirstName());
            pst.setString(5, u.getLastName());
            pst.setString(6, u.getSpeciality());
            pst.setString(7, u.getLicence());
            pst.setString(8, u.getLocation());
            pst.setString(9, u.getPhoneNumber());
            pst.setDate(10, new java.sql.Date(u.getDateOfBirth().getTime()));
            pst.setString(11, u.getStatus());
            pst.setDate(12, new java.sql.Date(u.getCreatedAt().getTime()));
            pst.setString(13, u.getGender());
            pst.setString(14, u.getLastLogin());
            pst.setString(15, u.getAge());
            pst.setInt(16, u.getIsVerified());
            pst.setString(17, u.getBanned());

            System.out.println("User added successfully");
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }



    }

    @Override
        public boolean updateUser(User u,String emailrech) {
            String requete = "UPDATE user SET " +
                    "email = ?, roles = ?, password = ?, first_name = ?, last_name = ?, speciality = ?, " +
                    "licence = ?, location = ?, phone_number = ?, status = ?, " +
                    "  last_login = ?, age = ?, is_verified = ? , reset_token = ? , banned= ? "+
                    "WHERE email = ? ";


        try {
            MyConnection connex = MyConnection.getInstance();
            PreparedStatement pst = connex.getCnx().prepareStatement(requete);
            pst.setString(1, u.getEmail());
            pst.setString(2, u.getRole());
            pst.setString(3, u.getPassword());
            pst.setString(4, u.getFirstName());
            pst.setString(5, u.getLastName());
            pst.setString(6, u.getSpeciality());
            pst.setString(7, u.getLicence());
            pst.setString(8, u.getLocation());
            pst.setString(9, u.getPhoneNumber());
            pst.setString(10, u.getStatus());
            pst.setString(11, u.getLastLogin());
            pst.setString(12, u.getAge());
            pst.setInt(13, u.getIsVerified());
            pst.setString(14, u.getResetToken());
            pst.setString(15, u.getBanned());
            pst.setString(16, emailrech);

            System.out.println("User updated successfully  "+ emailrech);
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }   }


    @Override
    public boolean deleteUser(String  email) {
        String requete = "delete from user where email=?; ";

        try {
            MyConnection connex = MyConnection.getInstance();
            PreparedStatement pst = connex.getCnx().prepareStatement(requete);
            pst.setString(1, email);

            System.out.println("User added successfully");
            return pst.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }


    @Override
    public User afficherUser(String emailId) {
        String requete = "SELECT * FROM user WHERE email = ?";
        try {
            MyConnection connex = MyConnection.getInstance();
            PreparedStatement pst = connex.getCnx().prepareStatement(requete);
            pst.setString(1, emailId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Retrieve data from ResultSet
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String role = rs.getString("roles");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String speciality = rs.getString("speciality");
                String licence = rs.getString("licence");
                String location = rs.getString("location");
                String phoneNumber = rs.getString("phone_number");
                Date dateOfBirth = rs.getDate("date_of_birth");
                String status = rs.getString("status");
                Date createdAt = rs.getDate("created_at");
                String gender = rs.getString("gender");
                String lastLogin = rs.getString("last_login");
                String age = rs.getString("age");
                int isVerified = rs.getInt("is_verified");
                String banned = rs.getString("banned");

                // Create a User object and set its attributes
                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setRole(role);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setSpeciality(speciality);
                user.setLicence(licence);
                user.setLocation(location);
                user.setPhoneNumber(phoneNumber);
                user.setDateOfBirth(dateOfBirth);
                user.setStatus(status);
                user.setCreatedAt(createdAt);
                user.setGender(gender);
                user.setLastLogin(lastLogin);
                user.setAge(age);
                user.setIsVerified(isVerified);
                user.setBanned(banned);
                return user;
            } else {
                return null; // No user found with given email
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }

   /* @Override
    public User login(String emaillog, String passwordlog) {
        String hash= PasswordHasher.hashPassword(passwordlog);
                String requete = "SELECT * FROM user WHERE email = ? and password=?";
        try {
            MyConnection connex = MyConnection.getInstance();
            PreparedStatement pst = connex.getCnx().prepareStatement(requete);
            pst.setString(1, emaillog);
            System.out.println("passwordlog        "+hash + "\nemaillog   " + emaillog);
            pst.setString(2, hash);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                // Retrieve data from ResultSet
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String role = rs.getString("roles");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String speciality = rs.getString("speciality");
                String licence = rs.getString("licence");
                String location = rs.getString("location");
                String phoneNumber = rs.getString("phone_number");
                Date dateOfBirth = rs.getDate("date_of_birth");
                String status = rs.getString("status");
                Date createdAt = rs.getDate("created_at");
                String gender = rs.getString("gender");
                String lastLogin = rs.getString("last_login");
                String age = rs.getString("age");
                int isVerified = rs.getInt("is_verified");

                // Create a User object and set its attributes
                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setRole(role);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setSpeciality(speciality);
                user.setLicence(licence);
                user.setLocation(location);
                user.setPhoneNumber(phoneNumber);
                user.setDateOfBirth(dateOfBirth);
                user.setStatus(status);
                user.setCreatedAt(createdAt);
                user.setGender(gender);
                user.setLastLogin(lastLogin);
                user.setAge(age);
                user.setIsVerified(isVerified);
                return user;
            } else {
                return null; // No user found with given email
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }*/

    public User login(String emaillog, String passwordlog) {
        User user = afficherUser(emaillog);
        if(user == null) {
            System.out.println("User not found");
            return null; // No user found with given email
        }
        String password = user.getPassword();
        if ( PasswordHasher.checkPassword(passwordlog, password)) {
            System.out.println("passwordlog        "+passwordlog + "\nemaillog   " + emaillog);
            return user;
        }
        else {
            System.out.println("Wrong password or email");
            return null;// No user found with given email
        }

    }

    public List<User> selectAllUsers() {
        String requete = "select * from user ";
        List<User> users = new ArrayList<>();
        try {
            MyConnection connex = MyConnection.getInstance();
            PreparedStatement pst = connex.getCnx().prepareStatement(requete);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                // Retrieve data from ResultSet
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String role = rs.getString("roles");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String speciality = rs.getString("speciality");
                String licence = rs.getString("licence");
                String location = rs.getString("location");
                String phoneNumber = rs.getString("phone_number");
                Date dateOfBirth = rs.getDate("date_of_birth");
                String status = rs.getString("status");
                Date createdAt = rs.getDate("created_at");
                String gender = rs.getString("gender");
                String lastLogin = rs.getString("last_login");
                String age = rs.getString("age");
                int isVerified = rs.getInt("is_verified");
                String banned = rs.getString("banned");

                // Create a User object and set its attributes
                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setRole(role);
                user.setPassword(password);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setSpeciality(speciality);
                user.setLicence(licence);
                user.setLocation(location);
                user.setPhoneNumber(phoneNumber);
                user.setDateOfBirth(dateOfBirth);
                user.setStatus(status);
                user.setCreatedAt(createdAt);
                user.setGender(gender);
                user.setLastLogin(lastLogin);
                user.setAge(age);
                user.setIsVerified(isVerified);
                user.setBanned(banned);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
