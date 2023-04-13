/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.services.gestion_rdv.exceptions;

import java.sql.SQLException;

/**
 *
 * @author rbaih
 */
public class DeleteException extends SQLException{

    public DeleteException(String reason) {
        super(reason);
    }
    
    
}
