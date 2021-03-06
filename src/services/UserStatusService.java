package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.UserStatus;
import java.util.Collection;
import java.util.Collections;

public class UserStatusService implements Service<UserStatus>{

	
	Connection connection;
	
	public UserStatusService() {
		super();
	}
	public UserStatusService(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public boolean add(UserStatus userStatus) {
		try{
			String userStatusId = userStatus.getUserStatusId();
			String userStatusName = userStatus.getUserStatus();
                                                
			CallableStatement oCSF = connection.prepareCall("{call sp_insert_user_status(?,?)}");
                        oCSF.setString(1, userStatusId);
			oCSF.setString(2, userStatusName);
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
			Statement usersSt = connection.createStatement();
			usersSt.executeQuery("Delete from user_statuses where user_status_id = "+id);
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public UserStatus getById(String id){
		UserStatus userStatus = null;
		
		try{
			Statement usersSt = connection.createStatement();
			ResultSet usersRs = usersSt.executeQuery("Select * from Users where user_id = " + id);
			
			usersRs.next();
			userStatus = new UserStatus(
					usersRs.getString(1),
					usersRs.getString(2)
					); 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}	
		
		return userStatus;
	}
	public ArrayList<UserStatus> getAll(){
		ArrayList<UserStatus> userStatuses = new ArrayList<UserStatus>();
		
		try{
			Statement userStatusesSt = connection.createStatement();
                        userStatusesSt.execute("select * from user_statuses");
			ResultSet userStatusesRs = userStatusesSt.getResultSet();
			
			while(userStatusesRs.next()){
				UserStatus userStatus = new UserStatus(
						userStatusesRs.getString(1),
						userStatusesRs.getString(2)
						); 
				userStatuses.add(userStatus);
			}
		}catch(Exception e){
			System.out.println("UserStatusService Error: " + e.getMessage());
		}
		return userStatuses;
	}
	public void update(UserStatus userStatus){
		try{
			String userStatusId = userStatus.getUserStatusId();
			String userStatusName = userStatus.getUserStatus();
			
			CallableStatement oCSF = connection.prepareCall("{call sp_update_user_status(?,?)}");
			oCSF.setString(1,userStatusId);
			oCSF.setString(2, userStatusName);
			oCSF.execute();
			oCSF.close();
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
	}
        
        public int newStatusID(){
            try {
                // Ask for the user_status_ids
                String query = "select user_status_id from user_statuses";
                Statement stmnt = connection.createStatement();
                stmnt.execute(query);
                
                // Collect the user_status_id
                ArrayList<Integer> userStatusIDs = new ArrayList<>();
                ResultSet results = stmnt.getResultSet();
                while(results.next()){
                    userStatusIDs.add(Integer.parseInt(results.getString(1)));
                }
                
                // Make the next newStatusID
                return Collections.max(userStatusIDs) + 1;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
                return -1;
            }
        }        
 
        public void replace(String oldOne, String newOne){
            try {
                CallableStatement stmnt = connection.prepareCall("{call sp_swap_status(?,?)}");
                stmnt.setString(1, oldOne);
                stmnt.setString(2, newOne);
                stmnt.execute();
                stmnt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}