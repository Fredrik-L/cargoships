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
	public void moveShipNorth() {

		int[] destinationPosition = {getPosition()[0] - 1, getPosition()[1]};
		setPosition(destinationPosition);
		this.bearing = DIRECTION.N;
	}
	public  void moveShipNE() {

		int[] destinationPosition = {getPosition()[0] - 1, getPosition()[1] + 1};
		setPosition(destinationPosition);
		this.bearing = DIRECTION.NE;
	}

	public void moveShipSouth() {
		int[] destinationPosition = {getPosition()[0] + 1, getPosition()[1]};
		setPreviousPosition(getPosition());
		setPosition(destinationPosition);
		this.bearing = DIRECTION.S;
	}
	public  void moveShipSE() {
		int[] destinationPosition = {getPosition()[0] + 1, getPosition()[1] + 1};
		setPosition(destinationPosition);
		this.bearing = DIRECTION.SE;
	}
	public void moveShipWest() {
		int[] destinationPosition = {getPosition()[0], getPosition()[1] -1};
		setPreviousPosition(getPosition());
		setPosition(destinationPosition);
		this.bearing = DIRECTION.W;
	}
	public  void moveShipSW() {
		int[] destinationPosition = {getPosition()[0] + 1, getPosition()[1] - 1};
		setPosition(destinationPosition);
		this.bearing = DIRECTION.SW;
	}
	public void moveShipEast() {
		int[] destinationPosition = {getPosition()[0], getPosition()[1] + 1};
		setPreviousPosition(getPosition());
		setPosition(destinationPosition);
		this.bearing = DIRECTION.E;
	}
	public  void moveShipNW() {

		int[] destinationPosition = {getPosition()[0] - 1, getPosition()[1] - 1};
		setPosition(destinationPosition);
		this.bearing = DIRECTION.NW;
	}

	public void navigateToPort(int [] portPosition, int [] shipPosition ) {
		if (portPosition[0] < shipPosition[0] && portPosition[1] < shipPosition[1]) {
			moveShipNW();
		}
		if (portPosition[0] < shipPosition[0] && portPosition[1] > shipPosition[1]) {
			moveShipNE();
		}
		if (portPosition[0] > shipPosition[0] && portPosition[1] > shipPosition[1]) {
			moveShipSE();
		}
		if (portPosition[0] > shipPosition[0] && portPosition[1] < shipPosition[1]) {
			moveShipSW();

			if (portPosition[0] < shipPosition[0]) {
				moveShipNorth();
			} else if (portPosition[1] < shipPosition[1]) {
				moveShipWest();
			} else if (portPosition[1] > shipPosition[1]) {
				moveShipEast();
			} else if (portPosition[0] > shipPosition[0]) {
				moveShipSouth();
			}
			if (portPosition == shipPosition) {
				setInDock(true);

			}

		}


	}
}
}
