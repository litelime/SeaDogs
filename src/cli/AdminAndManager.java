package cli;

import static cli.Tiger.firstScreen;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domain.Card;
import domain.Location;
import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import domain.UserStatus;
import domain.itemType;
import services.CardService;
import services.LocationService;
import services.MenuServices;
import services.OrderService;
import services.StoreService;
import services.UserService;
import services.UserStatusService;
import services.itemTypeServices;

public class AdminAndManager {
	
	static Connection con;
	
	public AdminAndManager(Connection con){
		AdminAndManager.con = con;
	}
	
	public void adminScreen(){
		ArrayList<String> options = new ArrayList<String>();
		System.out.println("Admin View");
		options.add("Alter Cards");
		options.add("Alter Combos");
		options.add("Alter Delivery Methods");
		options.add("Alter Delivery Statuses");
		options.add("Alter Items");
		options.add("Alter Item Types");
		options.add("Alter Locations");
		options.add("Alter Orders");
		options.add("Alter Order_items"); //Probably don't need this one
		options.add("Alter Users");
		options.add("Alter User Statuses");
		ServiceWrapper.printOptions(options);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    int option = 0;
	    switch(input){
	    	case 1:
	    		{
	    			option = optionsScreen("Card");
	    			switch(option){
	    				case 1:
	    					alterCardScreen();
	    				case 2:
	    					addCardScreen();
	    				case 3:
	    					deleteCardScreen();
	    				case 4: 
	    					adminScreen();
	    			}
	    			break;
	    		}
	    	case 3:
	    		optionsScreen("Delivery Method");
	    	case 4:
	    		optionsScreen("Delivery Statuse");
	    	case 5:
	    	{
	    		option = optionsScreen("Item");
    			switch(option){
    				case 1:
    					alterItemScreen();
    					break;
    				case 2:
    					addItemScreen();
    					break;
    				case 3:
    					deleteItemScreen();
    					break;
    				case 4:
    					adminScreen();
    					break;
    				case 5:
    					System.exit(0);
    			}
    			break;
	    	}
	    	case 6:
	    		option=optionsScreen("Item Type");
                        switch(option){
                            case 1:
                                addItemTypeScreen();
                                break;
                        }
	    	case 7:
	    		optionsScreen("Location");
	    	case 8:
	    		optionsScreen("Order");
	    	case 9:
	    		optionsScreen("Order Item");
	    	case 10:
	    	{
	    		option = optionsScreen("User");
	    		switch(option){
	    			case 1:
                                        alterUserScreen();
                                        break;
	    			case 2:
	    				addUserScreen();
                                        break;
	    			case 3:
	    				deleteUserScreen();
                                        break;
                                case 4:
                                    adminScreen();
                                    break;
	    		}
	    			
	    	}
	    	case 11:
	    		option = optionsScreen("User Statuses");
                        switch(option){
                            case 1:
                                alterStatus();
                                break;
                            case 2:
                                addStatus();
                                break;
                            case 3:
                                System.out.println("Deleting not supported");
                                deleteStatus();
                                break;
                        }
                case 12:
	    		firstScreen();
	    	case 13:
	    		System.exit(0);
	    }
	    
	    adminScreen();
	    
	}
	
        public static int optionsScreen(String thing){
		System.out.println("How would you like to alter " + thing);
		ArrayList<String> options = new ArrayList<String>();
		options.add("Alter");
		options.add("Add");
		options.add("Delete");
		ServiceWrapper.printOptions(options);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
		return input;
	}
        
        void alterStatus(){
            // Ask for which status to alter
            UserStatusService statusHelper = new UserStatusService(con);
            ArrayList<UserStatus> statuses = statusHelper.getAll();
            System.out.println("Select a user status to alter:" + statuses.size());
            for(int i = 0; i < statuses.size(); i++){
                System.out.println((i + 1) + ". " + statuses.get(i));
            }
            Scanner kb = new Scanner(System.in);
            UserStatus toAlter = statuses.get(Integer.parseInt(kb.nextLine()));
            
            // Alter it
            System.out.println("What would you like to change the status to?");
            String newStatus = kb.nextLine();
            toAlter.setUserStatus(newStatus);
            statusHelper.update(toAlter);
            System.out.println("Status altered");
        }
        void addStatus(){
            // Ask for a new status
            UserStatusService statusHelper = new UserStatusService(con);
            Scanner kb = new Scanner(System.in);
            System.out.println("What status would you like to add?");
            String newID = statusHelper.newStatusID() + "";
            String newStatus = kb.nextLine();
            
            // Make the status
            UserStatus toInsert = new UserStatus(newID, newStatus);
            statusHelper.add(toInsert);
            System.out.println(newStatus + " added");
            adminScreen();
        }
        
