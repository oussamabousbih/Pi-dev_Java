/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi;

import Entities.TypesServices;
import services.CRUDTypesServices;
import utils.MyConnection;

/**
 *
 * @author Yasmine Rajhi
 */
public class Pi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MyConnection conn = new MyConnection();
        TypesServices TS = new TypesServices(1,"fdez","zefez");
        CRUDTypesServices ajout = new CRUDTypesServices();
        ajout.showTypesServices();
    }
    
    
}
