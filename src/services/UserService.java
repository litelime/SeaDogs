package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.User;
import java.util.Collection;
import java.util.Collections;

public class UserService implements Service<User>{
	
	Connection connection;
	
	public UserService(Connection connection) {
		super();
		this.connection = connection;
	}
	public boolean add(User user){
		try{
			
			String userId = user.getUserId();
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			String phone = user.getPhone();
			String email = user.getEmail();
			String password = user.getPassword();
			String userStatusId = user.getUserStatusId();
			
			CallableStatement oCSF = connection.prepareCall("{call sp_insert_user(?,?,?,?,?,?,?)}");
			oCSF.setString(1, userId);
			oCSF.setString(2, firstName);
			oCSF.setString(3, lastName);
			oCSF.setString(4, phone);
			oCSF.setString(5, email);
			oCSF.setString(6, password);
			oCSF.setString(7, userStatusId);
			
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
			CallableStatement stmnt = connection.prepareCall("{call sp_delete_user_by_id(?)}");
                        stmnt.setString(1, id);
                        stmnt.execute();
                        stmnt.close();
                }catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public ArrayList<User> getAll(){

		ArrayList<User> users = new ArrayList<User>();
		
		try{
			Statement usersSt = connection.createStatement();
			ResultSet usersRs = usersSt.executeQuery("Select * from Users");
			
			while(usersRs.next()){
				User user = new User(
						usersRs.getString(1),
						usersRs.getString(2),
						usersRs.getString(3),
						usersRs.getString(4),
						usersRs.getString(5),
						usersRs.getString(6),
						usersRs.getString(7)
						); 
				users.add(user);
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return users;
	}
	public User getById(String id){
		User user = null;
		
		try{
			Statement usersSt = connection.createStatement();
			ResultSet usersRs = usersSt.executeQuery("Select * from Users where user_id = " + id);
			
			usersRs.next();
			user = new User(
					usersRs.getString(1),
					usersRs.getString(2),
					usersRs.getString(3),
					usersRs.getString(4),
					usersRs.getString(5),
					usersRs.getString(6),
					usersRs.getString(7)
					); 
		}catch(Exception e){
                        System.out.println("HERE");
			System.out.println(e.getMessage());
		}	
		
		return user;
	}
	
	public User getByEmail(String email){
		User user = null;
		
		try{
			// If the email isn't associated with anyone
                        if(!emailExists(email)){
                            return null;
                        }
			
                        PreparedStatement pstmt = connection.prepareStatement("select * from users "
					+ "where email = ?"); 
			pstmt.setString(1,email);
						
			ResultSet usersRs = pstmt.executeQuery();
			
			usersRs.next();
			user = new User(
					usersRs.getString(1),
					usersRs.getString(2),
					usersRs.getString(3),
					usersRs.getString(4),
					usersRs.getString(5),
					usersRs.getString(6),
					usersRs.getString(7)
					); 
		}catch(Exception e){
                    e.printStackTrace();
                    System.out.println(e.getMessage() + "????");
		}	
		
		return user;
	}
	public void update(User user){
		try{
			String userId = user.getUserId();
			String firstName = user.getFirstName();
			String lastName = user.getLastName();
			String phone = user.getPhone();
			String email = user.getEmail();
			String password = user.getPassword();
			String userStatusId = user.getUserStatusId();
			
			CallableStatement oCSF = connection.prepareCall("{call sp_update_user(?,?,?,?,?,?,?)}");
			oCSF.setString(1, userId);
			oCSF.setString(2, firstName);
			oCSF.setString(3, lastName);
			oCSF.setString(4, phone);
			oCSF.setString(5, email);
			oCSF.setString(6, password);
			oCSF.setString(7, userStatusId);
			oCSF.execute();
			oCSF.close();
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}	
	}
        
        // Check to see if a userID exists
	public boolean emailExists(String email){
            try {
                // Ask for all the user IDs
                String query = "select email from users";
                Statement stmnt = connection.createStatement();
                stmnt.execute(query);
                
                // Get the emails
                ArrayList<String> emails = new ArrayList<>();
                ResultSet results = stmnt.getResultSet();
                while(results.next()){
                    emails.add(results.getString(1));
                }
                
                // See if the email exists
                return emails.contains(email);
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
                return false;
            }
        }
        
        // Generate a new user id
        public int newUserId(){
            try {
                // Ask for all the ids
                String query = "select user_id from users";
                Statement stmnt = connection.createStatement();
                stmnt.execute(query);
                
                // Collect the ids
                ArrayList<Integer> ids = new ArrayList<Integer>();
                ResultSet results = stmnt.getResultSet();
                while(results.next()){
                    ids.add(Integer.parseInt(results.getString(1)));
                }
                
                // Generate a new id
                if(ids.isEmpty())
                    return 0;
                return Collections.max(ids) + 1;
            } catch (NumberFormatException | SQLException e) {
                System.out.println(e.getMessage());
                System.exit(1);
                return -1;
            }
        }
}
