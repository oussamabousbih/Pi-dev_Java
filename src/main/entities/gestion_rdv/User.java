/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.entities.gestion_rdv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rbaih
 */
public class User {
    private final Collection<CalandarDay> calander_days;
    private final Collection<Appointment> doctor_appointments;
    private final Collection<Appointment> user_appointments;
    
    private Integer id;
    private String email;
    private String password;
    private List<String> roles = new ArrayList<>();
    private String image;
    private String first_name;
    private String last_name;
    private String speciality;
    private String licence;
    private String location;
    private String phone_number;
    private Date date_of_birth;
    private String status;
    private Date created_at;
    private String gender;
    private String last_login;
    private Date updated_at;
    private String extra1_rdv;
    private String age;

    
    
    public User(Integer id)
    {
       this.id=id;
       
       this.calander_days = new ArrayList<>();
       this.doctor_appointments = new ArrayList<>();
       this.user_appointments = new ArrayList<>();  
    }
    public User()
    {
       this.calander_days = new ArrayList<>();
       this.doctor_appointments = new ArrayList<>();
       this.user_appointments = new ArrayList<>();  
    }

    public User(Integer id, String email, String password, String image, String first_name, 
            String last_name, String speciality, String licence, String location, String phone_number, 
            Date date_of_birth, String status, Date created_at, String gender, String last_login, 
            Date updated_at, String extra1_rdv, String age, Collection<CalandarDay> calander_days, 
            Collection<Appointment> doctor_appointments, Collection<Appointment> user_appointments) {
        
        this.calander_days = new ArrayList<>();
        this.doctor_appointments = new ArrayList<>();
        this.user_appointments = new ArrayList<>(); 
        
        this.id = id;
        this.email = email;
        this.password = password;
        this.image = image;
        this.first_name = first_name;
        this.last_name = last_name;
        this.speciality = speciality;
        this.licence = licence;
        this.location = location;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.status = status;
        this.created_at = created_at;
        this.gender = gender;
        this.last_login = last_login;
        this.updated_at = updated_at;
        this.extra1_rdv = extra1_rdv;
        this.age = age;
        this.calander_days.addAll(calander_days);
        this.doctor_appointments.addAll(user_appointments);
        this.user_appointments.addAll(user_appointments);
    }

    @Override
    public String toString() {
//        return "User{" + "calander_days=" + calander_days + ", doctor_appointments=" + doctor_appointments + ", user_appointments=" + user_appointments + ", id=" + id + ", email=" + email + ", password=" + password + ", roles=" + roles + ", image=" + image + ", first_name=" + first_name + ", last_name=" + last_name + ", speciality=" + speciality + ", licence=" + licence + ", location=" + location + ", phone_number=" + phone_number + ", date_of_birth=" + date_of_birth + ", status=" + status + ", created_at=" + created_at + ", gender=" + gender + ", last_login=" + last_login + ", updated_at=" + updated_at + ", extra1_rdv=" + extra1_rdv + ", age=" + age + '}';
        return "User{" + "id=" + id + ", email=" + email +  ", roles=" + roles + ", first_name=" + first_name + ", last_name=" + last_name +  '}';
    }
    
  
    
   

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public String getExtra1_rdv() {
        return extra1_rdv;
    }

    public void setExtra1_rdv(String extra1_rdv) {
        this.extra1_rdv = extra1_rdv;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    
    
    
    
}
