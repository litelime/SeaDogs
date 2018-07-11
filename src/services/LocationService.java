package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Location;
import java.util.Collections;
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
						locationsRs.getString(3),
						locationsRs.getString(4),
						locationsRs.getString(5),
                                                locationsRs.getString(6),
                                                locationsRs.getString(7)
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
					locationsRs.getString(3),
					locationsRs.getString(4),
					locationsRs.getString(5),
					locationsRs.getString(6),
                                        locationsRs.getString(7)
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
						locationsRs.getString(3),
						locationsRs.getString(4),
						locationsRs.getString(5),
						locationsRs.getString(6),
                                                locationsRs.getString(7)
						); 
				locations.add(location);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return locations;
	}
        public String getNextLocId(){
            try {
                // Ask for all the ids
                String query = "select location_id from locations";
                Statement stmnt = connection.createStatement();
                stmnt.execute(query);
                
                // Collect the ids
                ArrayList<Integer> ids = new ArrayList<>();
                ResultSet results = stmnt.getResultSet();
                while(results.next()){
                    ids.add(Integer.parseInt(results.getString(1)));
                }
                
                // Generate a new id
                if(ids.isEmpty())
                    return "0";
                return Integer.toString(Collections.max(ids) + 1);
            } catch (NumberFormatException | SQLException e) {
                System.out.println(e.getMessage());
                System.exit(1);
                return "-1";
            }
        }
	
        /*
            If a location is managed by manager_id, then the location's user_id
            (the field for storing the manager ID) is set to null. This is was
            writtten to reflect changes in the 
        */
        public void removeManager(String managerID){
            try {
                CallableStatement stmnt = connection.prepareCall("call sp_null_store_manager(?)");
                stmnt.setString(1, managerID);
                stmnt.execute();
                stmnt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
