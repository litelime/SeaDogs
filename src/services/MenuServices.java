package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Menu;
import domain.SpecialMenu;
import domain.TimeSlots;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.Collections;

public class MenuServices implements Service<Menu> {

    Connection con;
    TimeSlotServices timServ;

    public MenuServices(Connection con) {
        super();
        this.con = con;
        timServ = new TimeSlotServices(con);
    }

    @Override
    public Menu getById(String id) {
        try {

            PreparedStatement preparedStatement = con.prepareStatement(
                    "SELECT * FROM items WHERE item_id = ?");
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            rs.next();
            //float price = rs.getFloat("price");
            //String time = getTimeName(times, rs.getString("time_slot_id"));
            Menu men = new Menu(rs.getString("item_id"),
                    rs.getString("name"),
                    rs.getString("vegetarian").charAt(0),
                    rs.getString("item_type_id"),
                    rs.getString("description"),
                    rs.getString("time_slot_id"),
                    rs.getString("photo"),
                    rs.getFloat("price"));

            return men;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }
    
    @Override
    public ArrayList<Menu> getAll() {
        ArrayList<Menu> menArr = new ArrayList<Menu>();
        ArrayList<TimeSlots> times = timServ.getAll();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM items");
            while (rs.next()) {
                float price = rs.getFloat("price");
                String tid = rs.getString("time_slot_id");
                String tName = getTimeName(times, tid);
                Menu men = new Menu(rs.getString("item_id"), rs.getString("name"), rs.getString("vegetarian").charAt(0),
                        rs.getString("item_type_id"), rs.getString("description"), tName,
                        rs.getString("photo"), price);
                menArr.add(men);
            }
            return menArr;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }


    @Override
    public boolean add(Menu men) {
        ArrayList<TimeSlots> times = timServ.getAll();
        String timeId = getTimeID(times, men.getSlot_ID());
        try {
            CallableStatement preStmt = con.prepareCall("call insertItem (?,?,?,?,?,?,?,?)");
            preStmt.setString(1, men.getId());
            preStmt.setString(2, men.getName());
            preStmt.setString(3, ("" + men.getVegetarian()));
            preStmt.setString(4, men.getType());
            preStmt.setString(5, men.getDescription());
            preStmt.setString(6, timeId);
            preStmt.setString(7, men.getPhoto());
            preStmt.setFloat(8, men.getPrice());
            preStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean addSpecial(SpecialMenu men, String id, int amount) {
        ArrayList<TimeSlots> times = timServ.getAll();
        String timeId = getTimeID(times, men.getSlot_ID());
        try {
            CallableStatement preStmt = con.prepareCall("call sp_insert_Special (?,?,?,?,?,?,?,?,?)");
            preStmt.setString(1, id);
            preStmt.setFloat(2, men.getDiscount());
            preStmt.setString(3, men.getId());
            preStmt.setString(4, men.getName());
            preStmt.setString(5, men.getDescription());
            preStmt.setInt(6, amount);
            preStmt.setString(7, men.getPhoto());
            preStmt.setString(8, ""+men.getVegetarian());
            preStmt.setString(9, timeId);
            preStmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    @Override
    public void deleteById(String id) {
        try {
            CallableStatement preStmt = con.prepareCall("call deleteItem(?)");
            preStmt.setString(1, id);
            preStmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteSpecialById(String id) {
        try {
            CallableStatement preStmt = con.prepareCall("call sp_delete_special(?)");
            preStmt.setString(1, id);
            preStmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void update(Menu men) {
        ArrayList<TimeSlots> times = timServ.getAll();
        String timeId = getTimeID(times, men.getSlot_ID());
        try {
            CallableStatement preStmt = con.prepareCall("call updateItem (?,?,?,?,?,?,?,?)");
            preStmt.setString(1, men.getId());
            preStmt.setString(2, men.getName());
            preStmt.setString(3, ("" + men.getVegetarian()));
            preStmt.setString(4, men.getType());
            preStmt.setString(5, men.getDescription());
            preStmt.setString(6, timeId);
            preStmt.setString(7, men.getPhoto());
            preStmt.setFloat(8, men.getPrice());
            preStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateSpecial(SpecialMenu men) {
        ArrayList<TimeSlots> times = timServ.getAll();
        String timeId = getTimeID(times, men.getSlot_ID());
        try {
            CallableStatement preStmt = con.prepareCall("call sp_update_special (?,?,?,?,?,?,?,?,?)");
            for (String i:men.getUniqueItemId()) {
                preStmt.setString(1, i);
                preStmt.setFloat(2, men.getDiscount());
                preStmt.setString(3, men.getId());
                preStmt.setString(4, men.getName());
                preStmt.setString(5, men.getDescription());
                preStmt.setInt(6, men.countItemsById(i));
                preStmt.setString(7, men.getPhoto());
                preStmt.setString(8, "" + men.getVegetarian());
                preStmt.setString(9, timeId);
                preStmt.executeUpdate();
            }
            preStmt = con.prepareCall("call sp_delete_special_item (?,?)");
            for (String i:men.getUniqueItemId()) {
                if (men.countItemsById(i) == 0) {
                    preStmt.setString(1,  men.getId());
                    preStmt.setString(2, i);
                    preStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Menu> getByType(String type) {
        ArrayList<Menu> menArr = new ArrayList<Menu>();
        ArrayList<TimeSlots> times = timServ.getAll();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM items WHERE item_type_id = " + type);
            while (rs.next()) {
                float price = rs.getFloat("price");
                String tid = rs.getString("time_slot_id");
                String tName = getTimeName(times, tid);
                Menu men = new Menu(rs.getString("item_id"), rs.getString("name"), rs.getString("vegetarian").charAt(0),
                        rs.getString("item_type_id"), rs.getString("description"), tName,
                        rs.getString("photo"), price);
                menArr.add(men);
            }
            return menArr;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public int getNextItemId() {
        try {
            // Ask for all the ids
            String query = "select item_id from items";
            Statement stmnt = con.createStatement();
            stmnt.execute(query);

            // Collect the ids
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ResultSet results = stmnt.getResultSet();
            while (results.next()) {
                ids.add(Integer.parseInt(results.getString(1)));
            }

            // Generate a new id
            if (ids.isEmpty()) {
                return 0;
            }
            return Collections.max(ids) + 1;
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
            return -1;
        }
    }
    
    public int getNextSpecialId() {
        try {
            // Ask for all the ids
            String query = "select special_id from specials";
            Statement stmnt = con.createStatement();
            stmnt.execute(query);

            // Collect the ids
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ResultSet results = stmnt.getResultSet();
            while (results.next()) {
                ids.add(Integer.parseInt(results.getString(1)));
            }

            // Generate a new id
            if (ids.isEmpty()) {
                return 0;
            }
            return Collections.max(ids) + 1;
        } catch (NumberFormatException | SQLException e) {
            System.out.println(e.getMessage());
            System.exit(1);
            return -1;
        }
    }

    private String getTimeName(ArrayList<TimeSlots> timeArr, String id) {
        String tName = "";
        for (TimeSlots tim : timeArr) {
            if (tim.getSlot_ID().equals(id)) {
                tName = tim.getTimeName();
            }
        }
        return tName;
    }

    private String getTimeID(ArrayList<TimeSlots> timeArr, String name) {
        String id = "";
        for (TimeSlots tim : timeArr) {
            if (tim.getTimeName().equals(name)) {
                id = tim.getSlot_ID();
            }
        }
        return id;
    }

    public ArrayList<SpecialMenu> getAllSpecials() {
        ArrayList<SpecialMenu> menArr = new ArrayList<SpecialMenu>();
        ArrayList<TimeSlots> times = timServ.getAll();
        try {
            String query = "SELECT * FROM specials s, items i "
                    + "WHERE s.item_id = i.item_id "
                    + "ORDER BY special_id";
            ResultSet rs = con.createStatement().executeQuery(query);
            SpecialMenu sm = new SpecialMenu();
            float discount = 15;
            sm.setPrice(0);
            while (rs.next()) {
                discount = rs.getInt("discount_percentage");
                //First case
                if (sm.getId().equals("")) {
                    sm.setId(rs.getString("special_id"));
                }
                //if this is a new special
                if (!sm.getId().equals(rs.getString("special_id"))) {
                    menArr.add(sm);
                    sm = new SpecialMenu();
                    sm.setId(rs.getString("special_id"));
                }
                if (sm.getId().equals(rs.getString("special_id"))) {
                    if (rs.getString("special_name") != null && !rs.getString("special_name").equals("")) {
                        sm.setName(rs.getString("special_name"));
                        if (rs.getString("special_description") != null && !rs.getString("special_description").equals("")) {
                            sm.setDescription(rs.getString("special_description"));
                        }
                        
                        sm.setDiscount(rs.getFloat("discount_percentage"));
    
                        for(int i = 0; i < rs.getInt("amount"); i++) {
                            sm.addItemId(rs.getString("item_id"));
                        }
                        discount = rs.getFloat("discount_percentage");
                        String tid = rs.getString("time_slot_id");
                        String tName = getTimeName(times, tid);
                        sm.setPhoto(rs.getString("photo"));
                        sm.setVegetarian(rs.getString("vegetarian").charAt(0));
                    }
                }
                
            }
            if (!sm.getId().equals("")) {
                sm.setPrice(sm.getPrice()* (1.0f - discount/100));
                menArr.add(sm);
            }

            return menArr;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }

    public SpecialMenu getSpecialById(String id) {
        ArrayList<TimeSlots> times = timServ.getAll();
        try {
            String query = "SELECT * FROM specials s, items i "
                    + "WHERE s.item_id = i.item_id and "
                    + "special_id = " + id;
            ResultSet rs = con.createStatement().executeQuery(query);
            SpecialMenu sm = new SpecialMenu();
            float discount = 15.0f;
            sm.setPrice(0);
       
            while (rs.next()) {
                discount = rs.getFloat("discount_percentage");
                if (rs.getString("special_id").equals(id)) {
                    sm.setId(rs.getString("special_id"));
                    if (rs.getString("special_name") != null && !rs.getString("special_name").equals("")) {
                        sm.setName(rs.getString("special_name"));
                        if (rs.getString("special_description") != null && !rs.getString("special_description").equals("")) {
                            sm.setDescription(rs.getString("special_description"));
                        }

                        sm.setDiscount(rs.getFloat("discount_percentage"));

                        for(int i = 0; i < rs.getInt("amount"); i++) {
                            sm.addItemId(rs.getString("item_id"));
                        }
                        
                        String tid = rs.getString("time_slot_id");
                        String tName = getTimeName(times, tid);
                        sm.setPhoto(rs.getString("photo"));
                        sm.setVegetarian(rs.getString("vegetarian").charAt(0));
                    }
                }
            }
            sm.setPrice(sm.getPrice() * (1 - discount / 100.0f));

            return sm;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }
}
