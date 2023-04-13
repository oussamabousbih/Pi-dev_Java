/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author AOUADI HADIL
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyConnection {
    static MyConnection instance;
    private Connection  conn;
    String url="jdbc:mysql://localhost:3306/pi3a35_test";
    String user = "root";
    String pwd = "";
    
    
    
    private MyConnection() {
        
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("connexion etablie");
        } catch (SQLException ex) {
           System.out.println(" probléme de connesxion ");
        }
    }
      public static MyConnection getInstance(){
        if (instance==null){
            instance = new MyConnection() ;
        }
        return instance ;
      }

    public Connection getConn() {
        return conn;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPwd() {
        return pwd;
    }

   
    
    } 

