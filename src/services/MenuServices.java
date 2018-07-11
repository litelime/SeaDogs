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

    public void deleteById(String id) {
        try {
            CallableStatement preStmt = con.prepareCall("call deleteItem(?)");
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

    public ArrayList<Menu> getByType(String type) {
        ArrayList<Menu> menArr = new ArrayList<Menu>();
        ArrayList<TimeSlots> times = timServ.getAll();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM items WHERE items_type_id = " + type);
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
            sm.setPrice(0);
            while (rs.next()) {
                sm.setId(rs.getString("special_id"));
                if (rs.getString("special_name") != null && !rs.getString("special_name").equals("")) {
                    sm.setName(rs.getString("special_name"));
                    if (rs.getString("special_description") != null && !rs.getString("special_description").equals("")) {
                        sm.setDescription(rs.getString("special_description"));
                    }
                    sm.setPrice(sm.getPrice() + rs.getInt("amount") * (100 - rs.getFloat("discount_percentage")) / 100);
                    for(int i = 0; i < rs.getInt("amount"); i++) {
                        sm.addItemId(rs.getString("item_id"));
                    }
                    String tid = rs.getString("time_slot_id");
                    String tName = getTimeName(times, tid);
                    sm.setPhoto(rs.getString("photo"));
                    sm.setVegetarian(rs.getString("vegetarian").charAt(0));
                }
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
                    + "ORDER BY special_id";
            ResultSet rs = con.createStatement().executeQuery(query);
            SpecialMenu sm = new SpecialMenu();
            sm.setPrice(0);
            while (rs.next()) {
                if (rs.getString("special_id").equals(id)) {
                    sm.setId(rs.getString("special_id"));
                    if (rs.getString("special_name") != null && !rs.getString("special_name").equals("")) {
                        sm.setName(rs.getString("special_name"));
                        if (rs.getString("special_description") != null && !rs.getString("special_description").equals("")) {
                            sm.setDescription(rs.getString("special_description"));
                        }
                        sm.setPrice(sm.getPrice() + rs.getInt("amount") * (100 - rs.getFloat("discount_percentage")) / 100);
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
            return sm;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;

    }
}
