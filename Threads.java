package cargoships;

import java.awt.AWTException;

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
	        PopUp td = new PopUp();
	        try {
	            td.displayTray( String.format("Ship %s has arrived to %s", cargoship.getName(),port.getPortName()));
	        } catch (AWTException e) {
	            e.printStackTrace();
	        }
	        DatabaseHandler db = new DatabaseHandler();
	        db.deleteShipFromLog(cargoship.getId());
	    });

	    
	    t.start();
	    
	    System.out.println(String.format("%s travelling to %s, Position: %s,%s Bearing: %s", cargoship.getName(), port.getPortName(),
	    		Integer.toString(cargoship.getPosition()[0]),Integer.toString(cargoship.getPosition()[1]), cargoship.getsSBearing()));
	    if(!t.isAlive()) {
	    	t.join();
	    }
	}
}
