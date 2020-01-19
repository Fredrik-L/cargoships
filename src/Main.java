import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) throws InterruptedException {

         int [] HOME_PORT_COORDINATES = {0, 29};
         int [] NORTH_WEST_PORT_COORDINATES = {0, 0};
         int [] SOUTH_WEST_PORT_COORDINATES = {27, 0};
         int [] SOUTH_EAST_PORT_COORDINATES = {27, 29};

        CargoShip ship1 = new CargoShip(1,32, 300, "black",0,20, 45, 100,"north", new int[]{12,11 }, "ms_Sofia");
        CargoShip ship2 = new CargoShip(2,32, 300, "black",0,20, 45, 100,"north", new int[]{3, 23}, "ms_Sonja");
        Port northEast = new Port("North East", HOME_PORT_COORDINATES, 300000, 0);
        Port northWestPort = new Port("North West", NORTH_WEST_PORT_COORDINATES, 300000, 0);
        Port southWestPort = new Port("South West", SOUTH_WEST_PORT_COORDINATES, 300000, 0);
        Port southEast = new Port("South East", SOUTH_EAST_PORT_COORDINATES, 300000, 0);
        SeaGrid seaGrid = new SeaGrid(new String [30][30]);
        seaGrid.placePortOnGrid(seaGrid.getGrid(),northEast.getPosition());
        seaGrid.placePortOnGrid(seaGrid.getGrid(),northWestPort.getPosition());
        seaGrid.placePortOnGrid(seaGrid.getGrid(),southWestPort.getPosition());
        seaGrid.placePortOnGrid(seaGrid.getGrid(),southEast.getPosition());
        seaGrid.placeShipOnGrid(seaGrid.getGrid(),ship1.getPosition());
        seaGrid.placeShipOnGrid(seaGrid.getGrid(),ship2.getPosition());
        seaGrid.printSeaGrid(seaGrid.getGrid());
        while (true) {
            ship1.navigateToPort(northEast.getPosition(),ship1.getPosition());
            ship2.navigateToPort(southWestPort.getPosition(),ship2.getPosition());
            Thread.sleep(600);
            System.out.println("\n");
            seaGrid.placeShipOnGrid(seaGrid.getGrid(), ship1.getPosition());
            seaGrid.placeShipOnGrid(seaGrid.getGrid(), ship2.getPosition());
            seaGrid.removeShipFromGrid(seaGrid.getGrid(), ship1.getPreviousPosition());
            seaGrid.removeShipFromGrid(seaGrid.getGrid(), ship2.getPreviousPosition());
            seaGrid.printSeaGrid(seaGrid.getGrid());
            seaGrid.clearConsole();
        }



    }

}
