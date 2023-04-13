/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 * @author rbaih
 */
public final class JdbcConnection implements AutoCloseable {
    
   // volatile  when using multithreading 
    private static /* volatile */JdbcConnection INSTANCE = null;
    private final Connection connection;
//    private static final Object LOCK = new Object(); // for thread safe
    private static final Logger LOGGER = Logger.getLogger(JdbcConnection.class.getName());  // to log better then print expensive 
   
    
    String driverClassName ;
    String url ;
    String username ;
    String password ;
    
    public void setConnection_and_DriverResources_From_PropertiesFile_To_Our_Attributes(){
        // creating the resourese bundle wich can read from the .properties file the settings
        String currenPackage = this.getClass().getPackage().getName();
        ResourceBundle resBundle = ResourceBundle.getBundle(currenPackage+".jdbc");
        
        this.driverClassName = resBundle.getString("driverClassName");
        this.url = resBundle.getString("url");
        this.username = resBundle.getString("username");
        this.password = resBundle.getString("password");
    }
    
    
    private JdbcConnection() throws SQLException { // throwing exception in purpose to avoid hidden bugs
        try {
            
            this.setConnection_and_DriverResources_From_PropertiesFile_To_Our_Attributes(); // setting the necessary setting on the attributes

            Class.forName(this.driverClassName); // getting driver
            
            this.connection = DriverManager.getConnection(this.url, this.username, this.password); // creating a connection
            
            LOGGER.log(Level.FINE,"JdbcConnection instance successfully created and connection assigned to attribute connection");
            
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE,"Error occurred during creation of connection on constructor level", e);
            throw new SQLException("Failed to create connection on the constructor level", e);
        }
    }
    
    //------------- this return an instance of a class whenever needed and insure that no multiple instance of connections are created
   public static JdbcConnection getInstance() throws SQLException {
        if (INSTANCE == null) {
//            synchronized (LOCK) {
//                if (INSTANCE == null) {  // thread safety 
                    INSTANCE = new JdbcConnection();
                    
//                }
//            }
        }
        return INSTANCE;
    }
    
    //---------------------------------------------------------------------------------
 
    public Connection getConnection() throws SQLException { // throws exception in extreme condition no connection
        
        if (connection == null || connection.isClosed()) {
//            synchronized (LOCK) {
//                if (connection == null || connection.isClosed()) {
                    INSTANCE = new JdbcConnection(); // creating a new instance in case the connection get closed 
//                }
//            }
        }
        return INSTANCE.connection;
              

    }
    
    // -----------------------------------------------------------------------
    


    @Override
    public void close() {
        
        Boolean isConnectionNull = null; // not important just for debugging in the future 
        Boolean isInstanceNULL = null; // not important just for debugging in the future
        Boolean isConnectionClosed = null; // not important just for debugging in the future
        
        try {
            isConnectionNull = (connection==null);  
            isInstanceNULL = (INSTANCE == null);  
            isConnectionClosed = connection.isClosed() ; 
            
            if (connection != null && !connection.isClosed()) {            
                connection.close();
                
            if (connection == null) isConnectionNull =true;  // this is trush just for debug behavior if needed in the future
            if (connection.isClosed() ) isConnectionClosed =true; // this is trush for debug and future use if any bug with the finally block
            
            LOGGER.log(Level.FINE,"Connection attribute is now closed successfuly ");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"Error occurred while trying to close the connection on close method", e);
        } 
//        finally {  // not really necessary since we deal with one single instance anyway
//
//                 LOGGER.log(Level.INFO, "@ close() \t" 
//                    +"attrbute_connection's closed == "+isConnectionClosed +" .\t"
//                    + "attribute_connection's NULL == "+isConnectionNull+" .\t"
//                    + "attribute_INSTANCE's NULL == "+isInstanceNULL+" ."  
//                 );
//         
//                INSTANCE = null;  // whatever happen make this null already too late 
//        }
        
    }
   
   
   
}




















