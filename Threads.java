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
        System.out.println(cargoship.getPosition()[0] + ", " + cargoship.getPosition()[1] + " " + cargoship);


    });
            t.start();
            System.out.println("name of thread " + t.getName());
                if(!t.isAlive()) {
        t.join();
    }
            System.out.println(t.isAlive());


}


    }



