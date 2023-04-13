/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import Entities.TypesServices;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yasmine Rajhi
 */
public class CRUDTypesServices implements Interfaces.InterfaceTypesServices {

    Statement ste;
    Connection conn;
    
    @Override
    public void addTypeServices(TypesServices TS) {
        try {
            String req ="INSERT INTO `type_service`( `id`,`nom_type`, `description`) VALUES ('"+TS.getId()+"','"+TS.getNomType()+"','"+TS.getDescription()+"')";
            ste = conn.createStatement();
            ste.executeUpdate(req);
            
            System.out.println("Type ajoutée");  
        } catch (SQLException ex) {
             System.out.println("Type non ajoutée");
             System.out.println(ex.getMessage());
        }
    }

    @Override
    public void updateTypeServices(TypesServices TS) {try {
        String req = "UPDATE `type_service` SET `nom_type`='" + TS.getNomType() + "', `description`='" + TS.getDescription() + "'";

        ste = conn.createStatement();
        int result = ste.executeUpdate(req);
        if (result > 0) {
            System.out.println("Type modifié ");
          
            TS.setNomType(TS.getNomType());
            TS.setDescription(TS.getDescription());
           
        } else {
            System.out.println("Impossible de modifier le type.");
        }
        } catch (SQLException ex) {
            System.out.println("Erreur lors de la modification du type : " + ex.getMessage());
        }
    }

    @Override
    public void deleteTypesServices(TypesServices TS) {
        try {
        String query = "DELETE FROM `type_service` WHERE `nom_type`='" + TS.getNomType() + "'";
        Statement stmt = conn.createStatement();
        int rows = stmt.executeUpdate(query);
        if (rows > 0) {
            System.out.println("Element deleted successfully.");
        } else {
            System.out.println("No element matches");
        }
        } catch (SQLException ex) {
            System.out.println("Error deleting element: " + ex.getMessage());
        }
    }

    @Override
    public List<TypesServices> showTypesServices() {
        List<TypesServices> list = new ArrayList<>();
        
        String req="SELECT * FROM `type_service` ";
        
        try {
    if (conn != null) {
        ste = conn.createStatement();
        ResultSet RS = ste.executeQuery(req);
        while(RS.next()){
            TypesServices TS = new TypesServices();
            TS.setId(RS.getInt(1));
            TS.setNomType(RS.getString("NomType"));
            TS.setDescription(RS.getString("description"));
            list.add(TS);
        }
        System.out.println("type affiché");
    } else {
        System.out.println("Connection object is null.");
    }
    } catch (SQLException ex) {
        System.out.println("type non affiché");
        System.out.println(ex.getMessage());
    }

                
        return list;
        }
}
