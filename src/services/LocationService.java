package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Location;
import java.util.Random;

public class LocationService implements Service<Location>{
	
	Connection connection;
	
	public LocationService() {
		super();
	}
	public LocationService(Connection connection) {
		super();
		this.connection = connection;
	}
	public boolean add(Location location){
		try{
			String locationId = location.getLocationId();
                        String userId = location.getUserId();
			String street = location.getStreet();
			String city = location.getCity();
			String state = location.getState();
			String country = location.getCountry();
			String zip = location.getZip();
			
			CallableStatement oCSF = connection.prepareCall("{call sp_insert_location(?,?,?,?,?,?,?)}");
			oCSF.setString(1, locationId);
                        oCSF.setString(2, userId);
			oCSF.setString(3, street);
			oCSF.setString(4, city);
			oCSF.setString(5, state);
			oCSF.setString(6, country);
			oCSF.setString(7, zip);
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
			CallableStatement locationsSt = connection.prepareCall("{call SP_DELETE_LOCATION_BY_ID(?) }");
                        locationsSt.setString(1, id);
                        locationsSt.execute();
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public ArrayList<Location> getAll(){

		ArrayList<Location> locations = new ArrayList<Location>();
		
		try{
			Statement locationsSt = connection.createStatement();
			ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations");
			
			while(locationsRs.next()){
                                //loc, st, ci, co, st, zip, uid
				Location location = new Location(
						locationsRs.getString(1),
						locationsRs.getString(2),
						locationsRs.getString(4),
						locationsRs.getString(5),
						locationsRs.getString(6),
                                                locationsRs.getString(7),
                                                locationsRs.getString(8)
						); 
				locations.add(location);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return locations;
	}
	public Location getById(String id){
		Location location = null;
		
		try{
			Statement locationsSt = connection.createStatement();
			ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations where location_id = " + id);
			
			locationsRs.next();
			location = new Location(
					locationsRs.getString(1),
					locationsRs.getString(2),
					locationsRs.getString(4),
					locationsRs.getString(5),
					locationsRs.getString(6),
					locationsRs.getString(7),
                                        locationsRs.getString(8)
					); 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		
		return location;
	}
	public void update(Location location){
		try{
			String locationId = location.getLocationId();
                        String userId = location.getUserId();
			String street = location.getStreet();
			String city = location.getCity();
			String state = location.getState();
			String country = location.getCountry();
			String zip = location.getZip();
			
			CallableStatement oCSF = connection.prepareCall("{call sp_update_location(?,?,?,?,?,?,?)}");
			oCSF.setString(1, locationId);
                        oCSF.setString(2, userId);
			oCSF.setString(3, street);
			oCSF.setString(4, city);
			oCSF.setString(5, state);
			oCSF.setString(6, country);
			oCSF.setString(7, zip);
                        
                        oCSF.execute();
                        oCSF.close();
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
	}
	
	public ArrayList<Location> getUserLocations(String userId){

		ArrayList<Location> locations = new ArrayList<Location>();
		
		try{
			Statement locationsSt = connection.createStatement();
			ResultSet locationsRs = locationsSt.executeQuery("Select * from Locations where user_id = '" + userId + "'");
			
			while(locationsRs.next()){
				Location location = new Location(
						locationsRs.getString(1),
						locationsRs.getString(2),
						locationsRs.getString(4),
						locationsRs.getString(5),
						locationsRs.getString(6),
						locationsRs.getString(7),
                                                locationsRs.getString(8)
						); 
				locations.add(location);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return locations;
	}
        public String getNextLocId(){
            String id = "";
            try{
		Statement locSt = connection.createStatement();
                boolean idNotFound = true;
                Random val = new Random();
                while(idNotFound){
                    //To try and ensure uniqueness...
                    id = Integer.toString(val.nextInt(999999));
                    ResultSet locRs = locSt.executeQuery("Select location_id from Locations WHERE location_id =" + id);
                    if(!locRs.next()) idNotFound = false;
                }
            }catch(SQLException e){
			System.out.println(e.getMessage());
		}
            return id;
        }
	
}
