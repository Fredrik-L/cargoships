import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        int[] STARTPOSITION = {10, 14};
        int[] DESTINATION = {0, 0};

        Cargoships cargoship1 = new Cargoships(true, "ms sofia", " ", STARTPOSITION, 32, 50, 1, 3000, 0);
        Cargoships cargoship2 = new Cargoships(true, "ms sofia", " ", STARTPOSITION, 32, 50, 1, 3000, 0);

        List<Cargoships> shipGarage = new ArrayList<Cargoships>();
        shipGarage.add(cargoship1);
        shipGarage.add(cargoship2);

        do {
            System.out.println(cargoship1.getPosition()[0] + " " + cargoship1.getPosition()[1] + " " + cargoship1.getBearing());
            cargoship1.navigateToPort(DESTINATION, cargoship1.getPosition());
            Thread.sleep(800);
            System.out.println(cargoship1.getInDock());
        }while (!cargoship1.getInDock());

    }

}
