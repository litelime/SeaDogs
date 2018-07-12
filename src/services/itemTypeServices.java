/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import domain.Menu;
import domain.itemType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author syntel
 */
public class itemTypeServices {
//implements Service<itemType> {
    
    Connection connection;
    CallableStatement orclCallableStatement;

    public itemTypeServices(Connection connection) {
        this.connection = connection;
    }
    
    public void deleteById(String id){
       Connection conn;
       try{
           //call driver
           //Class.forName("oracle.jdbc.OracleDriver");
           //Database connection
           conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","db_uspring","pass");
           //call SP for deleting item types
           orclCallableStatement=conn.prepareCall("{Call SP_DEL_ITEM_TYPES(?)}");
           orclCallableStatement.setString(1, id);
           orclCallableStatement.execute();
           
           orclCallableStatement.close();
           conn.close();
           
       }catch(SQLException e){
           System.out.println(e.getMessage());
       }
        
    }
    public boolean add(itemType it){
        Connection conn;
        
        try{
            //get the driver
            //Class.forName("oracle.jdbc.OracleDriver");
            //connecting to database
            conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","db_uspring","pass");
            System.out.println("Connection is successful");
            
            orclCallableStatement=conn.prepareCall("{call SP_ADD_ITEM_TYPES(?,?)}");
            orclCallableStatement.setString(1, it.getItemTypeId());
            orclCallableStatement.setString(2, it.getItemType());
            
            orclCallableStatement.execute();
            //close all resources
            orclCallableStatement.close();
            conn.close();
            return true;           
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    public void update(itemType itype){
        CallableStatement cs;
        Connection connect;
        try{
            //get the driver
            //Class.forName("oracle.jdbc.OracleDriver");
            //connecting to database
            connect=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "db_uspring", "pass");
            System.out.println("Connection is successful");
            System.out.println("here in update1");
            cs=connect.prepareCall("{call SP_UPD_ITEM_TYPES(?,?)}");
            cs.setString(1, itype.getItemTypeId());
            cs.setString(2, itype.getItemType());
            
            System.out.println("here in update2: it id " + itype.getItemTypeId() + " \n aafg: " + itype.getItemType());
            cs.execute();
            System.out.println("here in update3");
            //close all resources
            cs.close();
            connect.close();
            //return true;
                      
        }catch(SQLException e){
            System.out.println(e.getMessage());
            //return false;
        }
       
               
    }
    //public E getById(String id)
    //public ArrayList<E> getAll();
    
        
    
}
