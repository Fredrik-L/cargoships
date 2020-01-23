package cargoships;

import java.util.*;

public class Port {
	private int id;
	private String name;
	private int[] position;  // Port position in the map
	private int cargoInPort; // Total current cargo units in a port
	private int shipsInPort; // No. of ships which already anchored in a port
	static DatabaseHandler db = new DatabaseHandler();
	//class constructor
	public Port(int[] position, int cargos, int ships, String name, int id) {
		
		this.id = id;
		this.position = position;
		this.cargoInPort = cargos;
		this.shipsInPort = ships;
		this.name = name;
	}
	
	public void setPortName(String name) {
		this.name = name;
	}
	
	public String getPortName() {
		return this.name;
	}
	public int getCargoInPort() {
		return this.cargoInPort;
	}
	public void loadCargo(int cargoUnits) {
		this.cargoInPort += cargoUnits;	
		db.updateCargoPort(this.name, cargoUnits, "+");
	}
	
	public void unLoadCargo(int cargoUnits) {
		this.cargoInPort -= cargoUnits;
		db.updateCargoPort(this.name, cargoUnits, "-");
	}
	
	// add a new anchored ship into the ship list at a port
	public void addShipInDock(int shipId) {
		this.shipsInPort++;
		//this.shipsId.add(shipId);
	}
	
	// remove a ship from the ship list, when it lefts a port
	public void dumpShipsFromPort() {
		this.shipsInPort--;
		/*int removeValue = this.shipsId.indexOf(shipId);
		this.shipsId.remove(removeValue);*/
	}
	
	// Return the no. of ships which anchored in a port
	public int getShipsInPort() {
		return this.shipsInPort;
	}
	
	public int[] getPortPosition() {
		return this.position;
	}
	
	public void setPortPosition(int[] position) {
		int[] copy = position.clone();
		this.position = copy;
	}
}
