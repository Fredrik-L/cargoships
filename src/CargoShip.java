public class CargoShip {
    private int id;
    private int weight;
    private int length;
    private String color;
    private int cargoCapacity;
    private int fuelRemaining;
    private String bearing;
    private int [] position;

    public CargoShip(int id, int weight, int length, String color, int cargoCapacity, int fuelRemaining, String bearing, int[] position) {
        this.id = id;
        this.weight = weight;
        this.length = length;
        this.color = color;
        this.cargoCapacity = cargoCapacity;
        this.fuelRemaining = fuelRemaining;
        this.bearing = bearing;
        this.position = position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getPosition() {
        return position;
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
}





