package cargoships;

public class Port {
	private int id;
	private String name;
	private int[] position;  // Port position in the map
	private int cargoInPort; // Total current cargo units in a port
	static DatabaseHandler db = new DatabaseHandler();
	//class constructor
	public Port(int[] position, int cargos, String name, int id) {
		
		this.id = id;
		this.position = position;
		this.cargoInPort = cargos;
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
		//Load the caro and updates the database.
		this.cargoInPort += cargoUnits;	
		db.updateCargoPort(this.name, cargoUnits, "+");
	}
	
	public void unLoadCargo(int cargoUnits) {
		//Unloads the cargo and updates the database
		this.cargoInPort -= cargoUnits;
		db.updateCargoPort(this.name, cargoUnits, "-");
	}
	
	public int[] getPortPosition() {
		int[] copy = this.position.clone();
		return copy;
	}
	
	public void setPortPosition(int[] position) {
		int[] copy = position.clone();
		this.position = copy;
	}
}
