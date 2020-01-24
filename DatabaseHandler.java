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
//Handles all the talking with the Database.
//All the methods in the class will create a own connection to the Database and will close it when its done.
	final private String url = "jdbc:mysql://localhost:3306/ships?useTimezone=true&serverTimezone=UTC";
	final String user = "fredde";
	final String pw = "fredde";
	
	List<Integer> selectAvailableShips(String port) {
		//Selects all the shipids that is in a specfic port.
		//Returns a list of all the ids
		Connection c;
		List<Integer> shipsId = new ArrayList<>();
		try {

			c = DriverManager.getConnection(url, user, pw);
			Statement stmt = c.createStatement();
			ResultSet rs;
			//MySql query
			rs = stmt.executeQuery(String.format("SELECT cargoships.id from ships.cargoships inner join ships.ports using (position) where ships.ports.name = '%s';", port));
			
			while(rs.next()) {
				shipsId.add(rs.getInt(1));			
			} 
			c.close();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shipsId;
	}
	static List<String> readDataLoop(ResultSet rs){
		
		List<String> allRows = new ArrayList<String>();
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int maxCol = metaData.getColumnCount();
			while(rs.next()) {
				String row = "";
				for(int i = 1; i <= maxCol; i++) {
					row += rs.getString(i) + ",";
				}
				row = row.substring(0, row.length() - 1);
				allRows.add(row);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return allRows;
	}
	String selectLastShip() {
		//Select the last ship in cargoships, and return the row as a string with , as a seperator.
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
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}
	void writeToDatabase(String name, String pos, String bearing, int cruiseKnots, int topKnots, int cargoCap, int cargoUnits){
		//Writes to cargoships and inserts a new cargoship.
		try {
			Connection c = DriverManager.getConnection(url, user, pw);
			
			PreparedStatement p = c.prepareStatement("INSERT INTO ships.cargoships(NAME, POSITION, BEARING, CRUISEKNOTS, TOPKNOTS, CARGOCAPACITY, CARGOUNITS)" +
			String.format("VALUES('%S','%S','%S','%S','%S','%S','%S')", name, pos, bearing, cruiseKnots, topKnots, cargoCap, cargoUnits));
			
			p.execute();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	String searchForShip(int id) {
		//Search for a shipid in Cargoship table.
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
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		row = "Didnt find a ship.";
		return row;
	}
	

	void writeToLog(int id, String startport, String destinationport, String table){
		//Writes to log. argument is log info and the table name.
		try {
			Connection c = DriverManager.getConnection(url, user, pw);

			PreparedStatement p = c.prepareStatement(String.format("INSERT INTO ships.%s(SHIPID,STARTPORT,DESTINATIONPORT)",table)+
			String.format("VALUES('%S','%S','%S')", id , startport, destinationport ));

			p.execute();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
			}
	}
	void deleteShipFromLog(int id) {
		//Delete a ship from jobinprocess.
		//Will be called when the ship gets to its destination.
		try {
			Connection c = DriverManager.getConnection(url, user, pw);
			PreparedStatement p = c.prepareStatement(String.format("DELETE FROM ships.jobsinprocess WHERE shipid = %S", id));
			p.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	List<String> readFromDb(String table){
		//Reads from the Database, argument is table name. 
		//Returns all the info in a list.
		List<String> allRows = new ArrayList<String>();
		try {
			Connection c = DriverManager.getConnection(url,user,pw);
			Statement stmt = c.createStatement();
			ResultSet rs;

			rs = stmt.executeQuery(String.format("SELECT * FROM ships.%s;", table));
			
			ResultSetMetaData metaData = rs.getMetaData();
			int maxCol = metaData.getColumnCount();
			allRows = readDataLoop(rs);
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return allRows;
	}
	
	void updatePosition(int id, int[] pos, String bearing) {
		//Updates the cargoships info in cargoships table.
		try {
			Connection c = DriverManager.getConnection(url,user,pw);
			String sPos = Integer.toString(pos[0]) + "," + Integer.toString(pos[1]);
			//We were having trouble if the y coordinat was 00 then it was saved as only one 0.
			//this is to prevent that.
			if(pos[0] == 0) {
				sPos = "00," + pos[1];
			}
			if(pos[1] == 0) {
				sPos = pos[0] + ",00";
			}
			if(pos[0] == 0 && pos[1] == 0) {
				sPos = "00,00";
			}
			PreparedStatement p = c.prepareStatement(String.format("UPDATE ships.cargoships set position = '%s', bearing = '%s' where id = %s;", sPos, bearing, id));
			p.execute();
			c.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	void updateCargoPort(String name, int cargoUnits, String symbol) {
		//Updates port table with how much cargo it should be.
		try {
			
			Connection c = DriverManager.getConnection(url,user,pw);
			PreparedStatement p = c.prepareStatement(String.format("UPDATE ships.ports set CARGO = cargo %s %s where name = '%s';", symbol, cargoUnits, name));
			p.execute();
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	void updateCargoShip(int id, int cargoUnits, String symbol) {
		//Updates cargoships table with how much cargo each ship as.
		try {
			Connection c = DriverManager.getConnection(url, user, pw);
			PreparedStatement p = c.prepareStatement(String.format("UPDATE ships.cargoships SET CARGOUNITS = CARGOUNITS %s %s WHERE ID = %s", symbol, cargoUnits, id));
			p.execute();
			c.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
