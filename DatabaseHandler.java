package cargoships;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHandler {

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
	void readAllDatabase() {
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
				System.out.println(row);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	void searchForShip(int id) {
		try {
			Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ships", "fredde", "fredde");
			
			Statement stmt = c.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery(String.format("SELECT * FROM ships.cargoships WHERE ID = %s;", id));
			
			while(rs.next()) {
				String row = "";
				for (int i = 1; i <= 8; i++) {
					row += rs.getString(i) + ",";
				}
				row = row.substring(0, row.length() - 1);
				System.out.println(row);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		DatabaseHandler db = new DatabaseHandler();
		db.writeToDatabase("danne", "24.45", "North", 12,15,500,200);
		db.readAllDatabase();
		db.searchForShip(2);
	}
	

}
