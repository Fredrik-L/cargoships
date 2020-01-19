public class CargoShip {
    private int id;
    private String name;
    private int weight; //Tons
    private int length; //Meter
    private String color;
    private int cargoOnBoard;//
    private int cargoCapacity;//Tons
    private int fuelRemaining; //percent %
    private String bearing;
    private int [] position;// coordinates x y
    private int [] previousPosition;// coordinates x y
    private int [] startPosition;
    private int max_speed; //Knots
    private boolean inDock = true;



    public CargoShip(int id, int weight, int length, String color, int cargoOnBoard, int cargoCapacity, int max_speed, int fuelRemaining, String bearing, int[] position, String name ) {
        this.id = id;
        this.weight = weight;
        this.length = length;
        this.color = color;
        this.cargoCapacity = cargoCapacity;
        this.fuelRemaining = fuelRemaining;
        this.bearing = bearing;
        this.position = position;
        this.max_speed = max_speed;
        this.cargoOnBoard = cargoOnBoard;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCargoOnBoard(int cargoOnBoard) {
        this.cargoOnBoard = cargoOnBoard;
    }
    public int getCargoOnBoard() {
        return cargoOnBoard;
    }
    public void setMax_speed(int max_speed) {
        this.max_speed = max_speed;
    }
    public int getMax_speed() {
        return max_speed;
    }
    public void setPosition(int[] position) {
        this.position = position;
    }
    public int[] getPosition() {
        return position;
    }

    public int[] getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(int[] previousPosition) {
        this.previousPosition = previousPosition;
    }

    public void setBearing(String bearing) {
        this.bearing = bearing;
    }

    public String getBearing() {
        return bearing;
    }

    public void setFuelRemaining(int fuelRemaining) {
        this.fuelRemaining = fuelRemaining;
    }

    public int getFuelRemaining() {
        return fuelRemaining;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public boolean isInDock() {
        return inDock;
    }
    public void setInDock(boolean inDock) {
        this.inDock = inDock;
    }
    public int[] getStartPosition() {
        return startPosition;
    }
    public void setStartPosition(int[] startPosition) {
        this.startPosition = startPosition;
    }

    public void loadCargo(int cargo) {
        setCargoOnBoard(getCargoOnBoard() + cargo);
    }
    public void unLoadCargo(int cargo) {
        setCargoOnBoard((getCargoOnBoard() - cargo));
    }

   //


    public void moveShipNorth(int [] shipPosition) {

        int[] destinationPosition = {getPosition()[0] - 1, getPosition()[1]};
        setPreviousPosition(getPosition());
        setPosition(destinationPosition);
        setBearing("North");
        }

        public void moveShipSouth(int [] shipPosition) {
        int[] destinationPosition = {getPosition()[0] + 1, getPosition()[1]};
        setPreviousPosition(getPosition());
        setPosition(destinationPosition);
            setBearing("South");
    }
        public void moveShipWest(int [] shipPosition) {
        int[] destinationPosition = {getPosition()[0], getPosition()[1] -1};
        setPreviousPosition(getPosition());
        setPosition(destinationPosition);
        setBearing("West");
    }
    public void moveShipEast(int [] shipPosition) {
        int[] destinationPosition = {getPosition()[0], getPosition()[1] + 1};
        setPreviousPosition(getPosition());
        setPosition(destinationPosition);
        setBearing("East");
    }
    public void navigateToPort(int [] portPosition, int [] shipPosition ) {
        if (portPosition[0] < shipPosition[0]) {
            moveShipNorth(getPosition());
        }
        else if (portPosition[0] > shipPosition[0]) {
            moveShipSouth(getPosition());
        }
        else if (portPosition[1] < shipPosition[1]) {
            moveShipWest(getPosition());
        }
        else if (portPosition[1] > shipPosition[1]) {
            moveShipEast(shipPosition);
        }
        if (portPosition == shipPosition) {

        }

    }





    }








