/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Yasmine Rajhi
 */
public class MyConnexion {
    static MyConnexion instance;
    private Connection conn;
    String url = "jdbc:mysql://localhost:3306/legends";
    String user="root";
    String pwd = "";
    private MyConnexion() {
            try{
                conn = DriverManager.getConnection(url, user, pwd);
                    System.out.println("cnx etablie!");
                   }catch (SQLException ex){
                    System.out.println("Problem");
                   }
    }
    
    public static MyConnexion getInstance(){
        if(instance==null){
            instance = new MyConnexion();
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }
    
}
