package entities;

import java.time.LocalDate;
import java.util.Date;

public class User {
    private int id;
    private String email;
    private String role;
    private String password;
    private String firstName;
    private  String lastName;
    private String location;
    private String speciality;
    private String phoneNumber;
    private String image;
    private String licence;
    private Date dateOfBirth;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private String age;
    private String resetToken;
    private int isVerified;
    private String isBlocked;
    private String gender;
    private String lastLogin;
    private String Extra1Rdv;
    private String banned;

    public User() {
    }

    public User(int id, String email, String role, String password, String firstName, String lastName, String location, String speciality, String phoneNumber, String image, String licence, Date dateOfBirth, String status, Date createdAt, Date updatedAt, String age, String resetToken, int isVerified, String isBlocked,String banned) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.speciality = speciality;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.licence = licence;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.age = age;
        this.resetToken = resetToken;
        this.isVerified = isVerified;
        this.isBlocked = isBlocked;
        this.banned = banned;
    }

    public User(String email, String role, String password, String firstName, String lastName, String location, String speciality, String phoneNumber, String image, String licence, Date dateOfBirth, String status, Date createdAt, Date updatedAt, String age, String resetToken, int isVerified, String isBlocked,String banned) {
        this.email = email;
        this.role = role;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.speciality = speciality;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.licence = licence;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.age = age;
        this.resetToken = resetToken;
        this.isVerified = isVerified;
        this.isBlocked = isBlocked;
        this.banned = banned;
    }
    public User(String banned){
        this.banned = banned;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        this.isBlocked = isBlocked;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getExtra1Rdv() {
        return Extra1Rdv;
    }

    public void setExtra1Rdv(String extra1Rdv) {
        Extra1Rdv = extra1Rdv;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + location + '\'' +
                ", speciality='" + speciality + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", image='" + image + '\'' +
                ", licence='" + licence + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", age='" + age + '\'' +
                ", resetToken='" + resetToken + '\'' +
                ", isVerified=" + isVerified +
                ", isBlocked='" + isBlocked + '\'' +
                ", gender='" + gender + '\'' +
                ", lastLogin='" + lastLogin + '\'' +
                ", Extra1Rdv='" + Extra1Rdv + '\'' +
                ", banned='" + banned + '\'' +
                '}';
    }


}
