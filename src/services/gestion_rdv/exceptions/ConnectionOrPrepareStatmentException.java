/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.gestion_rdv.exceptions;

import java.sql.SQLException;

/**
 *
 * @author rbaih
 */
public class ConnectionOrPrepareStatmentException extends  SQLException{

    public ConnectionOrPrepareStatmentException(String reason) {
        super(reason);
    }
    
}