        void deleteStatus(){
            // Ask for the user status to delete
            UserStatusService statusHelper = new UserStatusService(con);
            Scanner kb = new Scanner(System.in);
            ArrayList<UserStatus> statuses = statusHelper.getAll();
            System.out.println("Select a user status to delete");
            for (int i = 0; i < statuses.size(); i++) {
                System.out.println((i + 1) + ". " + statuses.get(i));
            }
            int choiceIndex = Integer.parseInt(kb.nextLine()) - 1;
            UserStatus toDelete = statuses.get(choiceIndex);
            
            // Ask for the replacement status
            System.out.println("Users with status " + toDelete.toString() + " should take on which status?");
            statuses.remove(choiceIndex);
            for (int i = 0; i < statuses.size(); i++) {
                System.out.println((i + 1) + ". " + statuses.get(i));
            }
            UserStatus replacement = statuses.get(Integer.parseInt(kb.nextLine()) - 1);
            
            // Update the users statuses
            statusHelper.replace(toDelete.getUserStatusId(), replacement.getUserStatusId());
            
            // Delete the user status from the table
            statusHelper.deleteById(toDelete.getUserStatusId());
        }
        
        /*
            This will protect against updating to nulls but not updating
            to values that are not valid. I.e. someone can change a user
            status to 999999 which is not a status
        */
	public void alterUserScreen(){
            // Display the users
            UserService userHelper = new UserService(con);
//            for(User user: userHelper.getAll()){
//                System.out.println(user.getEmail);
//            }

            // Ask for the user to modify
            Scanner kb = new Scanner(System.in);
            System.out.println("Enter the email of the user you wish to alter:");
            String alterEmail = kb.nextLine();
            
            // Check to see the user exists
            User toAlter = null;
            boolean exists = userHelper.emailExists(alterEmail);
            if(!exists){
                System.out.println(alterEmail + " not associated with an account.");
                adminScreen();
                return;
            } else {
                toAlter = userHelper.getByEmail(alterEmail);
            }
            
            // Fields that can't be null
            ArrayList<String> cantBeNull = new ArrayList<String>();
            cantBeNull.add("Email");
            cantBeNull.add("Password");
            
            // Ask for a field to change
            String changeField = null;
            ArrayList<String> options = new ArrayList<String>();
            options.add("First Name");
            options.add("Last Name");
            options.add("Phone Number");
            options.add("Email");
            options.add("Password");
            options.add("User ID Status");
            options.add("Go back");
            System.out.println("What field would you like to change?");
            for (int i = 0; i < options.size(); i++) {
                System.out.println(i + ". " + options.get(i));
            }
            changeField = options.get(Integer.parseInt(kb.nextLine()));
            
            // Go back 
            if(changeField.equals("Go back")){
                adminScreen();
                return;
            }
            
            // Ask for the value to change it to
            System.out.println("What would you like to change " + changeField + " to?");
            String changeValue = kb.nextLine();
            
            
            // Check the user input 
            // No value given
            if(changeValue.isEmpty()){
                if(cantBeNull.contains(changeField)){ // empty and shouldnt be
                    System.out.println(changeField + " cannot be empty");
                    alterUserScreen();
                    return;
                } else { // empty and needs to be changed to null
                    changeValue = "NULL";
                }
            // Email alread in use
            } else if(changeField.equals("Email") && userHelper.emailExists(changeValue)){
                System.out.println("Email already in use");
                adminScreen();
            }
           
            // Update the user
            if(changeField.equals("First Name")){
                toAlter.setFirstName(changeValue);
            } else if(changeField.equals("Last Name")){
                toAlter.setLastName(changeValue);
            } else if(changeField.equals("Phone Number")){
                toAlter.setPhone(changeValue);
            } else if(changeField.equals("Email")){
                toAlter.setEmail(changeValue);
            } else if(changeField.equals("Password")){
                toAlter.setPassword(changeValue);
            } else if(changeField.equals("User ID Status")){
                toAlter.setUserStatusId(changeValue);
            } else {
                System.out.println("ERROR: AdminAndManager -> alterUserScreen");
            }
            
            // Update the employee and return to alter screen
            userHelper.update(toAlter);
            System.out.println("Alteration successful");
            adminScreen();
            return;
        }   

