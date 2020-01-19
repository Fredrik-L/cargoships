public class Port {
    private String name;
    private int [] position;//coordinates
    private int portCargo; //Tons
    private int shipsInDock;

    public Port(String name, int[] position, int cargo, int shipsInDock) {
        this.name = name;
        this.position = position;
        this.portCargo = cargo;
        this.shipsInDock = shipsInDock;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int[] getPosition() {
        return position;
    }
    public void setPosition(int[] position) {
        this.position = position;
    }
    public int getPortCargo() {
        return portCargo;
    }
    public void setPortCargo(int cargo) {
        this.portCargo = cargo;
    }
    public int getShipsInDock() {
        return shipsInDock;
    }
    public void setShipsInDock(int shipsInDock) {
        this.shipsInDock = shipsInDock;
    }

    public void  addShipInDock() {
        setShipsInDock(getShipsInDock() + 1);
    }
    public void removeShipFromDock() {
        setShipsInDock(getShipsInDock() - 1);
    }
    public void unloadCargoToPort(int cargo) {
        setPortCargo(getPortCargo() + cargo);
    }
    public void loadCargoFromPort(int cargo) {
        setPortCargo(getPortCargo() - cargo);
    }
}


