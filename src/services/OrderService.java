package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Order;
import domain.Special;
import domain.SpecialMenu;
import java.sql.DriverManager;
import java.time.LocalTime;
import java.util.Collections;

public class OrderService implements Service<Order> {

    /*
	ArrayList<Integer> item_ids = new ArrayList<Integer>();
     */

    Connection connection;

    public OrderService() {
        super();
    }

    public OrderService(Connection connection) {
        super();
        this.connection = connection;
    }

    @Override
    public boolean add(Order order) {
        try {
            //Add order items
            CallableStatement statement = connection.prepareCall(
                    "{call AddOrder(?,?,?,?,?,?,?,?,?,?,?)}");

            statement.setString(1, order.getOrder_id());
            statement.setString(2, order.getUser_id());
            statement.setFloat(3, order.getTip());
            statement.setFloat(4, order.getTotal_price());
            statement.setInt(5, order.getPlaced_timestamp().toSecondOfDay());
            statement.setInt(6, order.getDelivery_timestamp().toSecondOfDay());
            statement.setString(7, order.getCard_id());
            statement.setString(8, order.getInstuctions());
            statement.setString(9, order.getDelivery_method_id());
            statement.setString(10, order.getStore_id());
            statement.setString(11, order.getDelivery_status_id());
            statement.execute();
            statement.close();

            
            //Add all items in order to order_items
            ArrayList<String> item_ids = order.getItem_ids();
            ArrayList<String> special_ids = order.getSpecial_ids();
            SpecialServices SS = new SpecialServices(connection);
            
            for(String item_id : special_ids){
                
                statement = connection.prepareCall(
                        "{call AddOrderItem(?,?)}");
                statement.setString(1, order.getOrder_id());
                statement.setString(2, item_id);
                statement.execute();
                statement.close();
                
            }
            for (String item_id : item_ids) {
                statement = connection.prepareCall(
                        "{call AddOrderItem(?,?)}");
                statement.setString(1, order.getOrder_id());
                statement.setString(2, item_id);
                statement.execute();
                statement.close();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public void deleteById(String id) {
        try {
            //Delete order_items
            CallableStatement statement = connection.prepareCall(
                    "{call DeleteOrderItems(?)}");
            statement.setString(1, id);
            statement.execute();
            statement.close();

            //Delete order 
            statement = connection.prepareCall(
                    "{call DeleteOrder(?)}");

            statement.setString(1, id);
            statement.execute();
            statement.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Order> getAll() {
        ArrayList<Order> orders = new ArrayList<Order>();
        Order order;
        ArrayList<String> order_items = new ArrayList<String>();
        try {
            //Get Order
            Statement statement = connection.createStatement();
            ResultSet resultSetOrders = statement.executeQuery("SELECT * FROM ORDERS");

            ResultSet resultSetItems;
            while (resultSetOrders.next()) {
                //fetch all order items
                statement = connection.createStatement();
                resultSetItems = statement.executeQuery(
                        "SELECT * FROM ORDER_ITEMS WHERE ORDER_ID = " + resultSetOrders.getString("ORDER_ID"));
                order_items.clear();
                while (resultSetItems.next()) {
                    order_items.add(resultSetItems.getString("ITEM_ID"));
                }

                //Make new order
                order = new Order(resultSetOrders.getString("ORDER_ID"),
                        resultSetOrders.getString("USER_ID"),
                        resultSetOrders.getFloat("TIP"),
                        resultSetOrders.getFloat("TOTAL_PRICE"),
                        LocalTime.ofSecondOfDay(resultSetOrders.getInt("PLACED_TIMESTAMP")),
                        LocalTime.ofSecondOfDay(resultSetOrders.getInt("DELIVERY_TIMESTAMP")),
                        resultSetOrders.getString("CARD_ID"),
                        resultSetOrders.getString("INSTRUCTIONS"),
                        resultSetOrders.getString("DELIVERY_METHOD_ID"),
                        resultSetOrders.getString("STORE_ID"),
                        resultSetOrders.getString("DELIVERY_STATUS_ID"),
                        order_items);
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return orders;
    }
    
    public void updateAdmin(Order order) {

        try {
            //Add order items
            CallableStatement statement = connection.prepareCall(
                    "{call UPDATE_ORDER_ADMIN(?,?,?,?,?,?,?)}");

            statement.setString(1, order.getOrder_id());
            statement.setFloat(2, order.getTip());
            statement.setFloat(3, order.getTotal_price());
            statement.setString(4, order.getCard_id());
            statement.setString(5, order.getInstuctions());
            statement.setString(6, order.getDelivery_method_id());
            statement.setString(7, order.getDelivery_status_id());

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void update(Order order) {

        try {
            //Add order items
            CallableStatement statement = connection.prepareCall(
                    "{call UpdateOrder(?,?,?,?,?,?,?,?,?,?,?)}");

//            statement.setString("ORDER_ID", order.getOrder_id());
//            statement.setString("USER_ID", order.getUser_id());
//            statement.setFloat("TIP", order.getTip());
//            statement.setFloat("TOTAL_PRICE", order.getTotal_price());
//            statement.setInt("PLACED_TIMESTAMP", order.getPlaced_timestamp().toSecondOfDay());
//            statement.setInt("DELIVERY_TIMESTAMP", order.getDelivery_timestamp().toSecondOfDay());
//            statement.setString("CARD_ID", order.getCard_id());
//            statement.setString("INSTRUCTIONS", order.getInstuctions());
//            statement.setString("DELIVERY_METHOD_ID", order.getDelivery_method_id());
//            statement.setString("STORE_ID", order.getStore_id());
//            statement.setString("DELIVERY_STATUS_ID", order.getDelivery_status_id());
            statement.setString(1, order.getOrder_id());
            statement.setString(2, order.getUser_id());
            statement.setFloat(3, order.getTip());
            statement.setFloat(4, order.getTotal_price());
            statement.setInt(5, order.getPlaced_timestamp().toSecondOfDay());
            statement.setInt(6, order.getDelivery_timestamp().toSecondOfDay());
            statement.setString(7, order.getCard_id());
            statement.setString(8, order.getInstuctions());
            statement.setString(9, order.getDelivery_method_id());
            statement.setString(10, order.getStore_id());
            statement.setString(11, order.getDelivery_status_id());
            statement.execute();
            statement.close();

            //remove all items from order_items 
            statement = connection.prepareCall(
                    "{call DeleteOrderItems(?)}");
            statement.setString("ORDER_ID", order.getOrder_id());
            statement.execute();
            statement.close();

            //Add all items in order to order_items
            ArrayList<String> item_ids = order.getItem_ids();
            for (String item_id : item_ids) {
                statement = connection.prepareCall(
                        "{call AddOrderItem(?,?)}");
                statement.setString(1, order.getOrder_id());
                statement.setString(2, item_id);
                statement.execute();
                statement.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Order getById(String id) {
        Order order = new Order();
        try {
            //Get Order
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM ORDERS WHERE ORDER_ID = " + id);
            resultSet.next();
            order.setOrder_id(resultSet.getString("ORDER_ID"));
            order.setUser_id(resultSet.getString("USER_ID"));
            order.setTip(resultSet.getFloat("TIP"));
            order.setTip(resultSet.getFloat("TOTAL_PRICE"));
            order.setPlaced_timestamp(LocalTime.ofSecondOfDay(resultSet.getInt("PLACED_TIMESTAMP")));
            order.setDelivery_timestamp(LocalTime.ofSecondOfDay(resultSet.getInt("DELIVERY_TIMESTAMP")));
            order.setCard_id(resultSet.getString("CARD_ID"));
            order.setInstuctions(resultSet.getString("INSTRUCTIONS"));
            order.setDelivery_method_id(resultSet.getString("DELIVERY_METHOD_ID"));
            order.setStore_id(resultSet.getString("STORE_ID"));
            order.setDelivery_status_id(resultSet.getString("DELIVERY_STATUS_ID"));

            //get order items
            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT * FROM ORDER_ITEMS WHERE ORDER_ID = " + id);

            ArrayList<String> order_items = new ArrayList<String>();
            while (resultSet.next()) {
                order_items.add(resultSet.getString("ITEM_ID"));
            }
            order.setItem_ids(order_items);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return order;
    }

    public String getNextOrderId() {

        try {
            // Ask for all the ids
            String query = "select order_id from orders";
            Statement stmnt = connection.createStatement();
            stmnt.execute(query);

            // Collect the ids
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ResultSet results = stmnt.getResultSet();
            while (results.next()) {
                ids.add(Integer.parseInt(results.getString(1)));
            }

            // Generate a new id
            if (ids.isEmpty()) {
                return "0";
            }
            return ""+(Collections.max(ids) + 1);
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
            return "-1";
        }
    }
        
	public ArrayList<Order> getUserOrders(String userId){
		ArrayList<Order> orders = new ArrayList<Order>();
		Order order;
		ArrayList<String> order_items = new ArrayList<String>();
		try{
			//Get Order
			Statement statement = connection.createStatement();
			ResultSet resultSetOrders = statement.executeQuery("SELECT * FROM ORDERS WHERE USER_ID = '" + userId + "'");
			
			ResultSet resultSetItems;
			while(resultSetOrders.next()){
				//fetch all order items
				statement = connection.createStatement();
				resultSetItems = statement.executeQuery(
						"SELECT * FROM ORDER_ITEMS WHERE ORDER_ID = " + resultSetOrders.getString("ORDER_ID"));
				order_items.clear();
				while(resultSetItems.next()){
					order_items.add(resultSetItems.getString("ITEM_ID"));
				}
				
				//Make new order
				order = new Order(resultSetOrders.getString("ORDER_ID"),
						resultSetOrders.getString("USER_ID"),
						resultSetOrders.getFloat("TIP"),
						resultSetOrders.getFloat("TOTAL_PRICE"),
						LocalTime.ofSecondOfDay(resultSetOrders.getInt("PLACED_TIMESTAMP")),
						LocalTime.ofSecondOfDay(resultSetOrders.getInt("DELIVERY_TIMESTAMP")),
						resultSetOrders.getString("CARD_ID"),
						resultSetOrders.getString("INSTRUCTIONS"),
						resultSetOrders.getString("DELIVERY_METHOD_ID"),
						resultSetOrders.getString("STORE_ID"),
						resultSetOrders.getString("DELIVERY_STATUS_ID"),
						order_items);
				orders.add(order);
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return orders;

	}
        
        public void generateInvoice(String Order_ID){
            
            PreparedStatement pStmt;
            ResultSet RS;
            try{
                pStmt=connection.prepareStatement("SELECT S.store, S.PHONE_NUMBER, O.order_id, U.first, U.last, U.phone, U.email, I.name, I.description, I.price, O.TOTAL_PRICE, C.CARD_NUMBER "
                                       +"FROM orders O, USERS U, Order_items OI, stores S, cards C, items I WHERE "
                                       + "O.order_id=OI.ORDER_ID AND OI.ITEM_ID=I.ITEM_ID AND U.user_id=O.user_id AND O.Store_id=S.STORE_ID AND O.card_id=C.CARD_ID AND O.order_id=?");
                pStmt.setString(1, Order_ID);
                pStmt.execute();
                //Step4: get the output ResultSet
                RS=pStmt.getResultSet();

                RS.next();

                System.out.println("Thank You for the Order");
                System.out.println("                                      INVOICE");
                System.out.println("STORE DETAILS");
                System.out.println(" Store-- "+RS.getString(1)+"                     Store Phone Number-- "+RS.getString(2));
                System.out.println(" ");
                System.out.println("CUSTOMER and ORDER DETAILS");
                System.out.println(" Order ID-- "+RS.getString(3)+"     Total Amount-- "+RS.getString(11));
                System.out.println(" Name--"+RS.getString(4)+" "+RS.getString(5)+"     Phone Number-- "+RS.getString(6)+"     Email-- "+RS.getString(7)+"     Payment Card Number-- "+RS.getString(12));
                System.out.println(" ");
                System.out.println("Items Ordered under Order no. 1 ");
                System.out.println(" Item No. 1-- "+RS.getString(8)+
                            "     Item Description-- "+RS.getString(9)+"     Item Price-- "+RS.getString(10));
                int i=2;
                while(RS.next()){
                    System.out.println(" Item No. "+i+"-- "+RS.getString(8)+
                            "     Item Description-- "+RS.getString(9)+"     Item Price-- "+RS.getString(10));
                    i++;
                }
                //Step5: close statement and connection
                pStmt.close();

            }catch (SQLException e){
                System.out.println(e.getMessage());
        }

    }

    public void addItem_id(String item_id, String order_id) {
        try {
            CallableStatement statement = connection.prepareCall(
                    "{call AddOrderItem(?,?)}");
            statement.setString(1, order_id);
            statement.setString(2, item_id);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