	public static void addCardScreen(){
            CardService cs = new CardService(con);
            UserService us = new UserService(con);    
            
		System.out.println("Add a Credit Card");
		Scanner sc = new Scanner(System.in);
                            
                System.out.println("\nWhat user does this card belong to");
                int count = 0;
                ArrayList<User> users = us.getAll(); 
                for(User user : users){
                    count++;
                    System.out.println(count+ ". "+user.getFirstName() + " "+user.getLastName());
                }
		int userChoice = Tiger.getAnInt();
                userChoice--;
                String userId = users.get(userChoice).getUserId();
                
		System.out.println("\nEnter Card number: ");
		String cardNumber= sc.next();

		Date expiryDate= Tiger.editDate();
                
		System.out.println("Enter Security code: ");
		String securityCode= sc.next();
                
		Card c = new Card(cs.getNextCardId(), userId, cardNumber, expiryDate, securityCode);
		
		cs.add(c);
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
	}

	public static void deleteCardScreen(){
		System.out.println("List of cards");
		CardService cs = new CardService(con);
		ArrayList<Card> cl = cs.getAll();
		int count=1;
		for(Card c:cl){
			System.out.println(count + ": " + c.getCardNumber());
			count++;
		}
		System.out.println("Select card you'd like to delete");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    cs.deleteById(cl.get(input-1).getCardId());
	    System.out.println("Deleted Card");
		
	}
	
	public static void alterCardScreen(){
		System.out.println("List of cards");
		CardService cs = new CardService(con);
		ArrayList<Card> cl = cs.getAll();
		int count=1;
		for(Card c:cl){
			System.out.println(count + ": " + c.getCardNumber());
			count++;
		}
		System.out.println("Enter the number of the card you'd like to alter");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    
            UserService us = new UserService(con);
	    String cardId= cl.get(input-1).getCardId();
            
               System.out.println("\nWhat user shoild this card belong to");
                int userCount = 0;
                ArrayList<User> users = us.getAll(); 
                for(User user : users){
                    userCount++;
                    System.out.println(userCount+ ". "+user.getFirstName() + " "+user.getLastName());
                }
		int userChoice = Tiger.getAnInt();
                userChoice--;
                String userId = users.get(userChoice).getUserId();
                
		System.out.println("Enter Card number: ");
		String cardNumber= sc.next();

		Date expiryDate= Tiger.editDate();
                
		System.out.println("Enter Security code: ");
		String securityCode= sc.next();
	    
		Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);
		
