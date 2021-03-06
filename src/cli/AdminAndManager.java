package cli;

import static cli.Tiger.con;
import static cli.Tiger.editString;
import static cli.Tiger.firstScreen;
import static cli.Tiger.getAnInt;

import domain.Card;
import domain.Location;
import domain.Menu;
import domain.Order;
import domain.Special;
import domain.SpecialMenu;
import domain.User;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

import domain.UserStatus;
import domain.ItemType;
import java.sql.Date;
import java.util.Arrays;
import services.CardService;
import services.DeliveryMethod;
import services.DeliveryMethodService;
import services.DeliveryStatus;
import services.DeliveryStatusService;
import services.LocationService;
import services.MenuServices;
import services.OrderService;
import services.SpecialServices;
import services.UserService;
import services.UserStatusService;
import services.ItemTypeServices;

public class AdminAndManager {

    static Connection con;
    private static User user;
    private static final String MANAGER = "5";
    private static final String ADMIN = "3";

    public AdminAndManager(Connection con) {
        AdminAndManager.con = con;
        user = null;
    }

    public static void adminScreen() {
        // Request login            
        if (user == null) {
            adminLogin();
        }

        ArrayList<String> options = new ArrayList<String>();
        System.out.println("Admin View");
        options.add("Alter Cards");
        options.add("Alter Specials");
        options.add("Alter Delivery Methods");
        options.add("Alter Delivery Statuses");
        options.add("Alter Items");
        options.add("Alter Item Types");
        options.add("Alter Locations");
        options.add("Alter Orders");
        options.add("Alter Users");
        options.add("Alter User Statuses");
        ServiceWrapper.printOptions(options);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        int option = 0;
        switch (input) {
            case 1: {
                option = optionsScreen("Card");
                switch (option) {
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
            case 2:
                option = optionsScreen("Specials");
                switch (option) {
                    case 1:
                        alterSpecialScreen();
                        break;
                    case 2:
                        addSpecialScreen();
                        break;
                    case 3:
                        deleteSpecialScreen();
                        break;
                    case 4:
                        adminScreen();
                        break;
                    case 5:
                        System.exit(0);
                }
            case 3:
                option = optionsScreen("Delivery Method");
                switch (option) {
                    case 1:
                        alterDeliveryMethod();
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
            case 4:{
                option = optionsScreen("Delivery Status");
                switch(option){
                    case 1:
                        alterDeliveryStatus();
                        break;
                    case 2:
                        addDeliveryStatus();
                        break;
                    case 3:
                        deleteDeliveryStatus();
                        break;
                    case 4:
                        adminScreen();
                        break;
                    case 5:
                        System.exit(0);
                }
            }
            
            case 5: {
                option = optionsScreen("Item");
                switch (option) {
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
            }
            case 6:
                option = optionsScreen("Item Type");

                        switch(option){
                            case 1:
                                updateItemTypeScreen();
                                break;
                            case 2:
                                addItemTypeScreen();
                                break;
                            case 3:
                                deleteItemScreen();
                                break;
                            case 4:
                                adminScreen();
                                break;
                            default:
                                System.exit(1);
                        }
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
               alterOrder();
               break; 
            case 9: {
                option = optionsScreen("User");
                switch (option) {
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
            case 10:
                option = optionsScreen("User Statuses");
                switch (option) {
                    case 1:
                        alterStatus();
                        break;
                    case 2:
                        addStatus();
                        break;
                    case 3:
                        deleteStatus();
                    case 4:
                        adminScreen();
                        break;
                    default:
                        firstScreen();
                }
            case 11:
                firstScreen();
            default:
                adminScreen();
        }

        adminScreen();

    }

    public static int optionsScreen(String thing) {
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
        *********
        * Order *
        *********
     */
    private static void deleteOrder(){
         // Select a user's orders to modify
        Scanner kb = new Scanner(System.in);
        UserService userHelper = new UserService(con);
        if(userHelper.getAll().size() == 0){
            System.out.println("No orders to delete");
            adminScreen();
        }
        User user = userHelper.selectUser();
        
        // Select a order to alter
        OrderService orderHelper = new OrderService(con);
        ArrayList<Order> orders = orderHelper.getUserOrders(user.getUserId());
        if(orders.size() == 0){
            System.out.println("No orders to delete");
            adminScreen();
        }
        System.out.println("Which order would you like to edit?");
        for (int i = 0; i < orders.size(); i++) {
            System.out.println((i + 1) + ". " + orders.toString());
        }
        Order toDelete = orders.get(Integer.parseInt(kb.nextLine()) - 1);
    
        orderHelper.deleteById(toDelete.getOrder_id());
    }
    
    
    private static void alterOrder(){
        // Select a user's orders to modify
        Scanner kb = new Scanner(System.in);
        UserService userHelper = new UserService(con);
        User user = userHelper.selectUser();
        
        // Select a order to alter
        ServiceWrapper service = new ServiceWrapper(con);
        OrderService orderHelper = new OrderService(con);
        ArrayList<Order> orders = orderHelper.getUserOrders(user.getUserId());
        if(orders.size() == 0){
            System.out.println("No orders to edit.");
            adminScreen();
        }
        System.out.println("Which order would you like to edit?");
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            String out = order.getItem_ids().size()+" items | Price $"
                               +order.getTotal_price()+" | Placed at "
                                +order.getPlaced_timestamp();
            System.out.println((i + 1) + ". " + out);
        }
        Order toAlter = orders.get(Integer.parseInt(kb.nextLine()) - 1);
        
        // Select a field to edit
        String[] fields = {"Tip", "Price", "Card", "Instructions",
                           "Delivery Method", "Delivery Status"};
        for (int i = 0; i < fields.length; i++) {
            System.out.println((i + 1) + ". " + fields[i]);
        }
        String toChange = fields[Integer.parseInt(kb.nextLine()) - 1]; 
        
        // Enter a new value
        System.out.println("What would you like to change " + toChange + " to?");
        if(toChange.equals("Tip")){
            toAlter.setTip(Float.parseFloat(kb.nextLine()));
        } else if(toChange.equals("Price")){
            toAlter.setTotal_price(Float.parseFloat(kb.nextLine()));
        } else if(toChange.equals("Instructions")){
            toAlter.setInstuctions(kb.nextLine());
        } else if(toChange.equals("Card")){
            // Get the users card
            CardService helper = new CardService(con);
            ArrayList<Card> cards = helper.getUserCards(user.getUserId());
        
            // Select a card to use
            System.out.println("Select a card:");
            for (int i = 0; i < cards.size(); i++) {
                System.out.println((i + 1) + ". " + cards.get(i).getCardNumber());
            }
            Card choice = cards.get(Integer.parseInt(kb.nextLine()) - 1);
            toAlter.setCard_id(choice.getCardId());
        } else if(toChange.equals("Delivery Method")){
            DeliveryMethodService helper = new DeliveryMethodService(con);
            DeliveryMethod choice = helper.selectDeliveryMethod();
            toAlter.setDelivery_method_id(choice.getDelivery_method_id());
        }  else if(toChange.equals("Delivery Status")){
            DeliveryStatusService helper = new DeliveryStatusService(con);
            DeliveryStatus choice = helper.selectDeliveryStatus();
            toAlter.setDelivery_status_id(choice.getDelivery_status_id());
        } else {
            System.out.println("Invalid option");
            alterOrder();
        }
        
        // Update the order
        orderHelper.updateAdmin(toAlter);
        adminScreen();
    }
    
    /*
        *******************
        * Delivery Method *
        *******************
     */
    private static void alterDeliveryMethod() {
        // Ask for a delivery method to delete
        Scanner kb = new Scanner(System.in);
        DeliveryMethodService deliveryHelper = new DeliveryMethodService(con);
        ArrayList<DeliveryMethod> methods = deliveryHelper.getAll();
        if(methods.size() == 0){
            System.out.println("No methods to edit");
            adminScreen();
        }
        for (int i = 0; i < methods.size(); i++) {
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

    private static void deleteDeliveryMethod() {
        // Ask for a delivery method to delete
        Scanner kb = new Scanner(System.in);
        DeliveryMethodService deliveryHelper = new DeliveryMethodService(con);
        ArrayList<DeliveryMethod> methods = deliveryHelper.getAll();
        if(methods.size() == 0){
            System.out.println("No methods to delete");
            adminScreen();
        }
        for (int i = 0; i < methods.size(); i++) {
            System.out.println((i + 1) + ". " + methods.get(i).getDelivery_method());
        }
        System.out.println("Chose a delivery method to delete:");
        DeliveryMethod choice = methods.get(Integer.parseInt(kb.nextLine()) - 1);

        // Delete the method
        deliveryHelper.deleteById(choice.getDelivery_method_id());
        System.out.println(choice.getDelivery_method() + " deleted");
        adminScreen();
    }

    private static void addDeliveryMethod() {
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
    private static void alterStatus() {
        // Ask for which status to alter
        UserStatusService statusHelper = new UserStatusService(con);
        ArrayList<UserStatus> statuses = statusHelper.getAll();
        System.out.println("Select a user status to alter:" + statuses.size());
        if(statuses.size() == 0){
            System.out.println("No user statuses to edit");
            adminScreen();
        }
        for (int i = 0; i < statuses.size(); i++) {
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
        adminScreen();
    }

    private static void addStatus() {
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

    private static void deleteStatus() {
        // Ask for the user status to delete
        UserStatusService statusHelper = new UserStatusService(con);
        Scanner kb = new Scanner(System.in);
        ArrayList<UserStatus> statuses = statusHelper.getAll();
        ArrayList<UserStatus> filtered = new ArrayList<UserStatus>();

        // Don't give the option to delete admin or manager
        for(UserStatus status: statuses){
            if(!status.getUserStatusId().equals(MANAGER) &&
                !status.getUserStatusId().equals(ADMIN)){
                filtered.add(status);
            }
        }

        if(filtered.isEmpty()){
            System.out.println("No statuses to delete");
            adminScreen();
        }
        System.out.println("Select a user status to delete");
        for (int i = 0; i < filtered.size(); i++) {
            System.out.println((i + 1) + ". " + filtered.get(i));
        }
        int choiceIndex = Integer.parseInt(kb.nextLine()) - 1;
        UserStatus toDelete = filtered.get(choiceIndex);

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
        adminScreen();
    }

    /*
            This will protect against updating to nulls but not updating
            to values that are not valid. I.e. someone can change a user
            status to 999999 which is not a status
     */
    private static void alterUserScreen() {
        // Ask what user to alter
        Scanner kb = new Scanner(System.in);
        UserService userHelper = new UserService(con);
        ArrayList<User> users = userHelper.getAll();
        if(users.size() == 0){
            System.out.println("No users to edit");
            adminScreen();
        }
        System.out.println("Select a user to edit:");
        for(int i = 0; i < users.size(); i++){
            String first = users.get(i).getFirstName();
            String last = users.get(i).getLastName();
            System.out.println((i + 1) + ". " + first + " " + last);
        }
        User toAlter = users.get(Integer.parseInt(kb.nextLine()) - 1);

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
            System.out.println((i + 1) + ". " + options.get(i));
        }
        changeField = options.get(Integer.parseInt(kb.nextLine()) - 1);

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
        
        /*Instead repeatedly ask for input*/
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
    }

    private static void addCardScreen() {
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
        adminScreen();
    }

    private static void deleteCardScreen() {
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cl = cs.getAll();
        if(cl.size() == 0){
            System.out.println("No cards to delete");
            adminScreen();
        }
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
        adminScreen();
    }

    private static void alterCardScreen() {
        System.out.println("List of cards");
        CardService cs = new CardService(con);
        ArrayList<Card> cl = cs.getAll();
        if(cl.size() == 0){
            System.out.println("No cards to edit");
            adminScreen();
        }
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
        adminScreen();
    }
private static void addSpecialScreen() {
        System.out.println("Add a special");
        Scanner sc = new Scanner(System.in);
        MenuServices menServ = new MenuServices(con);
        SpecialMenu sm = new SpecialMenu();
        sm.setId("" + menServ.getNextSpecialId());
        System.out.println("\nEnter special name: ");
        sm.setName(sc.nextLine());
//        System.out.println("\nEnter vegeterian (y or n): ");
//        String vege = sc.next();
//        char vegetarian = vege.charAt(0);
        System.out.println("\nEnter a description: ");
        sm.setDescription(sc.nextLine());
//        System.out.println("\nEnter meal time: ");
//        String slot_ID = sc.next();
        System.out.println("Enter discount");
        sm.setDiscount(sc.nextInt());
        MenuServices ms = new MenuServices(con);
        Boolean stillSelecting = true;
        while(stillSelecting) {
            System.out.println("Choose an item to add");
            ArrayList<Menu> menus = ms.getAll();
            ServiceWrapper.printMenuItems(menus);
            int id = sc.nextInt();
            if (id == menus.size() + 1) {
                return;
            }
            if (id == menus.size() + 2) {
                System.exit(0);
            }
            System.out.println("Enter amount");
            int amount = sc.nextInt();
            ms.addSpecial(sm, "" + (id-1), amount);
            System.out.println("Do you wish to add more items? ");
            System.out.println("1. I want to add more ");
            System.out.println("2. I am done ");
            int input = sc.nextInt();
            if (input > 1) {
                stillSelecting = false;
            }
        }
        adminScreen();
    }

    private static void deleteSpecialScreen() {
         MenuServices ms = new MenuServices(con);
        ArrayList<SpecialMenu> menus = ms.getAllSpecials();
        if(menus.isEmpty()){
            System.out.println("No special combos to delete");
        }
        System.out.println("Choose a Special Combo to delete");
        ServiceWrapper.printSpecialMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if (input == menus.size() + 1) {
            return;
        }
        if (input == menus.size() + 2) {
            System.exit(0);
        }

        ms.deleteSpecialById(menus.get(input - 1).getId());
        System.out.println("Deleted " + menus.get(input - 1).getName());
        adminScreen();
    }

    private static void alterSpecialScreen() {
        System.out.println("Choose a Special Combo to alter");
        MenuServices ms = new MenuServices(con);
        ArrayList<SpecialMenu> menus = ms.getAllSpecials();
        if(menus.size() == 0){
            System.out.println("No specials to edit");
            adminScreen();
        }
        System.out.println((menus.size() + 1) + ". Go Back");
        ServiceWrapper.printSpecialMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        
        if (input == menus.size() + 1) {
            adminScreen();
        }
        if (input == menus.size() + 2) {
            System.exit(0);
        }
        SpecialMenu men = menus.get(input - 1);
        System.out.println("Enter new name for " + men.getName() + ": ");
        men.setName(sc.next());
//        System.out.println("Enter vegeterian (y or n): ");
//        String vege = sc.next();
//        char vegetarian = vege.charAt(0);
        System.out.println("Enter a new description instead of: " + men.getDescription());
        men.setDescription(sc.next());
        System.out.println("Enter new discount");
        men.setDiscount(sc.nextInt());
//        System.out.println("Enter type number id: ");
//        String type = sc.next();
//        System.out.println("Enter meal time: ");
//        String slot_ID = sc.next();
//        System.out.println("Enter photo link: ");
//        String photo = sc.next();
        Boolean stillChanging = true;
        while(stillChanging) {
            System.out.println("Choose an item to change");
            Menu im;
            for(String i : men.getUniqueItemId()) {
                im = ms.getById(i);
                System.out.println("- " + im.getName() + ": " + men.countItemsById(i) + " * " 
                        + im.getPrice() + " * " + (100 - men.getDiscount()) + "% = "
                        + im.getPrice()* men.countItemsById(i) * (100 - men.getDiscount()) / 100);
            }
            ServiceWrapper sw = new ServiceWrapper(con);
            sw.printMenuItems(ms.getAll());
            System.out.println((ms.getAll().size()+2) + ". I will pass");
            int id = sc.nextInt();
            if (id == ms.getAll().size() + 1) {
                adminScreen();
            }
            if (id == ms.getAll().size() + 2) {
                stillChanging = false;
                ms.updateSpecial(men);
                adminScreen();
                //System.exit(0);
            } 
            System.out.println("Enter amount");
            int amount = sc.nextInt();
            if (men.getUniqueItemId().contains("" + (id-1))) {
                men.removeItemsById("" + (id-1));
                for (int j = 0; j < amount; j++) {
                    men.addItemId("" + (id-1));
                }
                System.out.println("new amount: " + men.countItemsById("" + (id-1)));
                ms.updateSpecial(men);
            } else {
                ms.addSpecial(men, "" + (id-1), amount);
            }
            
            men = ms.getSpecialById(men.getId());
            System.out.println("Do you wish to change more items? ");
            System.out.println("1. I want to change more ");
            System.out.println("2. I am done ");
            input = sc.nextInt();
            if (input > 1) {
                stillChanging = false;
            }
        }
        adminScreen();
    }
    private static void addItemScreen() {
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

        Menu men = new Menu("" + menServ.getNextItemId(), name, vegetarian, type, description, slot_ID, photo, price);
        menServ.add(men);
        System.out.println("\n" + name + " added to database\n");
        adminScreen();
    }

    private static void deleteItemScreen() {
        System.out.println("Choose an item to delete");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        if(menus.size()== 0){
            System.out.println("No items to delete");
            adminScreen();
        }
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
        adminScreen();
    }

    private static void alterItemScreen() {
        System.out.println("Choose an item to alter");
        MenuServices ms = new MenuServices(con);
        ArrayList<Menu> menus = ms.getAll();
        if(menus.size() == 0){
            System.out.println("No items to edit");
            adminScreen();
        }
        ServiceWrapper.printMenuItems(menus);
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if (input == (menus.size()) + 1) {
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
        adminScreen();
    }

    private static void addUserScreen() {
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

        // Go back to the admin screen
        adminScreen();
    }

    private static void deleteUserScreen() {
        // List existing users
        System.out.println("List of users");
        UserService us = new UserService(con);
        ArrayList<User> uArr = us.getAll();
        if(uArr.isEmpty()){
            System.out.println("No users to delete");
            adminScreen();
        }
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
        adminScreen();
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
        if(specials.size() == 0){
            System.out.println("No combos to edit");
            adminScreen();
        }
        for (Special special : specials) {
            specialCount++;
            System.out.println(specialCount + ". " + special.getItem_ID() + " " + special.getDiscountPercentage() + "%");
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
        String specialId = "" + SS.newSpecialId();
        SS.add(new Special(specialId, discount));
        adminScreen();
    }

    private void deleteComboScreen() {
        SpecialServices SS = new SpecialServices(con);
        ArrayList<Special> specials = SS.getAll();
        if(specials.size() == 0){
            System.out.println("No specials to delete");
            adminScreen();
        }
        System.out.println("\nWhat combo do you want to delete?");

        int specialCount = 0;
        for (Special special : specials) {
            specialCount++;
            System.out.println(specialCount + ". " + special.getItem_ID() + " " + special.getDiscountPercentage() + "%");
        }
        int specialChoice = Tiger.getAnInt();
        specialChoice--;
        String specialId = specials.get(specialChoice).getItem_ID();
        SS.deleteById(specialId);
        adminScreen();
    }


    private static void editLocations() {
        Scanner sc = new Scanner(System.in);
        LocationService ls = new LocationService(con);
        ArrayList<Location> locs = ls.getAll();
        if(locs.isEmpty()){
            System.out.println("No locations to edit");
            adminScreen();
        }
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

    private static void addLocation() {
        // Get the delivery method
        Scanner sc = new Scanner(System.in);
        LocationService locServe = new LocationService(con);
        Location newLoc = locPrompt(sc);
        String newID = locServe.getNextLocId();
        newLoc.setLocationId(newID);
        locServe.add(newLoc);
        System.out.println("Location at " +newLoc.getCity() +", " + newLoc.getState() +" added.");
    }
    private static Location locPrompt(Scanner sc){
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

    private static void deleteLocation() {
        // Ask for a location to delete
        Scanner sc = new Scanner(System.in);
        LocationService ls = new LocationService(con);
        ArrayList<Location> locs = ls.getAll();
        if(locs.size() == 0){
            System.out.println("No locations to delete");
            adminScreen();
        }
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

    private static void alterDeliveryStatus() {
        System.out.println("Choose a delivery status to alter");

        DeliveryStatusService DSS = new DeliveryStatusService(con);
        ArrayList<DeliveryStatus> stats = DSS.getAll();
        if(stats.size() == 0){
            System.out.println("No delivery statuses to edit");
            adminScreen();
        }
        int count = 0;
        for (DeliveryStatus x : stats) {
            count++;
            System.out.println(count + ". " + x.getDelivery_status());
        }
        Scanner sc = new Scanner(System.in);

        int input = Tiger.getAnInt();

        if (input == stats.size() + 1) {
            return;
        }
        if (input == stats.size() + 2) {
            System.exit(0);
        }

        System.out.println("Enter the new Delivery Status");

        String deliveryStatus = sc.next();

        DSS.update(new DeliveryStatus(stats.get(input - 1).getDelivery_status_id(), deliveryStatus));

        System.out.println("Delivery status deleted");

    }

    private static void deleteDeliveryStatus() {
        DeliveryStatusService DSS = new DeliveryStatusService(con);
        ArrayList<DeliveryStatus> stats = DSS.getAll();
        if(stats.size() == 0){
            System.out.println("No delivery statuses to delete");
            adminScreen();
        }
        
        System.out.println("Choose a delivery status to delete");

        int count = 0;
        for (DeliveryStatus x : stats) {
            count++;
            System.out.println(count + ". " + x.getDelivery_status());
        }
        Scanner sc = new Scanner(System.in);

        int input = Tiger.getAnInt();

        if (input == stats.size() + 1) {
            return;
        }
        if (input == stats.size() + 2) {
            System.exit(0);
        }

        DSS.deleteByID(stats.get(input - 1).getDelivery_status_id());

        System.out.println("Delivery status deleted");
    }

    private static void addDeliveryStatus() {

        Scanner sc = new Scanner(System.in);

        DeliveryStatusService DSS = new DeliveryStatusService(con);

        System.out.println("Enter the new Delivery Status");

        String newService = sc.next();

        DSS.add(new DeliveryStatus("" + DSS.newDeliveryStatusId(), newService));

        System.out.println("New Delivery Status " + newService + " created.");

    }

    private static void adminLogin() {
        Scanner sc = new Scanner(System.in);
        UserService userHelper = new UserService(con);
        while (user == null) {
            // Get email
            String email = "";
            do {
                System.out.println("Enter your email:");
                email = sc.nextLine();
            } while (email.length() == 0);

            // Get password
            String password = "";
            do {
                System.out.println("Enter your password");
                password = sc.nextLine();
            } while (password.length() == 0);

            // Check credentials
            boolean emailExists = (userHelper.getByEmail(email) != null);
            boolean passwordMatch = emailExists
                    && (userHelper.getByEmail(email).getPassword().equals(password));
            boolean isAdmin = passwordMatch
                    && (userHelper.getByEmail(email).getUserStatusId().equals(MANAGER)
                    || userHelper.getByEmail(email).getUserStatusId().endsWith(ADMIN));

            // Notify user of reason for failed login
            if (!emailExists || !passwordMatch) {
                System.out.println("Incorrect credentials.");
                firstScreen();
            } else if (!isAdmin) {
                System.out.println("You are not an admin.");
                firstScreen();
            }

            // Allow login
            if (isAdmin) {
                user = userHelper.getByEmail(email);
            }
        }
    }

    private static void addItemTypeScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the item type ID to be inserted");
        String TypeId = sc.next();
        System.out.println("Please enter the item type");
        String itmTyp = sc.next();
        ItemType it = new ItemType(TypeId, itmTyp);
        ItemTypeServices it1 = new ItemTypeServices(con);
        it1.add(it);
        System.out.println("Item Type ID " + TypeId + " added to database\n");
        adminScreen();

    }
    
    public static void updateItemTypeScreen() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the item type ID to be changed");
        String TypeId = sc.next();
        System.out.println("Please enter the new value for item type id: "+TypeId);
        String itmTyp = sc.next();
        System.out.println("1 here");
        ItemType it = new ItemType(TypeId, itmTyp);
        System.out.println("2 here: " + it.getItemType());
        ItemTypeServices it1 = new ItemTypeServices(con);
        System.out.println("here 3");
        it1.update(it);
        System.out.println("Item Type ID " + TypeId + " has been updated\n");
        AdminAndManager aam = new AdminAndManager(con);
        aam.adminScreen();

    }
    
    
    public static void deleteItemType(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Please enter the item type ID to be deleted");
        String id = sc.next();
        ItemTypeServices it1 = new ItemTypeServices(con);
        it1.deleteById(id);
        System.out.println(id + " Item Type ID has been successfully deleted");
        adminScreen();
    }
}
