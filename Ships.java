package cargoships;
public class Ships {
	private boolean inDock;
	private String name, sBearing;
	private int[] position;
	private int cruiseKnots,
			topKnots,
			id;
	static DatabaseHandler db = new DatabaseHandler();


	Ships(boolean inDock, String name, int[] position, int cruiseKnots, int topKnots, int id, String bearing){
		this.inDock = inDock;
		this.name = name;
		this.position = position;
		this.cruiseKnots = cruiseKnots;
		this.topKnots = topKnots;
		this.id = id;
		this.sBearing = bearing;
	}
	public String getsSBearing() {
		return this.sBearing;
	}
	public void setSBearing(String bearing) {
		this.sBearing = bearing;
	}
	public boolean getInDock() {
		return inDock;
	}

	public void setInDock(boolean inDock) {
		this.inDock = inDock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public int getCruiseKnots() {
		return cruiseKnots;
	}

	public void setCruiseKnots(int cruiseKnots) {
		this.cruiseKnots = cruiseKnots;
	}

	public int getTopKnots() {
		return topKnots;
	}

	public void setTopKnots(int topKnots) {
		this.topKnots = topKnots;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int[] getPosition() {
		return this.position;
	}
	public void setPosition(int[] position) {
		this.position = position;
	}


}
class Cargoships extends Ships{
	private final int cargoCapacity;
	private int cargoUnits;
	Cargoships(boolean inDock, String name, String bearing, int[] position, int cruiseKnots, int topKnots, int id, int cargoCap, int cargoUnits){

		super(inDock, name, position, cruiseKnots, topKnots, id, bearing);
		this.cargoCapacity = cargoCap;
		this.cargoUnits = cargoUnits;
		
	}
	private DIRECTION bearing = DIRECTION.NONE;
	public enum DIRECTION {
		NONE, N, NE, E, SE, S, SW, W, NW
	}
	public DIRECTION getBearing() {
		return this.bearing;
	}
	public void setBearing(DIRECTION bearing) {
		this.bearing = bearing;
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
		System.out.println("Unloaded: " + this.cargoUnits);
		db.updateCargoShip(this.getId(), unloadedCargo, "-");
		this.setCargoUnits(0);

		return unloadedCargo;
	}
	public void loadCargo(int cargoUnits) {
		this.setCargoUnits(cargoUnits);
		db.updateCargoShip(this.getId(), cargoUnits, "+");
		System.out.println("Loaded: " + cargoUnits);
	}
	public void moveShipNorth() {
		int[] nextPosition = {getPosition()[0] - 1, getPosition()[1]};
		setPosition(nextPosition);
		setBearing(DIRECTION.N);
	}
	public  void moveShipNE() {
		int[] nextPosition = {getPosition()[0] - 1, getPosition()[1] + 1};
		setPosition(nextPosition);
		setBearing(DIRECTION.NE);
	}
	public void moveShipSouth() {
		int[] nextPosition = {getPosition()[0] + 1, getPosition()[1]};
		setPosition(nextPosition);
		setBearing(DIRECTION.S);
	}
	public  void moveShipSE() {
		int[] nextPosition = {getPosition()[0] + 1, getPosition()[1] + 1};
		setPosition(nextPosition);
		setBearing(DIRECTION.SE);
	}
	public void moveShipWest() {
		int[] nextPosition = {getPosition()[0], getPosition()[1] -1};
		setPosition(nextPosition);
		setBearing(DIRECTION.W);
	}
	public  void moveShipSW() {
		int[] nextPosition = {getPosition()[0] + 1, getPosition()[1] - 1};
		setPosition(nextPosition);
		setBearing(DIRECTION.SW);
	}
	public void moveShipEast() {
		int[] nextPosition = {getPosition()[0], getPosition()[1] + 1};
		setPosition(nextPosition);
		setBearing(DIRECTION.E);
	}
	public  void moveShipNW() {
		int[] nextPosition = {getPosition()[0] - 1, getPosition()[1] - 1};
		setPosition(nextPosition);
		setBearing(DIRECTION.NW);
	}
	public void navigateToPort(int [] portPosition, int [] shipPosition ) {
		setInDock(false);
		if (portPosition[0] < shipPosition[0] && portPosition[1] < shipPosition[1]) {
			moveShipNW();
		}
		else if (portPosition[0] < shipPosition[0] && portPosition[1] > shipPosition[1]) {
			moveShipNE();
		}
		else if (portPosition[0] > shipPosition[0] && portPosition[1] > shipPosition[1]) {
			moveShipSE();
		}
		else if (portPosition[0] > shipPosition[0] && portPosition[1] < shipPosition[1]) {
			moveShipSW();
		}
		else if (portPosition[0] < shipPosition[0]) {
			moveShipNorth();
		}
		else if (portPosition[1] < shipPosition[1]) {
			moveShipWest();
		}
		else if (portPosition[1] > shipPosition[1]) {
			moveShipEast();
		}
		else if (portPosition[0] > shipPosition[0]) {
			moveShipSouth();
		}
		else {
			setBearing(DIRECTION.NONE);
			setInDock(true);
		}
		DatabaseHandler db = new DatabaseHandler();
		int id = this.getId();
		this.setSBearing(this.getBearing().toString());
		
		String tempBearing = this.getBearing().toString();
		db.updatePosition(id, this.getPosition(),tempBearing);
	}
}