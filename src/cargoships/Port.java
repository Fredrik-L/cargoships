package cargoships;

public class Port {
	
	String name;
	int[] position;
	int cargoInPort;
	int shipsInPort;
	
	//class constructor
	public Port(int[] position, int cargoInPort, int shipsInPort, int shipsInDock) {
		
		this.position = position;
		this.cargoInPort = cargoInPort;
		this.shipsInPort = shipsInPort;
	}
		
	public void load() {
		
	}
	
	public void unLoad() {
		
	}
	
	public void addShipInDock() {
		this.shipsInPort++;
	}
	
	public void dumpShipsFromPort() {
		this.shipsInPort--;
	}
	
	public int getShipsInPort() {
		return this.shipsInPort;
	}
	
	public void setShipsInPort(int shipsInPort) {
		this.shipsInPort = shipsInPort;
	}
	
	public int[] getPortPosition() {
		return this.position;
	}
	
	public void setPortPosition(int[] position) {
		this.position = position;
	}
	
	public int getCargoInPort() {
		return this.cargoInPort;
	}
	
	public void setCargoInPort(int cargoInPort) {
		this.cargoInPort = cargoInPort;
		
	}
	
	
}
