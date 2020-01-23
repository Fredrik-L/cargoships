package cargoships;

public class Threads {

    public Threads(Cargoships cargoship, Port port) {
        try {
            this.createThreads(cargoship, port);
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }
    private void createThreads(Cargoships cargoship, Port port) throws InterruptedException {
    Thread t = new Thread(() -> {
        do {
            cargoship.navigateToPort(port.getPortPosition(), cargoship.getPosition());
            try {
                Thread.sleep(200);//360000 6 min
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (!cargoship.getInDock());
        
        port.loadCargo(cargoship.getCargoUnits());
        cargoship.unloadCargo();
        System.out.println(String.format("Ship %s arrived at %s,%s", cargoship.getName(), cargoship.getPosition()[0],cargoship.getPosition()[1]));

    });
            t.start();
            System.out.println("Created and Started Job!");
                if(!t.isAlive()) {
        t.join();
    }
            //System.out.println(t.isAlive());


}


    }