		cs.update(c);
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
	}
	
	public static void addItemScreen(){
		System.out.println("Add an item");
		Scanner sc = new Scanner(System.in);
	    System.out.println("\nEnter item id: ");
	    String id= sc.next();
	    System.out.println("\nEnter item name: ");
	    sc.nextLine();
		String name= sc.nextLine();
		System.out.println("\nEnter vegeterian (y or n): ");
		String vege = sc.next();
		char vegetarian = vege.charAt(0);
		System.out.println("\nEnter a description: ");
		sc.nextLine();
		String description= sc.nextLine();
		System.out.println("\nEnter type number id: ");
		String type= sc.next();
		System.out.println("\nEnter meal time: ");
		String slot_ID= sc.next();
		System.out.println("\nEnter photo link: ");
		String photo= sc.next();
		System.out.println("\nEnter a price: ");
		float price= sc.nextFloat();
		
		Menu men = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
		MenuServices menServ = new MenuServices(con);
		menServ.add(men);
		System.out.println("\n" + name + " added to database\n");
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
		
	}
	
	public static void deleteItemScreen(){
		System.out.println("Choose an item to delete");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    if(input == menus.size() + 1)
	    	return;
	    if(input == menus.size()+2)
	    	System.exit(0);
	    MenuServices menServ = new MenuServices(con);
	    
	    menServ.deleteById(menus.get(input-1).getId());
	    System.out.println("Deleted " + menus.get(input-1).getName());
	}
	
	public static void alterItemScreen(){
		System.out.println("Choose an item to alter");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    Menu men = menus.get(input-1);
	    MenuServices menServ = new MenuServices(con);
	    System.out.println("Enter item name: ");
	    sc.nextLine();
		String name= sc.nextLine();
		System.out.println("Enter vegeterian (y or n): ");
		String vege = sc.next();
		char vegetarian = vege.charAt(0);
		System.out.println("Enter a description: ");
		sc.nextLine();
		String description= sc.nextLine();
		System.out.println("Enter type number id: ");
		String type= sc.next();
		System.out.println("Enter meal time: ");
		String slot_ID= sc.next();
		System.out.println("Enter photo link: ");
		String photo= sc.next();
		System.out.println("Enter a price: ");
		float price= sc.nextFloat();
		String id = men.getId();
		Menu menUp = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
	    menServ.update(menUp);
	    System.out.println("Updated " + name);
	}

	public static void addUserScreen(){
                // Get info for new user
                UserService helper = new UserService(con);
		System.out.println("Add a User");
		Scanner sc = new Scanner(System.in);
                String userID = helper.newUserId() + "";
                System.out.println("Enter first name: ");
		String firstName = emptyToNull(sc.nextLine());
		System.out.println("Enter last name: ");
		String lastName = emptyToNull(sc.nextLine());
                System.out.println("Enter phone number:");
                String phone = emptyToNull(sc.nextLine());
		System.out.println("Enter email: ");
		String email = emptyToNull(sc.nextLine());
		System.out.println("Enter password: ");
		String password = emptyToNull(sc.nextLine());
                
                // Let the user select from a list of status ids
		System.out.println("Select a user status: ");
		UserStatusService statusHelper = new UserStatusService(con);
                ArrayList<UserStatus> statuses = statusHelper.getAll();
                for(int i = 0; i < statuses.size(); i++){
                    System.out.println((i + 1) + ". " + statuses.get(i).getUserStatus());
                }
                String userStatusId = statuses.get(Integer.parseInt(sc.nextLine())).getUserStatusId();
                
                
                // Make sure email and password aren't null
                if(email.equals("null")){
                    System.out.println("Please try again and enter an email address");
                    addUserScreen();
                } else if(password.equals("null")){
                    System.out.println("Please try again and enter a password");
                    addUserScreen();
                }
                
                // Create and add the user
                User u = new User(userID, firstName, lastName, phone, email, password, userStatusId);
                helper.add(u);
		
                // Not sure why this is here
		AdminAndManager aam = new AdminAndManager(con);
		aam.adminScreen();
	}
	
	public static void deleteUserScreen() {
            // List existing users
            System.out.println("List of users");
            UserService us = new UserService(con);
            ArrayList<User> uArr = us.getAll();
            int count=1;
            for(User u:uArr){
		System.out.println(count + " " + u.getFirstName() + " " + u.getLastName());
		count++;
            }
	
            // Ask for the user to delete
            System.out.println("Select user you'd like to delete");
            Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
            User  toDelete = uArr.get(input-1);
            
            /*
                If the employee deleted was a manager, then the location they
                were managing will have no manager after the employee is
                deleted. This loss of manager should be reflected in the table
                by changing the user_id of the location to null.
            */
            // Update locations table
            System.out.println("Deleting: " + toDelete);
            //LocationService locationHelper = new LocationService(con);
            //locationHelper.removeManager(toDelete.getUserId());
            
            /*
                Tables who have records that reference this user will also be
                deleted. For example, all of the 
            */
            // Delete the user
            System.out.println("Deleting user...");
	    us.deleteById(toDelete.getUserId());
	    System.out.println(toDelete.getFirstName() + " has been deleted");	
        }

        
        private static String emptyToNull(String field){
            if(field.length() == 0){
                return "null";
            } else {
                return field;
            }
        }
        
        public static void addItemTypeScreen(){
            Scanner sc=new Scanner(System.in);
            System.out.println("Please enter the item type ID to be inserted");
            String TypeId=sc.next();
            System.out.println("Please enter the item type");
            String itmTyp=sc.next();
            itemType it=new itemType(TypeId, itmTyp);
            itemTypeServices it1 = new itemTypeServices(con);
            it1.add(it);
            System.out.println("\n" + TypeId + " added to database\n");
            AdminAndManager aam = new AdminAndManager(con);
            aam.adminScreen();
            
            
            
        }
}
