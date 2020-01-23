
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
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships", "fredde", "fredde");
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
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships", "fredde", "fredde");
			
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
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships", "fredde", "fredde");
			
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
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships", "fredde", "fredde");
			
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
	void writeJobToDb(String destinationPort, String startPort) {
		
	}
	int getShipsInPorts(String portName) {
		int shipsInPort = -1;
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships", "fredde", "fredde");
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
		return shipsInPort;
		
	}
}
