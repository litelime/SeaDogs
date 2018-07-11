package cli;

import static cli.Tiger.editString;
import static cli.Tiger.firstScreen;
import static cli.Tiger.getAnInt;

import domain.Card;
import domain.Location;
import domain.Menu;
import domain.Special;
import domain.User;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import domain.UserStatus;
import java.sql.Date;
import services.CardService;
import services.DeliveryMethod;
import services.DeliveryMethodService;
import services.LocationService;
import services.MenuServices;
import services.SpecialServices;
import services.UserService;
import services.UserStatusService;

public class AdminAndManager {

	static Connection con;
        private User user;
        private static String manager = "3";
        private static String admin = "4";

	public AdminAndManager(Connection con){
		AdminAndManager.con = con;
                user = null;
	}

	public void adminScreen(){
            // Wait for login
            Scanner sc = new Scanner(System.in);
            UserService userHelper = new UserService(con);
            while(user == null){
                // Get email
                String email = "";
                do{
                    System.out.println("Enter your email:");
                    email = sc.nextLine();
                } while(email.length() == 0);

                // Get password
                String password = "";
                do{
                    System.out.println("Enter your password");
                    password = sc.nextLine();
                } while(password.length() == 0);

                // Check credentials
                boolean emailExists = (userHelper.getByEmail(email) != null);
                boolean passwordMatch = emailExists &&
                                        (userHelper.getByEmail(email).getPassword().equals(password));
                boolean isAdmin = passwordMatch &&
                        (userHelper.getByEmail(email).getUserStatusId().equals(manager) ||
                         userHelper.getByEmail(email).getUserStatusId().endsWith(admin));

                // Notify user of reason for failed login
                if(!emailExists || !passwordMatch){
                    System.out.println("Incorrect credentials. Please try again.");
                } else if(!isAdmin){
                    System.out.println("You are not an admin.");
                    firstScreen();
                }

                // Allow login
                if(isAdmin){
                    user = userHelper.getByEmail(email);
                }
            }

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
		options.add("Alter Users");
		options.add("Alter User Statuses");
		ServiceWrapper.printOptions(options);
	    int input = sc.nextInt();
	    int option = 0;
	    switch(input){
	    	case 1:
	    		{
	    			option = optionsScreen("Card");
	    			switch(option){
	    				case 1:
	    					alterCardScreen();
                                                break;
	    				case 2:
	    					addCardScreen();
                                                break;
	    				case 3:
	    					deleteCardScreen();
                                                break;
	    				case 4:
	    					adminScreen();
                                                break;
	    			}
	    			break;
	    		}
	    	case 3:
	    		option = optionsScreen("Delivery Method");
                        // Goes to item menu. Fix that later
                        switch(option){
                            case 1:
                                editDeliveryMethod();
                                break;
                            case 2:
                                addDeliveryMethod();
                                break;
                            case 3:
                                deleteDeliveryMethod();
                                break;
                            case 5:
                                adminScreen();
                                break;
                            default:
                                System.exit(1);
                        }
                        break;
	    	case 4:
	    		optionsScreen("Delivery Statuse");
                        break;
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
	    		optionsScreen("Item Type");
                        break;
	    	case 7:
                        option = optionsScreen("Locations");
                        switch(option){
                            case 1:
                                editLocations();
                                break;
                            case 2:
                                addLocation();
                                break;
                            case 3:
                                deleteLocation();
                                break;
                            case 4:
                                adminScreen();
                                break;
                            default:
                                System.exit(1);
                        }
                        break;
	    	case 8:
	    		optionsScreen("Order");
                        break;
	    	case 9:
	    		optionsScreen("Order Item");
                        break;
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
                        break;

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
                        break;
                case 12:
	    		firstScreen();
                        break;
	    	case 13:
	    		System.exit(0);
                default:
                    adminScreen();
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

        /*
        **************************
        * Delivery Method Status *
        **************************
        */

        public void editDeliveryMethod(){
            // Ask for a delivery method to delete
            Scanner kb = new Scanner(System.in);
            DeliveryMethodService deliveryHelper = new DeliveryMethodService(con);
            ArrayList<DeliveryMethod> methods = deliveryHelper.getAll();
            for(int i = 0; i < methods.size(); i++){
                System.out.println((i + 1) + ". " + methods.get(i).getDelivery_method());
            }
            System.out.println("Chose a delivery method to edit:");
            DeliveryMethod choice = methods.get(Integer.parseInt(kb.nextLine()) - 1);

            // Ask for the new value
            System.out.println("What would you like to change it to?");
            choice.setDelivery_method(kb.nextLine());

            // Edit the method
            deliveryHelper.update(choice);
            System.out.println("Alteration successful.");
        }

        public void deleteDeliveryMethod(){
            // Ask for a delivery method to delete
            Scanner kb = new Scanner(System.in);
            DeliveryMethodService deliveryHelper = new DeliveryMethodService(con);
            ArrayList<DeliveryMethod> methods = deliveryHelper.getAll();
            for(int i = 0; i < methods.size(); i++){
                System.out.println((i + 1) + ". " + methods.get(i).getDelivery_method());
            }
            System.out.println("Chose a delivery method to delete:");
            DeliveryMethod choice = methods.get(Integer.parseInt(kb.nextLine()) - 1);

            // Delete the method
            deliveryHelper.deleteById(choice.getDelivery_method_id());
            System.out.println(choice.getDelivery_method() + " deleted");
        }

        public void addDeliveryMethod(){
            // Get the delivery method
            Scanner kb = new Scanner(System.in);
            DeliveryMethodService deliveryHelper = new DeliveryMethodService(con);
            System.out.println("Enter a delivery method:");
            String newMethod = kb.nextLine();
            String newID = deliveryHelper.nextID() + "";

            // Build the delivery method and add it
            DeliveryMethod method = new DeliveryMethod(newID, newMethod);
            deliveryHelper.add(method);
            System.out.println(newMethod + " added.");
        }

        /*
        ***************
        * User Status *
        ***************
        */

        public void alterStatus(){
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

        public void addStatus(){
            // Ask for a new status
            UserStatusService statusHelper = new UserStatusService(con);
            Scanner kb = new Scanner(System.in);
            System.out.println("What status would you like to add?");
            String newID = (statusHelper.newStatusID() + "").toLowerCase();
            String newStatus = kb.nextLine();

            // Make the status
            UserStatus toInsert = new UserStatus(newID, newStatus);
            statusHelper.add(toInsert);
            System.out.println(newStatus + " added");
            adminScreen();
        }

        public void deleteStatus(){
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
    public void alterUserScreen() {
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
        if (!exists) {
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
        if (changeField.equals("Go back")) {
            adminScreen();
            return;
        }

        // Ask for the value to change it to
        System.out.println("What would you like to change " + changeField + " to?");
        String changeValue = kb.nextLine();

        // Check the user input
        // No value given
        if (changeValue.isEmpty()) {
            if (cantBeNull.contains(changeField)) { // empty and shouldnt be
                System.out.println(changeField + " cannot be empty");
                alterUserScreen();
                return;
            } else { // empty and needs to be changed to null
                changeValue = "NULL";
            }
            // Email alread in use
        } else if (changeField.equals("Email") && userHelper.emailExists(changeValue)) {
            System.out.println("Email already in use");
            adminScreen();
        }

        // Update the user
        if (changeField.equals("First Name")) {
            toAlter.setFirstName(changeValue);
        } else if (changeField.equals("Last Name")) {
            toAlter.setLastName(changeValue);
        } else if (changeField.equals("Phone Number")) {
            toAlter.setPhone(changeValue);
        } else if (changeField.equals("Email")) {
            toAlter.setEmail(changeValue);
        } else if (changeField.equals("Password")) {
            toAlter.setPassword(changeValue);
        } else if (changeField.equals("User ID Status")) {
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

    public static void addCardScreen() {
        CardService cs = new CardService(con);
        UserService us = new UserService(con);

        System.out.println("Add a Credit Card");
        Scanner sc = new Scanner(System.in);

        System.out.println("\nWhat user does this card belong to");
        int count = 0;
        ArrayList<User> users = us.getAll();
        for (User user : users) {
            count++;
            System.out.println(count + ". " + user.getFirstName() + " " + user.getLastName());
        }
        int userChoice = Tiger.getAnInt();
        userChoice--;
        String userId = users.get(userChoice).getUserId();

        System.out.println("\nEnter Card number: ");
        String cardNumber = sc.next();

        Date expiryDate = Tiger.editDate();

        System.out.println("Enter Security code: ");
        String securityCode = sc.next();

        Card c = new Card(cs.getNextCardId(), userId, cardNumber, expiryDate, securityCode);

        cs.add(c);
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }

    public static void deleteCardScreen() {
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cl = cs.getAll();
        int count = 1;
        for (Card c : cl) {
            System.out.println(count + ": " + c.getCardNumber());
            count++;
        }
        System.out.println("Select card you'd like to delete");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        cs.deleteById(cl.get(input - 1).getCardId());
        System.out.println("Deleted Card");

    }

    public static void alterCardScreen() {
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cl = cs.getAll();
        int count = 1;
        for (Card c : cl) {
            System.out.println(count + ": " + c.getCardNumber());
            count++;
        }
        System.out.println("Enter the number of the card you'd like to alter");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();

        UserService us = new UserService(con);
        String cardId = cl.get(input - 1).getCardId();

        System.out.println("\nWhat user shoild this card belong to");
        int userCount = 0;
        ArrayList<User> users = us.getAll();
        for (User user : users) {
            userCount++;
            System.out.println(userCount + ". " + user.getFirstName() + " " + user.getLastName());
        }
        int userChoice = Tiger.getAnInt();
        userChoice--;
        String userId = users.get(userChoice).getUserId();

        System.out.println("Enter Card number: ");
        String cardNumber = sc.next();

        Date expiryDate = Tiger.editDate();

        System.out.println("Enter Security code: ");
        String securityCode = sc.next();

        Card c = new Card(cardId, userId, cardNumber, expiryDate, securityCode);

        cs.update(c);
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();
    }

    public static void addItemScreen() {
        System.out.println("Add an item");
        Scanner sc = new Scanner(System.in);
        MenuServices menServ = new MenuServices(con);

        System.out.println("\nEnter item name: ");
        String name = sc.nextLine();
        System.out.println("\nEnter vegeterian (y or n): ");
        String vege = sc.next();
        char vegetarian = vege.charAt(0);
        System.out.println("\nEnter a description: ");
        sc.nextLine();
        String description = sc.nextLine();
        System.out.println("\nEnter type number id: ");
        String type = sc.next();
        System.out.println("\nEnter meal time: ");
        String slot_ID = sc.next();
        System.out.println("\nEnter photo link: ");
        String photo = sc.next();
        System.out.println("\nEnter a price: ");
        float price = sc.nextFloat();

        Menu men = new Menu(""+menServ.getNextItemId(), name, vegetarian, type, description, slot_ID, photo, price);
        menServ.add(men);
        System.out.println("\n" + name + " added to database\n");
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();

    }

    public static void deleteItemScreen() {
        System.out.println("Choose an item to delete");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if (input == menus.size() + 1) {
            return;
        }
        if (input == menus.size() + 2) {
            System.exit(0);
        }
        MenuServices menServ = new MenuServices(con);

        menServ.deleteById(menus.get(input - 1).getId());
        System.out.println("Deleted " + menus.get(input - 1).getName());
    }

    public static void alterItemScreen() {
        System.out.println("Choose an item to alter");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        ServiceWrapper.printMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if(input == (menus.size())+1){
            alterItemScreen();
        }
        Menu men = menus.get(input - 1);
        MenuServices menServ = new MenuServices(con);
        System.out.println("Enter item name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.println("Enter vegeterian (y or n): ");
        String vege = sc.next();
        char vegetarian = vege.charAt(0);
        System.out.println("Enter a description: ");
        sc.nextLine();
        String description = sc.nextLine();
        System.out.println("Enter type number id: ");
        String type = sc.next();
        System.out.println("Enter meal time: ");
        String slot_ID = sc.next();
        System.out.println("Enter photo link: ");
        String photo = sc.next();
        System.out.println("Enter a price: ");
        float price = sc.nextFloat();
        String id = men.getId();
        Menu menUp = new Menu(id, name, vegetarian, type, description, slot_ID, photo, price);
        menServ.update(menUp);
        System.out.println("Updated " + name);
    }

    public static void addUserScreen() {
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
        for (int i = 0; i < statuses.size(); i++) {
            System.out.println((i + 1) + ". " + statuses.get(i).getUserStatus());
        }
        String userStatusId = statuses.get(Integer.parseInt(sc.nextLine())).getUserStatusId();

        // Make sure email and password aren't null
        if (email.equals("null")) {
            System.out.println("Please try again and enter an email address");
            addUserScreen();
        } else if (password.equals("null")) {
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
        int count = 1;
        for (User u : uArr) {
            System.out.println(count + " " + u.getFirstName() + " " + u.getLastName());
            count++;
        }

        // Ask for the user to delete
        System.out.println("Select user you'd like to delete");
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        User toDelete = uArr.get(input - 1);

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

    private static String emptyToNull(String field) {
        if (field.length() == 0) {
            return "null";
        } else {
            return field;
        }

    }

    private void alterComboScreen() {
        SpecialServices SS = new SpecialServices(con);

        System.out.println("\nWhat combo do you want to alter");
        int specialCount = 0;
        ArrayList<Special> specials = SS.getAll();
        for (Special special : specials) {
            specialCount++;
            System.out.println(specialCount + ". " + special.getItem_ID() + " " + special.getDiscoutPercentage() + "%");
        }
        int specialChoice = Tiger.getAnInt();
        specialChoice--;
        String specialId = specials.get(specialChoice).getItem_ID();

        System.out.println("Enter new percentage as an integer");
        int newDiscount = Tiger.getAnInt();
        SS.update(new Special(specialId, newDiscount));
        adminScreen();
    }

    private void addComboScreen() {
        SpecialServices SS = new SpecialServices(con);

        System.out.println("Enter the discount percentage as integer");
        int discount = Tiger.getAnInt();
        SS.getAll();
        String specialId =""+ SS.newSpecialId();
        SS.add(new Special(specialId, discount));
        adminScreen();
    }

    private void deleteComboScreen() {
        SpecialServices SS = new SpecialServices(con);
        System.out.println("\nWhat combo do you want to delete?");

        int specialCount = 0;
        ArrayList<Special> specials = SS.getAll();
        for (Special special : specials) {
            specialCount++;
            System.out.println(specialCount + ". " + special.getItem_ID() + " " + special.getDiscoutPercentage() + "%");
        }
        int specialChoice = Tiger.getAnInt();
        specialChoice--;
        String specialId = specials.get(specialChoice).getItem_ID();
        SS.deleteById(specialId);
        adminScreen();
    }

    private void editLocations() {
        Scanner sc = new Scanner(System.in);
        LocationService ls = new LocationService(con);
        ArrayList<Location> locs = ls.getAll();
        for(int i = 0; i < locs.size(); i++){
            System.out.println((i + 1) + ". " + locs.get(i).getAddress());
        }
        int input = Integer.parseInt(sc.nextLine());
        if(input <= locs.size() && input > 0){
            editALoc(locs.get(input -1));
            System.out.println("Location updated");
        }
    }
    
    private static void editALoc(Location l) {
        System.out.printf("\n*** Edit Location: ***\n");
        System.out.println("1. Edit Street: " + l.getStreet());
        System.out.println("2. Edit City:  " + l.getCity());
        System.out.println("3. Edit State: " + l.getState());
        System.out.println("4. Edit Country: " + l.getCountry());
        System.out.println("5. Edit Zip: " + l.getZip());
        System.out.println("6. Back");

        int input = getAnInt();

        switch (input) {
            case 1:
                String street = editString();
                l.setStreet(street);
                System.out.println("Street changed to " + street);
                editALoc(l);
                break;
            case 2:
                String city = editString();
                l.setCity(city);
                System.out.println("City changed to " + city);
                editALoc(l);
                break;
            case 3:
                String state = editString();
                l.setState(state);
                System.out.println("State changed to " + state);
                editALoc(l);
                break;
            case 4:
                String country = editString();
                l.setCountry(country);
                System.out.println("Country changed to " + country);
                editALoc(l);
                break;
            case 5:
                String zip = editString();
                l.setZip(zip);
                System.out.println("Zip changed to " + zip);
                editALoc(l);
                break;
            default:
                break;
        }
        LocationService ls = new LocationService(con);
        ls.update(l);
    }

    private void addLocation() {
        // Get the delivery method
        Scanner sc = new Scanner(System.in);
        LocationService locServe = new LocationService(con);
        Location newLoc = locPrompt(sc);
        String newID = locServe.getNextLocId();
        newLoc.setLocationId(newID);
        locServe.add(newLoc);
        System.out.println("Location at " +newLoc.getCity() +", " + newLoc.getState() +" added.");
    }
    private Location locPrompt(Scanner sc){
        System.out.println("Enter street");
        String street = sc.nextLine();
        System.out.println("Enter city");
        String city = sc.nextLine();
        System.out.println("Enter state");
        String state = sc.nextLine();
        System.out.println("Enter country");
        String country = sc.nextLine();
        System.out.println("Enter Zip");
        String zip = sc.nextLine();
        Location newLoc = new Location();
        newLoc.setStreet(street);
        newLoc.setCity(city);
        newLoc.setState(state);
        newLoc.setCountry(country);
        newLoc.setZip(zip);
        return newLoc;
    }

    private void deleteLocation() {
        // Ask for a location to delete
        Scanner sc = new Scanner(System.in);
        LocationService ls = new LocationService(con);
        ArrayList<Location> locs = ls.getAll();
        int total = locs.size();
        System.out.println("");
        if(total < 10){
            for(int i = 0; i < total; i++ ){
                System.out.println(i+1 + ". " + locs.get(i).getStreet());
            }
                System.out.println(total+1 + ". Go Back");
        }else{//Make a nicer format for a longer list. 4 To a line.
            for(int i= 0; i < total; i++){
                System.out.printf("%s. %-25s",(i+1), locs.get(i).getStreet());
                if((i+1)%4 ==0 ){
                    System.out.print("\n");
                }
            }
            System.out.println("");
        }
        int input = sc.nextInt();
        if(input <= total){
            ls.deleteById(locs.get(input-1).getLocationId());
            System.out.println("Location deleted");
        }
                adminScreen();     
    }
//        for(int i = 0; i < methods.size(); i++){
//            System.out.println((i + 1) + ". " + methods.get(i).getDelivery_method());
//        }
//        System.out.println("Chose a delivery method to delete:");
//        DeliveryMethod choice = methods.get(Integer.parseInt(kb.nextLine()) - 1);
//
//        // Delete the method
//        deliveryHelper.deleteById(choice.getDelivery_method_id());
//        System.out.println(choice.getDelivery_method() + " deleted");

}

