/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import domain.Menu;
import domain.ItemType;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author syntel
 */
public class ItemTypeServices {
//implements Service<itemType> {
    
    Connection connection;
    CallableStatement orclCallableStatement;

    public ItemTypeServices(Connection connection) {
        this.connection = connection;
    }
    
    public void deleteById(String id){
       Connection conn;
       try{
           //call driver
           Class.forName("oracle.jdbc.OracleDriver");
           //Database connection
           conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","db_uspring","pass");
           //call SP for deleting item types
           orclCallableStatement=conn.prepareCall("{Call SP_DEL_ITEM_TYPES(?)}");
           orclCallableStatement.setString(1, id);
           orclCallableStatement.execute();
           
           orclCallableStatement.close();
           conn.close();
           
       }catch(Exception e){
           System.out.println(e.getMessage());
       }
        
    }
    public boolean add(ItemType it){
        Connection conn;
        
        try{
            //get the driver
            Class.forName("oracle.jdbc.OracleDriver");
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
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    //public void update(E obj);
    //public E getById(String id);
    //public ArrayList<E> getAll();
    //public void add(ItemType it){
        
    
}
