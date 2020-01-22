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

	final private String url = "jdbc:mysql://localhost:3306/ships";
	final String user = "fredde";
	final String pw = "fredde";
	
	List<Integer> selectAvailableShips(String port) {
		Connection c;
		List<Integer> shipsId = new ArrayList<>();
		try {
			c = DriverManager.getConnection(url, user, pw);
			Statement stmt = c.createStatement();
			ResultSet rs;
			
			rs = stmt.executeQuery(String.format("SELECT id from ships.cargoships inner join ships.ports using (position) where ships.ports.name = '%s';", port));
			
			ResultSetMetaData metaData = rs.getMetaData();
			int maxCol = metaData.getColumnCount();
			
			while(rs.next()) {
				shipsId.add(rs.getInt(1));			
			} 
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shipsId;

	}
	
	String selectLastShip() {
		String row = "";
		try {
			Connection c = DriverManager.getConnection(url, user, pw);
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
			Connection c = DriverManager.getConnection(url, user, pw);
			
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
			Connection c = DriverManager.getConnection(url, user, pw);
			
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
			Connection c = DriverManager.getConnection(url, user, pw);
			
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
			Connection c = DriverManager.getConnection(url, user, pw);
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
	void writeToTravelLog(int id, String startport, String destinationport ){
		try {
			Connection c = DriverManager.getConnection(url, user, pw);

			PreparedStatement p = c.prepareStatement("INSERT INTO ships.travellog(SHIPID,STARTPORT,DESTINATIONPORT)"+
			String.format("VALUES('%S','%S','%S')", id , startport, destinationport ));

			p.execute();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			}
	}
}
