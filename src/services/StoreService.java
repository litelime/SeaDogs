package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Store;
import java.time.LocalTime;

public class StoreService implements Service<Store>{
	
	Connection connection;
	
	public StoreService() {
		super();
	}
	public StoreService(Connection connection) {
		super();
		this.connection = connection;
	}
	public boolean add(Store store){
		try{
			String storeId = store.getStoreId();
			String locationId = store.getLocationId();
			String storeName = store.getStoreName();
			String phoneNumber = store.getPhoneNumber();
			String managerId = store.getManagerId();
			LocalTime openTime = store.getOpenTime();
			LocalTime closeTime = store.getCloseTime();
			
			CallableStatement oCSF = connection.prepareCall("{?=call sp_insert_store(?,?,?,?,?,?,?)}");
			oCSF.setString(2, storeId);
			oCSF.setString(3, locationId);
			oCSF.setString(4, storeName);
			oCSF.setString(5, phoneNumber);
			oCSF.setString(6, managerId);
			oCSF.setInt(7, openTime.toSecondOfDay());
			oCSF.setInt(8, openTime.toSecondOfDay());
			oCSF.execute();
			oCSF.close();
			return true;
		}catch(SQLException e){
			System.out.println(e.getMessage());
			return false;
		}	
	}
	public void deleteById(String id){
		try{
			Statement storesSt = connection.createStatement();
			storesSt.executeQuery("Delete from stores where store_id = "+id);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public ArrayList<Store> getAll(){

		ArrayList<Store> stores = new ArrayList<Store>();
		
		try{
			Statement storesSt = connection.createStatement();
			ResultSet storesRs = storesSt.executeQuery("Select * from Stores");
			
			while(storesRs.next()){
				Store store = new Store(
						storesRs.getString(1),
						storesRs.getString(2),
						storesRs.getString(3),
						storesRs.getString(4),
						storesRs.getString(5),
						LocalTime.ofSecondOfDay(storesRs.getInt(6)),
						LocalTime.ofSecondOfDay(storesRs.getInt(7))
						); 
				stores.add(store);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return stores;
	}
	public Store getById(String id){
		Store store = null;
		
		try{
			Statement storesSt = connection.createStatement();
			ResultSet storesRs = storesSt.executeQuery("Select * from Stores where store_id = " + id);
			
			storesRs.next();
			store = new Store(
					storesRs.getString(1),
					storesRs.getString(2),
					storesRs.getString(3),
					storesRs.getString(4),
					storesRs.getString(5),
					LocalTime.ofSecondOfDay(storesRs.getInt(6)),
					LocalTime.ofSecondOfDay(storesRs.getInt(7))
					);  
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		
		return store;
	}
	public void update(Store store){
		try{
			String storeId = store.getStoreId();
			String locationId = store.getLocationId();
			String storeName = store.getStoreName();
			String phoneNumber = store.getPhoneNumber();
			String managerId = store.getManagerId();
			LocalTime openTime = store.getOpenTime();
                        LocalTime closeTime = store.getCloseTime();
			
			CallableStatement oCSF = connection.prepareCall("{?=call sp_insert_store(?,?,?,?,?,?,?)}");
			oCSF.setString(2, storeId);
			oCSF.setString(3, locationId);
			oCSF.setString(4, storeName);
			oCSF.setString(5, phoneNumber);
			oCSF.setString(6, managerId);
			oCSF.setInt(7, openTime.toSecondOfDay());
			oCSF.setInt(8, closeTime.toSecondOfDay());
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
	}
	
}
