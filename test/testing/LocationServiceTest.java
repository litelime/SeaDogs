/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import domain.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import services.LocationService;
import services.LocationService;
import static org.junit.Assert.*;
import static testing.DatabaseTestMethods.con;

/**
 *
 * @author syntel
 */
public class LocationServiceTest extends DatabaseTestMethods{
    LocationService instance;
    public LocationServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new LocationService(con);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of add method, of class LocationService.
     */
    @Test
    public void testAdd() {
        //Only works if there is no current location id = 999999
        Location location = new Location("999999", "2", "Street", "city", "state", "country", "zip");
        System.out.println("add");
        boolean expResult = true;
        boolean result = instance.add(location);
        assertEquals(expResult, result);
    }

    /**
     * Test of deleteById method, of class LocationService.
     */
    @Test
    public void testDeleteById() {
        System.out.println("deleteById");
        String id = "test";
        
        //Verified add works PRIOR to running the assertFalse statement below.
        instance.add(new Location("test", "", "test","test","test","test","test"));
        instance.deleteById(id);
        Statement statement;
		try {
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM LOCATIONS WHERE LOCATION_ID = 'test'");
                        //Verify there is nothing in the result set.
			assertFalse(rs.next());

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }

    /**
     * Test of getAll method, of class LocationService.
     */
    @Test
    public void testGetAll() {
        System.out.println("getAll");
        ArrayList<Location> expResult = new ArrayList<>();
        Statement statement;
		try {
			statement = con.createStatement();
                        //Get all locations, put them into array list.
			ResultSet rs = statement.executeQuery(
					"SELECT * FROM LOCATIONS");
                        while(rs.next()){
                            Location l = new Location(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                    rs.getString(5), rs.getString(6), rs.getString(7));
                            expResult.add(l);
                        }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
        
        ArrayList<Location> result = instance.getAll();
        assertTrue(result.toString().equalsIgnoreCase(expResult.toString()));
        
    }

    /**
     * Test of getById method, of class LocationService.
     */
    @Test
    public void testGetById() {
        System.out.println("getById");
        //Add a location to test.
        Location l = new Location("999999", "", "test","test","test","test","test");
        instance.add(l);
        String id = "999999";
        Location expResult = l;
        Location result = instance.getById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of update method, of class LocationService.
     */
//    @Test
//    public void testUpdate() {
//        System.out.println("update");
//        Location location = null;
//        LocationService instance = new LocationService();
//        instance.update(location);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getUserLocations method, of class LocationService.
//     */
//    @Test
//    public void testGetUserLocations() {
//        System.out.println("getUserLocations");
//        String userId = "";
//        LocationService instance = new LocationService();
//        ArrayList<Location> expResult = null;
//        ArrayList<Location> result = instance.getUserLocations(userId);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNextLocId method, of class LocationService.
//     */
//    @Test
//    public void testGetNextLocId() {
//        System.out.println("getNextLocId");
//        LocationService instance = new LocationService();
//        String expResult = "";
//        String result = instance.getNextLocId();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of removeManager method, of class LocationService.
//     */
//    @Test
//    public void testRemoveManager() {
//        System.out.println("removeManager");
//        String managerID = "";
//        LocationService instance = new LocationService();
//        instance.removeManager(managerID);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//    
}
