/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Yasmine Rajhi
 */
public class TypeService {
    private int id;
    private String NomType, Description;

    public TypeService() {
    }

    public TypeService(int id, String NomType, String Description) {
        this.id = id;
        this.NomType = NomType;
        this.Description = Description;
    }

    public TypeService(String NomType, String Description) {
        this.NomType = NomType;
        this.Description = Description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomType() {
        return NomType;
    }

    public void setNomType(String NomType) {
        this.NomType = NomType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public String toString() {
        return "TypeService{" + "id=" + id + ", NomType=" + NomType + ", Description=" + Description + '}';
    }

}
