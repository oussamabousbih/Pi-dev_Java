/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.TypeService;
import utils.MyConnexion;
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
public class CRUDTypeService implements interfaces.InterfaceTypeService {
    Statement ste;
    Connection conn = MyConnexion.getInstance().getConn();

    @Override
    public void addTypeServices(TypeService TS) {
        try {
            // INSERT INTO `type_service`(`id`, `nom_type`, `description`) VALUES ('[value-1]','[value-2]','[value-3]')
            String req = "INSERT INTO `type_service`(`id`, `nom_type`, `description`) VALUES ('"+TS.getId()+"','"+TS.getNomType()+"','"+TS.getDescription()+"')";
            ste = conn.createStatement();
            ste.executeUpdate(req);
            System.out.println("succés!");
        } catch (SQLException ex) {
            System.out.println("failure of add!"+ex.getMessage());
        }
    }

    @Override
    public void updateTypeServices(TypeService TS) {
        try {
            String req = "UPDATE `type_service` SET `nom_type`='" + TS.getNomType() + "', `description`='" + TS.getDescription() + "' WHERE `id`='" + TS.getId() + "'";
            ste = conn.createStatement();
            int rowsUpdated = ste.executeUpdate(req);
            if (rowsUpdated > 0) {
                System.out.println("Success! " + rowsUpdated + " rows updated.");
                } else {
                    System.out.println("Failure: no rows updated.");
                }
            } catch (SQLException ex) {
                System.out.println("Failure of update! " + ex.getMessage());
            }
    }

    @Override
    public void deleteTypesServices(TypeService TS) {
        try {
        String req = "DELETE FROM `type_service` WHERE `id`='" + TS.getId() + "'";
        ste = conn.createStatement();
        ste.executeUpdate(req);
        System.out.println("Record with id " + TS.getId() + " deleted successfully!");
        } catch (SQLException ex) {
            System.out.println("Error deleting record with id " + TS.getId() + ": " + ex.getMessage());
        }
    }

    @Override
    public List<TypeService> showTypesServices() {
        List<TypeService> list = new ArrayList<>();
        String req = "SELECT * FROM `type_service`";
        try {
            ste = conn.createStatement();
            ResultSet RS = ste.executeQuery(req);
            while(RS.next()){
                TypeService TS = new TypeService();
                TS.setId(RS.getInt(1));
                TS.setNomType(RS.getString("nom_type"));
                TS.setDescription(RS.getString("description"));
                list.add(TS);
            }
            System.out.println("-----jawna behi!!-----");
        } catch (SQLException ex) {
            System.out.println("mochkla ! : " + ex.getMessage());
        }
        
        return list;
    }
    
}
