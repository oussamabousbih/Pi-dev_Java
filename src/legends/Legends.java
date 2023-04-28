/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package legends;

import Entities.Service;
import Entities.TypeService;
import Services.CRUDService;
import Services.CRUDTypeService;
import Utils.MyConnexion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

/**
 *
 * @author Yasmine Rajhi
 */
public class Legends {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            MyConnexion cc = MyConnexion.getInstance();
            CRUDService crud = new CRUDService();
            CRUDTypeService crud_type = new CRUDTypeService();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = new Date(dateFormat.parse("2023-04-22").getTime());
            Date endDate = new Date(dateFormat.parse("2023-04-25").getTime());

            Service S = new Service(8, "service1", "owner1", "1", "20", startDate, endDate);
            Service updatedService = new Service(1, "new service name", "new owner", "2", "30", startDate, endDate);
            
            /*------------ SERVICE CRUD YI5DEM ------------*/
            //crud.addServices(S);
            //crud.updateServices(updatedService);
            System.out.println(crud.showServices());
            //crud.deleteServices(S);
            
            /*------------ TYPE SERVICE CRUD YI5DEM ------------*/
            TypeService TS = new TypeService(7,"test_update_java","test1_desc");
            //crud_type.addTypeServices(TS);
            //crud_type.deleteTypesServices(TS);
            System.out.println(crud_type.showTypesServices());
            //crud_type.updateTypeServices(TS);
            
        } catch (ParseException ex) {
            System.out.println("mochkla!! : " + ex.getMessage());
        }
    }
    
}
