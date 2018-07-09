package cli;

import Exceptions.IllegalExpiryDateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import domain.Menu;
import domain.Order;
import domain.Store;
import domain.User;
import domain.Card;
import domain.Location;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Comparator;
import services.CardService;
import services.DeliveryMethod;
import services.DeliveryMethodService;
import services.LocationService;
import services.MenuServices;
import services.OrderService;
import services.StoreService;
import services.UserService;

public class Tiger{

	public static ServiceWrapper sw;
	public static Connection con;
	public static User currentUser;
	public static Order currentOrder;
	public static Store currentStore;
	
	static Scanner sc;

	public static void main(String[] args) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
        	        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "db_uSpring", "pass");
		} catch (ClassNotFoundException | SQLException e) {
                    System.out.println(e.getMessage());
		}
		sw  = new ServiceWrapper(con);
		sc = new Scanner(System.in);
		firstScreen();
		sc.close();
	}
	
        
        public static void printArt(){
                   
            System.out.println(" _______  _______  _______  _____    _______  _______  _______ ");
            System.out.println("|       ||       ||       ||     |  |       ||       ||       |");
            System.out.println("|  _____||    ___||   _   ||  _   | |   _   ||    ___||  _____|");
            System.out.println("| |_____ |   |___ |  |_|  || | |   ||  | |  ||   | __ | |_____ ");
            System.out.println("|_____  ||    ___||       || |_|   ||  |_|  ||   ||  ||_____  |");
            System.out.println(" _____| ||   |___ |   _   ||      | |       ||   |_| | _____| |");
            System.out.println("|_______||_______||__| |__||_____|  |_______||_______||_______|");
        }
        
	public static void firstScreen(){
                printArt();
		ArrayList<String> options = new ArrayList<>();
		options.add("Login");
		options.add("Register");
		options.add("Quit");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
		
	    int input = sc.nextInt();
	    switch(input){
    		case 1:
    			loginScreen();break;
    		case 2:
    			registerScreen();break; 	
    		case 3:
    			System.out.println("Goodbye");
    			System.exit(0);
                        break;
    		case 4:
    			AdminAndManager aam = new AdminAndManager(con);
    			aam.adminScreen();
                        break;
	    }

	}
		
	public static void loginScreen(){
		System.out.println("\n*Login*");
		System.out.println("Enter email:");
                String email = sc.next();
		System.out.println("Enter password:");
                String password = sc.next();
	    
		UserService us = new UserService(con);
		User candidate = us.getByEmail(email);
		if(candidate == null){
			System.out.println("Wrong email");
			firstScreen();
		}
		if(password.equals(candidate.getPassword())){

                        OrderService os = new OrderService(con);
			currentUser = candidate;
			currentOrder = new Order(os.getNextOrderId());
			currentOrder.setUser_id(currentUser.getUserId());
			currentOrder.setDelivery_status_id("0");
			//currentOrder.setCard_id();
			StoreService ss = new StoreService(con);
			currentStore = ss.getById("0");
                	homeScreen();
	    }
	    else{
	    	System.out.println("Wrong email or password");
	    	try {
				TimeUnit.SECONDS.sleep(1);
				firstScreen();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    }


	}
	public static void registerScreen(){
		System.out.println("\n*Register*");
		System.out.println("Enter email:");
	    String email = sc.next();
		System.out.println("Enter password:");
	    String password = sc.next();
		System.out.println("Enter password again:");
	    String passwordConfirm = sc.next();
		System.out.println("Enter first name:");
	    String first = sc.next();
		System.out.println("Enter last name:");
	    String last = sc.next();
		System.out.println("Enter phone:");
	    String phone = sc.next();
/*
	    String street = sc.next();
		System.out.println("Enter city:");
	    String city = sc.next();
		System.out.println("Enter state:");
	    String state = sc.next();
		System.out.println("Enter country:");
	    String country = sc.next();
		System.out.println("Enter zip:");
	    String zip = sc.next();
		System.out.println("Enter status:");
	    String status = sc.next();*/
	    //, street, city, state, country, zip, status
	    if(password.equals(passwordConfirm)){
	    	System.out.println("Registered");
	    	currentUser = sw.register(first, last, phone, email, password);
                OrderService os = new OrderService(con);
			currentOrder = new Order(os.getNextOrderId());
			currentOrder.setOrder_id(Double.toString(Math.random()* 10001));
			currentOrder.setUser_id(currentUser.getUserId());
			currentOrder.setDelivery_status_id("0");
	    	homeScreen();
	    }else{
	    	System.out.println("Mismatching passwords, try again");
	    	firstScreen();
	    }


	}

	public static void homeScreen(){
    	System.out.println("Welcome " + currentUser.getFirstName());
		System.out.println("\n*Home*");
		ArrayList<String> options = new ArrayList<String>();
		options.add("Menu");
		options.add("Order");
		options.add("Account");
		options.add("Store Details");
		options.add("Logout");
		options.add("Quit");

                // Offer admin and manager menus
               // if(currentUser.)
                
                
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
	    int input = sc.nextInt();
		if(input==1) menuScreen();
		if(input==2) currentOrderScreen();    	
		if(input==3) accountScreen();
		if(input==4) storeDetailsScreen();   	
		if(input==5) firstScreen();
		if(input==6) {
		System.out.println("Goodbye");
    		System.exit(0);
	    }
	}

	public static void menuScreen(){
		System.out.println("\n*Menu*");
                ArrayList<String> options = new ArrayList<String>();
		options.add("Items");
		options.add("Specials");
                options.add("Go back");
                int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
	    int input = sc.nextInt();
		if(input==1) itemScreen();
		if(input==2) specialScreen();    	
		if(input==3) homeScreen();
	}
        public static void itemScreen() {
            System.out.println("\n*Items*");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
	    int input = sc.nextInt();
	    if(input==menus.size()+1) menuScreen();
	    else menuItemScreen(menus.get(input-1));
        }
        public static void specialScreen() {
            System.out.println("\n*Specials*");
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> menus = ms.getAll();
		ServiceWrapper.printMenuItems(menus);
	    int input = sc.nextInt();
	    if(input==menus.size()+1) menuScreen();
	    else menuItemScreen(menus.get(input-1));
        }
	public static void menuItemScreen(Menu menu){
		System.out.println("\n*" + menu.getName() + "*");
		System.out.println(menu.getDescription());
		System.out.println("$" + menu.getPrice());
		System.out.println("1. Enter Quantity");
		System.out.println("2. Go Back");
	    int input = sc.nextInt();
	    if(input==1) itemQuantityScreen(menu);
	    else if(input==2) menuScreen();
	}
	//TODO finish this
	public static void itemQuantityScreen(Menu menu){
		System.out.println("Enter Quantity");
	    int input = sc.nextInt();
	    for(int i=0;i<input;i++) currentOrder.addItem_id(menu.getId());
		System.out.println("Item(s) added");
		menuScreen();
	}
	public static void currentOrderScreen() {
                DeliveryMethodService method = new DeliveryMethodService(con);
		System.out.println("\n*Current Order*");
                DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
		System.out.println("Placed: " +currentOrder.getPlaced_timestamp().format(timeFormat));
		System.out.println("Delivery Time: " +currentOrder.getDelivery_timestamp().format(timeFormat));
                System.out.println("Items: ");
                
                MenuServices ms = new MenuServices(con);
                ArrayList<String> idList = currentOrder.getItem_ids();
                Comparator<String> c = Comparator.comparing(String::toString);
                idList.sort(c);
                if(!idList.isEmpty()){
                    String curId = idList.get(0);
                    //Tab so output can be read easier
                    System.out.print("    ");
                    int amount = 0;
                    for (int i = 0; i <= idList.size() - 1; i++) {
                        if (i == idList.size() - 1 || !idList.get(i+1).equals(curId)) {
                            System.out.print(ms.getById(idList.get(i)).getName() + " " + amount);
                            amount = 0;
                            if (i != idList.size() - 1) {
                                System.out.print(", ");
                                curId = idList.get(i+1);
                            } else {
                                System.out.print("\n");
                            }
                        } else {
                            amount += 1;
                        }
                    }
                }
		ServiceWrapper serviceWrap = new ServiceWrapper(con);
		currentOrder.setTotal_price(serviceWrap.calculateTotalPrice(currentOrder));
                System.out.println("Tip: $"+currentOrder.getTip());
		System.out.printf("Total price: $%.2f\n",currentOrder.getTotal_price());
		System.out.println("Method: " +method.getById(currentOrder.getDelivery_method_id()).getDelivery_method());
		System.out.println("Status: " +currentOrder.getDelivery_status_id());
		System.out.println("1. Cancel");
		System.out.println("2. View\\Edit Items");
		System.out.println("3. Edit Order");
		System.out.println("4. Submit Order");
		System.out.println("5. Go Back");
	    int input = sc.nextInt();
	    if(input==1 && confirm()) {
                OrderService os = new OrderService(con);
	    	currentOrder = new Order(os.getNextOrderId());
			currentOrder.setUser_id(currentUser.getUserId());
			currentOrder.setDelivery_status_id("0");
	    }
	    if(input==2) viewEditOrderItems(currentOrder);
	    if(input==3) editOrder(currentOrder);
	    if(input==4 && hasItems()){ 
                
                CardService cs = new CardService(con);
                OrderService os = new OrderService(con);
                ArrayList<Card> userCards = cs.getUserCards(currentUser.getUserId());
                if(userCards.isEmpty()){
                    System.out.println("You must add a Card to your account first.");
                    currentOrderScreen();
                }
                String cardId = cs.getUserCards(currentUser.getUserId()).get(0).getCardId();
                currentOrder.setCard_id(cardId);
                serviceWrap.submitOrder(currentOrder);
                os.generateInvoice(currentOrder.getOrder_id());
                homeScreen();
            }else{
                System.out.println("No Orders in Cart. Redirecting to previous screen");
                homeScreen();
            }
	    if(input==5) homeScreen();
	}
	public static boolean hasItems(){
            if(currentOrder.getItem_ids().isEmpty()){
                return false;
            }
            return confirm();
        }
        
	private static void editOrder(Order currentOrder2) {
		System.out.println("\n*Edit Order*");
		ArrayList<String> options = new ArrayList<>();
		options.add("Edit Tip");
		options.add("Edit delivery time");
		options.add("Edit Instructions");
		options.add("Edit Delivery Method");
		options.add("Edit Store");
                options.add("Edit Payment");
		options.add("Go Back");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
	    int input = sc.nextInt();
    		if(input==1){
                    float newTip = 0;
                    try{
                        newTip = Float.parseFloat(editString());
                    }catch(NumberFormatException e){
                        System.out.println("Tip value must be a number");
                        currentOrderScreen();
                    }
                    
                    currentOrder.setTip(newTip);
                    System.out.println("Tip Changed to: $" + newTip);
    		}
    		if(input==2){
                        System.out.println("Enter a time (hh:mm)");
                        String timeStr = sc.next();
                        LocalTime newDelivery_timestamp = LocalTime.now();
                        try{
                            newDelivery_timestamp = LocalTime.parse(timeStr);
                        }catch(DateTimeParseException e){
                            System.out.println("Time should be in the format (hh:mm)");
                            currentOrderScreen();
                        }
                        
                        currentOrder.setDelivery_timestamp(newDelivery_timestamp);
    			System.out.println("Delivery Time Changed to: " + newDelivery_timestamp);
    		}
    		if(input==3){
    			String newInstructions = editString();
    			currentOrder.setInstuctions(newInstructions);
    			System.out.println("Instructions Changed to: " + newInstructions);
    		}
    		if(input==4){
                        DeliveryMethodService method = new DeliveryMethodService(con);
                        ArrayList<DeliveryMethod> all = method.getAll();
                        int methodCount = 1;
                        for (DeliveryMethod x : all){
                            System.out.println(methodCount+". "+x.getDelivery_method());
                            methodCount++;
                        }
                        
                        System.out.println(methodCount + ". Go Back");
                        int methodSelection = sc.nextInt();
                        
                        if(methodSelection != methodCount){
                            currentOrder.setDelivery_method_id(all.get(methodSelection-1).getDelivery_method_id());
                            System.out.println("Method Changed to: " + all.get(methodSelection-1).getDelivery_method_id());
                        }
    		}
    		if(input==5){
                        LocationService location = new LocationService(con);
                        ArrayList<Location> all = location.getAll();
                        int locationCount = 1;
                        for (Location x : all){
                            System.out.println(locationCount+". "+x.getAddress());
                            locationCount++;
                        }
                        
                        System.out.println(locationCount + ". Go Back");
                        int locationSelection = sc.nextInt();
                        
                        if(locationSelection != locationCount){
                            currentOrder.setStore_id(all.get(locationSelection-1).getLocationId());
                            System.out.println("Store Changed to: " + all.get(locationSelection-1).getAddress());
                        }
    		}
                if(input==6){
                    CardService cs = new CardService(con);
                    ArrayList<Card> userCards = cs.getUserCards(currentUser.getUserId());
                    if(userCards.isEmpty()){
                        System.out.println("You must add a Card to your account first.");
                        currentOrderScreen();
                    }
                    int countCards = 0;
                    for(Card x : userCards){
                        countCards++;
                        System.out.println(countCards+ ". " +x.getCardNumber());
                        
                    }
                    int userChoice = sc.nextInt();
                    userChoice --; //subtract because array index at 0. 
                    String cardId = userCards.get(userChoice).getCardId();
                    currentOrder.setCard_id(cardId);

                }
    		if(input==7) homeScreen();
	    
	    currentOrderScreen();
		
	}

	//TODO get item from item id here
	private static void viewEditOrderItems(Order order) {
		System.out.println("*View Items*");
		ArrayList<String> itemIds = currentOrder.getItem_ids();
		ArrayList<Menu> items = sw.getMenuItems(itemIds);
		if(items.isEmpty()) System.out.println("No items");
		ServiceWrapper.printMenuItems(items);
	    int input = sc.nextInt();
	    if(input==items.size()) homeScreen();
	    else if(input==items.size()+1) currentOrderScreen();
	    else orderItemScreen(items.get(input));
	}
	public static void orderItemScreen(Menu menu){
		/*System.out.println(menu.getName());
		System.out.println(menu.getDescription());
		System.out.println(menu.getPrice());
		System.out.println("1. Enter Quantity");
		System.out.println("2. Go Back");
		Scanner sc = new Scanner(System.in);
	    int input = sc.nextInt();
	    if(input==1) itemQuantityScreen(menu);
	    else if(input==2) System.exit(0);*/
	}

	public static void submitOrder(){
		System.out.println("\n*Submit*");
                
                
	    //OrderService os = new OrderService(con);
	    //input should be equal to number of items in order
	    //Menu menu = null;
	   // int input = 0;
	    //for(int i=0;i<input;i++){
	    	//create order item and add to item
	    	//os.addItem_id(menu.getId(), currentOrder.getOrder_id());
	   // }
	    //OrderService os = new OrderService(con);
	    //os.update(currentOrder);
	}
	
	public static void accountScreen(){
		System.out.println("\n*Account*");
		ArrayList<String> options = new ArrayList<>();
		options.add("Edit First Name");
		options.add("Edit Last Name");
		options.add("Edit Email");
		options.add("Edit Password");
		options.add("Edit Phone Number");
		options.add("Edit Payment Options");
		options.add("Edit Saved Locations");
		options.add("View Orders");
		options.add("Go Back");
		int count = 0;
		for(String option : options) {
			count++;
			System.out.println(count + ". " + option);
		}
	    int input = sc.nextInt();
    		if(input==1){
    			String newFirstName = editString();
    			currentUser.setFirstName(newFirstName);
    			System.out.println("First Name Changed to: " + newFirstName);
    		}
    		if(input==2){
    			String newLastName = editString();
    			currentUser.setLastName(newLastName);
    			System.out.println("Last Name Changed to: " + newLastName);
    		}
    		if(input==3){
    			String newEmail = editString();
    			currentUser.setEmail(newEmail);
    			System.out.println("Email Changed to: " + newEmail);
    		}
    		if(input==4){
    			String newPassword = editString();
    			currentUser.setPassword(newPassword);
    			System.out.println("Password Changed to: " + newPassword);
    		}
    		if(input==5){
    			String newPhoneNumber = editString();
    			currentUser.setPhone(newPhoneNumber);
    			System.out.println("Phone Number Changed to: " + newPhoneNumber);
    		}
    		if(input==6) editCards();
    		if(input==7) editLocations();
    		if(input==8) allOrdersScreen();
    		if(input==9) homeScreen();
	    
	    UserService us = new UserService(con);
	    us.update(currentUser);
	    accountScreen();
	}
	private static void editLocations() {
            System.out.println("\n *** Locations ***\n");
            
            ArrayList<String> options = new ArrayList<>();
            options.add("Add Location");
            
            LocationService ls = new LocationService(con);
            ArrayList<Location> userLoc = ls.getUserLocations(currentUser.getUserId());
           
            //check if user has any locations on file. 
            if(userLoc == null || userLoc.isEmpty()){
                System.out.println("You have no locations on file.");
            }else{
                for(Location l: userLoc){
                    options.add(l.getStreet());
                }       
            }
            options.add("Go Back");
            
            //print options
            int count = 0;
            for(String x : options){
                count++;
                System.out.println(count+". "+x);
            }
            
            int input = sc.nextInt();
            if(input == 1){
                sc.nextLine();
                addLocation(ls);
                System.out.println("Location Added..");
            }else if(input ==(2+userLoc.size())){
                //Go back
                accountScreen();
            }else{
                //decrement bc array starts at 0. 
                input--;
                System.out.println("\nViewing Location: "+options.get(input));
                System.out.println("1. Delete Location");
                System.out.println("2. Edit Location");
                System.out.println("3. Go Back");
                
                int uChoice = sc.nextInt();
                
                input--;//decrement again to pull from userCards array
                switch(uChoice){
                    case 1: ls.deleteById(userLoc.get(input).getLocationId());
                            System.out.println("Location Deleted");
                            break;
                    case 2: editALoc(userLoc.get(input));   break;
                    case 3: editLocations(); break;
                    default:break;
                }
            }        	
	}
        private static void editALoc(Location l){
        System.out.printf("\n*** Edit Location: ***\n");
        System.out.println("1. Edit Street: " +l.getStreet());
        System.out.println("2. Edit City:  "+l.getCity());
        System.out.println("3. Edit State: "+l.getState());
        System.out.println("4. Edit Country: " + l.getCountry());
        System.out.println("5. Edit Zip: " + l.getZip());
        System.out.println("6. Back");
        
        int input = sc.nextInt();

        switch(input){
            case 1: 
                String street = editStringLine(); 
                l.setStreet(street);
                System.out.println("Street changed to "+street);
                editALoc(l);
                break;
            case 2: 
                String city = editStringLine();
                l.setCity(city);
                System.out.println("City changed to "+city);
                editALoc(l);
                break;
            case 3: 
                String state = editStringLine(); 
                l.setState(state);
                System.out.println("State changed to " + state);
                editALoc(l);
                break;
            case 4: 
                String country = editStringLine();
                l.setCountry(country);
                System.out.println("Country changed to " + country);
                editALoc(l);
                break;
            case 5:
                String zip = editStringLine();
                l.setZip(zip);
                System.out.println("Zip changed to "+ zip);
                editALoc(l);
                break;
            default: break;
        }
        LocationService ls = new LocationService(con);
        ls.update(l);
        editLocations();
        }
        private static String editStringLine(){
            sc.nextLine();
            System.out.println("Enter new value");
            String in = sc.nextLine();
            return in;
        }
        private static void addLocation(LocationService ls){
            System.out.println("Enter Street");
            String street = sc.nextLine();
            
            System.out.println("Enter city");
            String city = sc.nextLine();
            
            System.out.println("Enter Country");
            String country = sc.nextLine();
            
            System.out.println("Enter state");
            String state = sc.nextLine();
            
            System.out.println("Enter zip");
            String zip = sc.nextLine();
            String userId = currentUser.getUserId();
            String locId = ls.getNextLocId();
            Location loc = new Location(locId, userId, street, city, state, country, zip);
            ls.add(loc);
        }
        
        private static void addACard(){
            CardService cardService = new CardService(con);
            
            System.out.println("Enter card number");
            String cardNum = sc.next();
            
            Date cardDate = editDate();
            if(cardDate.getYear()==1111){
                return;
            }
            
            System.out.println("Enter security code");
            String securityCode = sc.next();
            
            String userId = currentUser.getUserId();
            String cardId = cardService.getNextCardId();
            
            Card newCard = new Card(cardId, userId, cardNum, cardDate, securityCode);
            cardService.add(newCard);
            System.out.println("Card Added..");
        }
             
	private static void editCards() {
            System.out.println("\n *** Cards ***\n");
            
            ArrayList<String> options = new ArrayList<String>();
            options.add("Add a Card");
            
            CardService cardServe = new CardService(con);
            ArrayList<Card> userCards= cardServe.getUserCards(currentUser.getUserId());
           
            //check if user has any cards on file. 
            if(userCards == null || userCards.size()==0){
                System.out.println("You have no cards on file.");
            }else{
            
                for(int i=0; i<userCards.size(); i++){
                    options.add(userCards.get(i).getCardNumber());
                }
                
            }
            
            options.add("Go Back");
            
            //print all optiins. first option is to add a card. 
            int count = 0;
            for(String x : options){
                count++;
                System.out.println(count+". "+x);
            }
            
            int cardChoice = sc.nextInt();
		
            if(cardChoice == 1){
                addACard();
            }else if(cardChoice == options.size()){
                accountScreen();
            }else{
                //decrement bc array starts at 0. 
                cardChoice = cardChoice -2;
                System.out.println("*** Viewing Card ***");
                System.out.println("Card Number: "+userCards.get(cardChoice).getCardNumber());
                System.out.println("Security Code: "+userCards.get(cardChoice).getSecurityCode());
                System.out.println("Expiry Date: "+userCards.get(cardChoice).getExpiryDate());
                System.out.println("1. Delete Card");
                System.out.println("2. Edit Card");
                System.out.println("3. Go Back");
                
                int viewChoice = sc.nextInt();
                
                switch(viewChoice){
                    case 1: cardServe.deleteById(userCards.get(cardChoice).getCardId());
                            System.out.println("Card Deleted");
                            break;
                    case 2: editACard(userCards.get(cardChoice));   break;
                    case 3: editCards(); break;
                    default:break;
                }
            }
            editCards();            
	}

	private static String editString() {
		System.out.println("Enter new value");
	    String inp = sc.next();
		return inp;
	}
        
	private static Date editDate() {
            System.out.println("Enter date in the format YYYY-MM-DD");
            String dateStr = sc.next();
           Date cardDate = new Date(1111,1,1);

            while(true){
                try{
                    cardDate = Date.valueOf(dateStr);
                    Calendar cardCal = Calendar.getInstance();
                    Calendar currCal = Calendar.getInstance();
                    cardCal.setTime(cardDate);
                    if(cardCal.compareTo(currCal) < 0 )
                        throw new IllegalExpiryDateException();
                    break;
                }catch(IllegalArgumentException e){
                    System.out.println("Date Format Incorrect");
                    System.out.println("Enter date in the format YYYY-MM-DD");
                    dateStr = sc.next();
                }catch(IllegalExpiryDateException e){
                    System.out.println("May not enter a past date for card expiry date");
                    System.out.println("Enter date in the format YYYY-MM-DD");
                    dateStr = sc.next();
                }
            }
            cardDate = Date.valueOf(dateStr);
            return cardDate;
        }
        
	public static void allOrdersScreen(){
		System.out.println("\n*All orders*");
		OrderService os = new OrderService(con);
		ArrayList<Order> orders = os.getUserOrders(currentUser.getUserId());
		ServiceWrapper.printOrders(orders);
	    int input = sc.nextInt();
	    if(input==orders.size()) homeScreen();
	    else oldOrderScreen(orders.get(input));
	}
	public static void oldOrderScreen(Order order) {
		System.out.println("Placed: " +order.getPlaced_timestamp());
		System.out.println("Delivered: " +order.getDelivery_timestamp());
		System.out.println("Total price: " +order.getTotal_price());
		System.out.println("Method: " +order.getDelivery_method_id());
		System.out.println("Status: " +order.getDelivery_status_id());
		System.out.println("1. Reorder");
		System.out.println("2. Go Back");
	    int input = sc.nextInt();
	    if(input==1 && confirm()) {
	    	currentOrder=order;
	    	//TODO find out what the status id this thing needs is
	    	currentOrder.setDelivery_status_id("1");
	    }
	    else if(input==2) accountScreen();
	}
	public static void storeDetailsScreen(){
		System.out.println("\n*Store*");
		System.out.println("Name: " + currentStore.getStoreName());
		System.out.println("Phone Number: " + currentStore.getPhoneNumber());
		System.out.println("Location: " + currentStore.getLocationId());
		System.out.println("Open: " + currentStore.getOpenTime());
		System.out.println("Close: " + currentStore.getCloseTime());
		homeScreen();
	}

	public static boolean confirm(){
		System.out.println("\n1*Confirm*");
		System.out.println("1. Yes");
		System.out.println("2. No");
	    int input = sc.nextInt();
	    return input==1;
	}

    private static void editACard(Card editCard) {
        
        System.out.printf("\n*** Editing Card ***\n");
        System.out.println("1. Edit Card Number: " +editCard.getCardNumber());
        System.out.println("2. Edit Security Code: "+editCard.getSecurityCode());
        System.out.println("3. Edit Expiration Date: "+editCard.getExpiryDate());
        System.out.println("4. Back");
        
        int editChoice = sc.nextInt();

        switch(editChoice){
            case 1: 
                String cardNum = editString(); 
                editCard.setCardNumber(cardNum);
                System.out.println("Card Number changed to "+cardNum);
                break;
            case 2: 
                String securityCode = editString();
                editCard.setSecurityCode(securityCode);
                System.out.println("Security code changed to "+securityCode);
                break;
            case 3: 
                Date cardDate = editDate(); 
                editCard.setExpiryDate(cardDate);
                System.out.println("Expiry Date changed to " + cardDate);
                break;
            case 4: break;
            default: break;
        }
        
        CardService cardServe = new CardService(con);
        cardServe.update(editCard);
        editCards();        
    }
}
