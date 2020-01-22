public class Ships {

	private boolean inDock;

	private String name,
				   bearing;

	private int[] position;

	private int cruiseKnots,
				topKnots,
				id;
	//Några get set metoder som behövs
	public String getBearing() {
		return bearing;
	}
	public void setBearing(String bearing) {
		this.bearing = bearing;
	}
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] position) {
		this.position = position;
	}
	public boolean getInDock() {
		return inDock;
	}

	public void setInDock(boolean inDock) {
		this.inDock = inDock;
	}


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

	public void moveShipNorth() {
		int[] nextPosition = {getPosition()[0] - 1, getPosition()[1]};
		setPosition(nextPosition);
		setBearing("N");
	}
	public  void moveShipNE() {
		int[] nextPosition = {getPosition()[0] - 1, getPosition()[1] + 1};
		setPosition(nextPosition);
		setBearing("NE");
	}
	public void moveShipSouth() {
		int[] nextPosition = {getPosition()[0] + 1, getPosition()[1]};
		setPosition(nextPosition);
		setBearing("S");
	}
	public  void moveShipSE() {
		int[] nextPosition = {getPosition()[0] + 1, getPosition()[1] + 1};
		setPosition(nextPosition);
		setBearing("SE");
	}
	public void moveShipWest() {
		int[] nextPosition = {getPosition()[0], getPosition()[1] -1};
		setPosition(nextPosition);
		setBearing("W");
	}
	public  void moveShipSW() {
		int[] nextPosition = {getPosition()[0] + 1, getPosition()[1] - 1};
		setPosition(nextPosition);
		setBearing("SW");
	}
	public void moveShipEast() {
		int[] nextPosition = {getPosition()[0], getPosition()[1] + 1};
		setPosition(nextPosition);
		setBearing("E");
	}
	public  void moveShipNW() {
		int[] nextPosition = {getPosition()[0] - 1, getPosition()[1] - 1};
		setPosition(nextPosition);
		setBearing("NW");
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
		else { //Detta sker När skeppet har samma koordinater som destinations hamnen
			setBearing("NONE");
			setInDock(true);

		}
		/* testade denna loop i Main för att se att det fungerar
		int [] STARTPOSITION = {0, 0};
		int [] DESTINATION = {15, 15};
		Cargoships cargoship1 = new Cargoships(true, "ms sofia", " ", STARTPOSITION, 32, 50, 1, 3000, 0);

		do {
			System.out.println(cargoship1.getPosition()[0] + " " + cargoship1.getPosition()[1] + " " + cargoship1.getBearing());
			cargoship1.navigateToPort(DESTINATION, cargoship1.getPosition());
			Thread.sleep(800);
		}while (!cargoship1.getInDock());

		*/

	}


}
