package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Special;
import java.sql.Statement;
import java.util.Collections;

public class SpecialServices implements Service<Special> {

    Connection con;

    public SpecialServices(Connection con) {
        super();
        this.con = con;
    }

    public int newSpecialId() {
        try {
            // Ask for all the ids
            String query = "select item_id from specials";
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

    @Override
    public boolean add(Special spec) {
        CallableStatement oracleCallStmt;
        try {
            oracleCallStmt = con.prepareCall("{call sp_insert_Special(?,?)}");
            oracleCallStmt.setString(1, spec.getItem_ID());
            oracleCallStmt.setInt(2, spec.getDiscoutPercentage());
            oracleCallStmt.execute();
            System.out.println("Successful");
            con.close();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void deleteById(String id) {
        CallableStatement stmt;
        try {
            stmt = con.prepareCall("{call sp_delete_special(?)}");
            stmt.setString(1, id);
            stmt.execute();
            System.out.println("Deleted");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public Special getById(String id) {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM specials WHERE item_id = " + id);
            rs.next();

            Special spec = new Special(rs.getString("item_id"), rs.getInt("discount_percentatge"));
            return spec;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Special> getAll() {
        ArrayList<Special> specArr = new ArrayList<Special>();
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM specials");
            while (rs.next()) {
                Special spec = new Special(rs.getString("item_id"), rs.getInt("discount_percentage"));
                specArr.add(spec);
            }
            return specArr;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void update(Special spec) {
        CallableStatement stmt;
        try {
            stmt = con.prepareCall("{call sp_update_special(?,?)}");
            stmt.setString(1, spec.getItem_ID());
            stmt.setInt(2, spec.getDiscoutPercentage());
            stmt.execute();
            System.out.println("Updated");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }

    }

}
