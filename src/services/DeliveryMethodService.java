package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;

public class DeliveryMethodService implements Service<DeliveryMethod>{
	
	Connection connection;
	
	public DeliveryMethodService() {
		super();
	}
	public DeliveryMethodService(Connection connection) {
		super();
		this.connection = connection;
	}
	
	@Override
	public boolean add(DeliveryMethod deliveryMethod){
		try{
			CallableStatement statement = connection.prepareCall("{call SP_INSERT_DELIVERY_METHOD(?, ?)}");
			statement.setString(1, deliveryMethod.getDelivery_method_id());
			statement.setString(2, deliveryMethod.getDelivery_method());
			statement.execute();
			statement.close();
			return true;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return false;
		}	
	}
	
	@Override
	public void update(DeliveryMethod deliveryMethod){		
		try{
                    CallableStatement stmnt = connection.prepareCall("{call SP_UPDATE_DELIVERY_METHOD(?,?)}");
                    stmnt.setString(1, deliveryMethod.getDelivery_method_id());
                    stmnt.setString(2, deliveryMethod.getDelivery_method());
                    stmnt.execute();
                    stmnt.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public ArrayList<DeliveryMethod> getAll(){

		ArrayList<DeliveryMethod> deliverMethods = new ArrayList<DeliveryMethod>();
		
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM DELIVERY_METHODS");
			
			while(rs.next()){
				DeliveryMethod deliveryMethod = new DeliveryMethod(rs.getString(1),rs.getString(2)); 
				deliverMethods.add(deliveryMethod);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return deliverMethods;
	}
	
	@Override
	public DeliveryMethod getById(String id){
		DeliveryMethod deliveryMethod = null;
		
		try{
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT * FROM DELIVERY_METHODS WHERE DELIVERY_METHOD_ID = " + id);
			
			resultSet.next();
			deliveryMethod = new DeliveryMethod(
					resultSet.getString(1),
					resultSet.getString(2)
					); 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		
		return deliveryMethod;
	}
	@Override
	public void deleteById(String id) {
		try{
			
			CallableStatement statement = connection.prepareCall("{call SP_DELETE_DELIVERY_METHOD(?)}");
			statement.setString(1, id);
			statement.execute();
			statement.close();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}
	
        public int nextID(){
            try {
                // Get all the ids
                String query = "select delivery_method_id from delivery_methods";
                Statement stmnt = connection.createStatement();
                stmnt.execute(query);
                
                // Collect the ids
                ArrayList<Integer> ids = new ArrayList<>();
                ResultSet result = stmnt.getResultSet();
                while(result.next()){
                    ids.add(Integer.parseInt(result.getString(1)));
                }
                
                // Return the next id
                return Collections.max(ids) + 1;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
                return -1;
            }
        }
}
