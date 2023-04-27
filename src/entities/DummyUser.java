/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author rbaih
 */
public class DummyUser {
    
    private Integer id;
    private String email;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String roles;

    public DummyUser() {
    }

    @Override
    public String toString() {
        String title;
        if( roles.contains("\"ROLE_DOCTOR\""))
            title="Dr";
        else
            title="Mr/MiZ:";
        return title+" "+ last_name.toUpperCase() + "    Phone: +216"+ phone_number +"    Email: "+email ;
    }
    
    public DummyUser(Integer id, String email, String first_name, String last_name, String phone_number, String roles) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.roles = roles;
    }

    public DummyUser( String email, String first_name, String last_name, String phone_number, String roles) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.roles = roles;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    
    
    
    
}
