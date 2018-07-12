package cli;

import static cli.Tiger.con;
import static cli.Tiger.currentOrder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.*;
import java.util.Comparator;

import services.MenuServices;
import services.OrderService;
import services.UserService;

public class ServiceWrapper {

    Connection con;

    public ServiceWrapper(Connection con) {
        super();
        this.con = con;

    }

    public User login(String email, String password) {

        UserService us = new UserService(con);
        User candidate = us.getByEmail(email);
        System.out.println(candidate.getFirstName());
        if (password.equals(candidate.getPassword())) {
            return candidate;
        } else {
            return null;
        }
    }

    public User register(String firstName, String lastName, String phone, String email, String password) {
        //, String street, String city, String state, String country, String zip, String userStatus
        boolean result = false;
        UserService us = new UserService(con);

        String userId = "" + us.newUserId();
        String userStatusId = "1";

        User user = new User(userId, firstName, lastName, phone, email, password, userStatusId);
        result = us.add(user);
        return user;
    }

    public static void printOptions(ArrayList<String> options) {
        options.add("Go back");
        int count = 0;
        for (String option : options) {
            count++;
            System.out.println(count + ". " + option);
        }

    }

    public static void printMenuItems(ArrayList<Menu> menus) {
        int count = 0;
        for (Menu menu : menus) {
            count++;
            System.out.println(count + ". $" + menu.getPrice() + " " + menu.getName());
        }
        System.out.println(++count + ". Go Back");
    }

    public static void printSpecialMenuItems(ArrayList<SpecialMenu> menus) {
        MenuServices ms = new MenuServices(Tiger.con);
        int count = 0;
        for (SpecialMenu menu : menus) {
            count++;
            System.out.printf("%d. $ %.2f %s\n",count, menu.getPrice() , menu.getName());

            ArrayList<String> idList = menu.getItemIds();
            Comparator<String> c = Comparator.comparing(String::toString);
            idList.sort(c);
            if (!idList.isEmpty()) {
                String curId = idList.get(0);
                //Tab so output can be read easier
                System.out.print("\t -");
                int amount = 1;
                for (int i = 0; i <= idList.size() - 1; i++) {
                    if (i == idList.size() - 1 || !idList.get(i + 1).equals(curId)) {
                        System.out.print(ms.getById(idList.get(i)).getName() + " " + amount);
                        amount = 1;
                        if (i != idList.size() - 1) {
                            System.out.print(", ");
                            curId = idList.get(i + 1);
                        } else {
                            System.out.print("\n");
                        }
                    } else {
                        amount += 1;
                    }
                }
            }
	}
        System.out.println(++count + ". Go Back");
    }
	public static void printOrders(ArrayList<Order> orders){
        int count = 0;

        for (Order order : orders) {
            count++;
            String orderStr = order.getItem_ids().size()+" items | Price $"
                               +order.getTotal_price()+" | Placed at "
                                +order.getPlaced_timestamp();
            System.out.println(count + ". " + orderStr);
        }
            System.out.println(++count + ". Go Back");
	}

	public void cancelOrder(Order order) {
		order.setDelivery_status_id("3");
		OrderService os = new OrderService(con);
		os.update(order);
	}

	public void submitOrder(Order currentOrder) {
		
		currentOrder.setDelivery_status_id("0");
		OrderService os = new OrderService(con);
		os.add(currentOrder);
		
	}

	public ArrayList<Menu> getMenuItems(ArrayList<String> itemIds) {
		
		MenuServices ms = new MenuServices(con);
		ArrayList<Menu> items = new ArrayList<Menu>();
		
		
		for (String itemId:itemIds){
			items.add(ms.getById(itemId));
		}

		return items;
	}
        
        public ArrayList<SpecialMenu> getSpecialMenuItems(ArrayList<String> itemIds) {
		
		MenuServices ms = new MenuServices(con);
		ArrayList<SpecialMenu> sms = new ArrayList<SpecialMenu>();
		
		
		for (String itemId:itemIds){
			sms.add(ms.getSpecialById(itemId));
		}

		return sms;
	}
        
	public float calculateTotalPrice(Order currentOrders) {
                ArrayList<String> item_ids = currentOrders.getItem_ids();
		float total = 0;
		ServiceWrapper sw = new ServiceWrapper(con);
		ArrayList<Menu> items = sw.getMenuItems(item_ids);
		for(Menu item: items){
			total += item.getPrice();
		}
                ArrayList<SpecialMenu> specials = sw.getSpecialMenuItems(currentOrders.getSpecial_ids());
		for(SpecialMenu sm: specials){
			total += sm.getPrice();
		}
                total+=currentOrders.getTip();
                
		return total;
	}

}
