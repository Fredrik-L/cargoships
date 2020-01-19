import java.awt.event.KeyEvent;
import java.security.Key;

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
    public int [] position;// coordinates x y
    private int max_speed; //Knots




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

    public void loadCargo(int cargo) {
        setCargoOnBoard(getCargoOnBoard() + cargo);
    }
    public void unLoadCargo(int cargo) {
        setCargoOnBoard((getCargoOnBoard() - cargo));
    }
   //


        public void moveShip(KeyEvent ke, CargoShip ship) {
        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
          int [] currentPosition = {ship.getPosition()[0] - 1,ship.getPosition()[1]};
          ship.setPosition(currentPosition);


        }
        if (ke.getKeyCode() == KeyEvent.VK_UP) {
            int [] currentPosition = {getPosition()[0] + 1,getPosition()[1]};
            setPosition(currentPosition);


        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            int [] currentPosition = {ship.getPosition()[0] - 1,ship.getPosition()[1]};
            ship.setPosition(currentPosition);

        }
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            int [] currentPosition = {ship.getPosition()[0] + 1,ship.getPosition()[1]};
            ship.setPosition(currentPosition);

        }
        }

    }








