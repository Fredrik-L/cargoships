package cargoships;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {

	String selectLastShip() {
		String row = "";
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC", "fredde", "fredde");
			Statement stmt = c.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery("SELECT * FROM ships.cargoships WHERE ID=(SELECT MAX(ID) FROM ships.cargoships)");
			ResultSetMetaData metaData = rs.getMetaData();
			int maxCol = metaData.getColumnCount();
			
			while(rs.next()) {
				for(int i = 1; i <= maxCol; i++) {
					row += rs.getString(i) + ",";
				}
				
				row = row.substring(0, row.length() - 1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}
	void writeToDatabase(String name, String pos, String bearing, int cruiseKnots, int topKnots, int cargoCap, int cargoWeight){
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC", "root", "123456789");
			
			PreparedStatement p = c.prepareStatement("INSERT INTO ships.cargoships(NAME, POSITION, BEARING, CRUISEKNOTS, TOPKNOTS, CARGOCAPACITY, CARGOWEIGHT)" +
			String.format("VALUES('%S','%S','%S','%S','%S','%S','%S')", name, pos, bearing, cruiseKnots, topKnots, cargoCap, cargoWeight));
			
			p.execute();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	List<String> readAllDatabase() {
		List<String> allShips = new ArrayList<String>(); 
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC", "root", "123456789");
			
			Statement stmt = c.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery("SELECT * FROM ships.cargoships");
			
			ResultSetMetaData metaData = rs.getMetaData();
			int maxCol = metaData.getColumnCount();
			
			while(rs.next()) {
				String row = "";
				
				for(int i = 1; i <= maxCol; i++) {
					row += rs.getString(i) + ",";
				}
				
				row = row.substring(0, row.length() - 1);
				allShips.add(row);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return allShips;
	}
	String searchForShip(int id) {
		String row = "";
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC", "root", "123456789");
			
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(String.format("SELECT * FROM ships.cargoships WHERE ID = %s;", id));
			
			while(rs.next()) {
				for (int i = 1; i <= 8; i++) {
					row += rs.getString(i) + ",";
				}
				row = row.substring(0, row.length() - 1);
				return row;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		row = "Didnt find a ship.";
		return row;
	}
	
	void writeToTravelLog(int id, String startport, String destinationport ){
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC", "root", "123456789");
			
			PreparedStatement p = c.prepareStatement("INSERT INTO ships.travellog(ID,STARTPORT,DESTINATIONPORT)"+
			String.format("VALUES('%S','%S','%S')", id , startport, destinationport ));
			
			
			p.execute();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			}
	}
		
	
	String selectTravelLog(int id) {
		String row = "";
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC", "root", "123456789");
			Statement stmt = c.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery(String.format("SELECT * FROM ships.travellog"));
			
			while(rs.next()) {
				for (int i = 1; i <= 8; i++) {
					row += rs.getString(i) + ",";
				}
				row = row.substring(0, row.length() - 1);
				return row;						
			}
		}
        catch(SQLException e) {
        	e.printStackTrace();
        	
        }
		row = "Didnt find a travellog.";
		return row;
	}
	
	/*void writeToPort(String name, String position, int ships, int cargo) {
		
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC", "root", "123456789");
			
			PreparedStatement p = c.prepareStatement("INSERT INTO ships.port(NAME,POSITION,SHIPS,CARGO)"+
			String.format("VALUES('%S','%S','%S','%S')", name , position, ships, cargo ));
			
			
			p.execute();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			}
	}*/
	
		void UpdateCargo(int cargo, String name) {
			try {
				Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC", "root", "123456789");
				
				PreparedStatement p = c.prepareStatement(
						String.format("Update ships.port set CARGO = %S where NAME = '%S';", cargo, name));
				
				p.execute();
				c.close();
				
				
		}
			catch(Exception e)
			{
				System.out.println(e);
			}
		
		}

			
			
		
		
	/*void writeJobToDb(String destinationPort, String startPort) {
		
	}
	int getShipsInPorts(String portName) {
		int shipsInPort = -1;
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC", "root", "123456789");
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(String.format("SELECT * from ships.port WHERE PORTNAME = '%s'", portName));
			while(rs.next()) {
				shipsInPort = Integer.parseInt(rs.getString(4));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shipsInPort;}*/
		
				
			
			
			public static void main(String[] args) {
				/*DatabaseHandler tl = new DatabaseHandler();
				tl.writeToTravelLog(2,"Skåne", "Stockholm");
			
				DatabaseHandler db = new DatabaseHandler();
				
				db.writeToDatabase("Cargoship", "SE", "S", 10, 15, 500, 50);
				*/
				DatabaseHandler pt = new DatabaseHandler();
				
				pt.UpdateCargo(1500,"STOCKHOLM");
				
			
		
		
	}
}
