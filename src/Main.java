import java.awt.event.KeyEvent;

public class Main {

    public static void main(String[] args) {

         int [] HOME_PORT_COORDINATES = {0, 29};

        CargoShip ship1 = new CargoShip(1,32, 300, "black",0,20, 45, 100,"north", new int[]{0, 23}, "ms_Sofia");
        Port homePort = new Port("My Port", HOME_PORT_COORDINATES, 300000, 0);
        System.out.println(ship1.getPosition()[0] + "," + ship1.getPosition()[1]   );
        SeaGrid seaGrid = new SeaGrid(new String [30][30]);
        seaGrid.placePortOnGrid(seaGrid.getGrid(),homePort.getPosition());
        seaGrid.placeShipOnGrid(seaGrid.getGrid(),ship1.getPosition());
        seaGrid.printSeaGrid(seaGrid.getGrid());




    }

}
