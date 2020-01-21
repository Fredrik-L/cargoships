package cargoships;

public class Ships {

	private boolean inDock;
	private String name,
				   bearing;
	
	private int[] position;
	
	private int cruiseKnots,
				topKnots,
				id;
	
	Ships(boolean inDock, String name, String bearing, int[] position, int cruiseKnots, int topKnots, int id){
		this.inDock = inDock;
		this.name = name;
		this.bearing = bearing;
		this.position = position;
		this.cruiseKnots = cruiseKnots;
		this.topKnots = topKnots;
		this.id = id;
	}

}
class Cargoships extends Ships{
	private final int cargoCapacity;
	private int cargoUnits;
	Cargoships(boolean inDock, String name, String bearing, int[] position, int cruiseKnots, int topKnots, int id, int cargoCap, int cargoUnits){
		
		super(inDock, name, bearing, position, cruiseKnots, topKnots, id);
		this.cargoCapacity = cargoCap;
		this.cargoUnits = cargoUnits;
		System.out.println("SKAPAT E CARGO");
	}
	public int getCargoCapacity() {
		return this.cargoCapacity;
	}
	public void setCargoUnits(int units) {
		this.cargoUnits = units;
	}
	public int getCargoUnits() {
		return this.cargoUnits;
	}
	
	public int unloadCargo() {
		int unloadedCargo = this.cargoUnits;
		System.out.println("Unloaded: " + this.cargoCapacity);
	
		this.setCargoUnits(0);
		
		return unloadedCargo;
	}
	public void loadCargo(int cargoUnits) {
		this.setCargoUnits(cargoUnits);
		System.out.println("Loaded: " + cargoUnits);
	}
}
