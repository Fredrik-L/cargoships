package cargoships;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartMenu {
	static List<Port> portList;
	static List<Cargoships> cargoshipsList;


    static List<Port> createPortFromDb(){
    	DatabaseHandler db = new DatabaseHandler();
    	List<Port> allPorts = new ArrayList<Port>();
    	
    	List<String> allRows = db.readFromDb("ports");
    	
    	for(String port: allRows) {
    		String[] splittedString = port.split(",");
    		int id = Integer.parseInt(splittedString[0]);
    		String name = splittedString[1];
    		
    		int cargo = Integer.parseInt(splittedString[2]);
    		int ships = Integer.parseInt(splittedString[3]);
    		int x = Integer.parseInt(splittedString[4]);
    		int y = Integer.parseInt(splittedString[5]);
    		int[] pos = new int[2];
    		pos[0] = x;
    		pos[1] = y;
    		
    		Port portObj = new Port(pos, cargo, ships, name, id);
    		allPorts.add(portObj);
    	}
    	return allPorts;
    }
    static List<Cargoships> createShipsFromDb() {
    	DatabaseHandler db = new DatabaseHandler();
    	List<Cargoships> allCargoshipsObj = new ArrayList<Cargoships>();
    	List<String> allShips = db.readFromDb("cargoships");
    	for(String ship: allShips) {
    		String[] splittedString = ship.split(",");
    		int id = Integer.parseInt(splittedString[0]);
    		String name = splittedString[1];
    		
    		int x = Integer.parseInt(splittedString[2]);
    		int y = Integer.parseInt(splittedString[3]);
    		int[] pos = new int[2];
    		pos[0] = x;
    		pos[1] = y;
    		String bearing = splittedString[4];
    		int cruiseKnots = Integer.parseInt(splittedString[5]);
    		int topKnots = Integer.parseInt(splittedString[6]);
    		int cargoCapacity = Integer.parseInt(splittedString[7]);
    		int cargoUnits = Integer.parseInt(splittedString[8]);
    		Cargoships cargoShipObj = createCargoship(true,name,bearing, pos, cruiseKnots, topKnots, id, cargoCapacity, cargoUnits);
    		allCargoshipsObj.add(cargoShipObj);
    	}
    	return allCargoshipsObj;
    }
	static int takeIntInput(String question, int biggestNmbr) {
		Scanner s = new Scanner(System.in);
		int answer;
		System.out.println(question);
		String temp = s.nextLine();
		try {
			answer = Integer.parseInt(temp);
			if(answer >= 0 && answer <= biggestNmbr) {
				return answer;
			}
			else {
				System.out.println("Please enter a numbet between 1-" + biggestNmbr);
				answer = takeIntInput(question, biggestNmbr);
			}
		}
		catch(NumberFormatException e) {
			System.out.println("Please enter a number!");
			answer = takeIntInput(question, biggestNmbr);
		}
		return answer;
	}
	static Object[] convertPos(String pos) {
		Object[] returnArray = new Object[2];
		boolean accepted;
		int[] posArray = new int[2];
		String[] tempArray = new String[2];
		try {
			tempArray = pos.split(",",2);
			int xPos = Integer.parseInt(tempArray[0]);
			int yPos = Integer.parseInt(tempArray[1]);
			posArray[0] = xPos;
			posArray[1] = yPos;
			accepted = true;
			
		}catch(Exception e) {
			System.out.println(e);
			accepted = false;
		}
		returnArray[0] = posArray;
		returnArray[1] = accepted;
		return returnArray;
	}
	static int getPortObjListIndex(String portName) {
		int i = 0;
		int portIndex = 0;
		for(Port p: portList) {
			if(p.getPortName().equalsIgnoreCase(portName)) {
				portIndex = i;
				break;
			}
			i++;
		}
		return portIndex;
	}
	static int getShipObjListIndex(int chosenId) {
    	int cargoshipIndex = 0;
        int i = 0;
    	for(Cargoships s: cargoshipsList) {
    		if(s.getId() == chosenId) {
    			cargoshipIndex = i;
    			break;
    		}
    		i ++;
    	}
    	return cargoshipIndex;
	}
	static Cargoships createCargoship(boolean inDock, String name, String bearing, int[] position, int cruiseKnots, int topKnots, int id, int cargoCap, int cargoUnits) {
		Cargoships cargoship = new Cargoships(inDock, name, bearing, position, cruiseKnots, topKnots, id, cargoCap, cargoUnits);
		return cargoship;
	}
	static void printShips(String row) {
		String[] splittedString = row.split(",", -1);
		System.out.println(String.format("| %-4s|%-12s| %s,%-6s| %-8s| %-12s| %-9s| %-9s| %-6s|", splittedString[0],splittedString[1],splittedString[2],
				splittedString[3],splittedString[4],splittedString[5],splittedString[6],splittedString[7],splittedString[8]));
	}
	static void startJobsInProgress() {
		DatabaseHandler db = new DatabaseHandler();
		List<String> allJobs = db.readFromDb("jobsinprocess");
		
		for(String job: allJobs) {
			String[] splittedString = job.split(",");
			int shipId = Integer.parseInt(splittedString[0]);
			String startPort = splittedString[1];
			String destinationPort = splittedString[2];
			int cargoObjIndex = getShipObjListIndex(shipId);
			int portObjIndex = getPortObjListIndex(destinationPort);
			Threads t = new Threads(cargoshipsList.get(cargoObjIndex),portList.get(portObjIndex));
			
		}		
	}
	public static void main(String[] args) {
		
		System.out.println("Welcome to the ShipSystem");
		
		Scanner in = new Scanner(System.in);
		DatabaseHandler db = new DatabaseHandler();
		portList = createPortFromDb();
		cargoshipsList = createShipsFromDb();
		startJobsInProgress();
		
		boolean run = true;
    	while(run = true) {
		int choice = takeIntInput("Choose your option\n[1] ADD SHIP\n[2] SEARCH SHIP\n[3] SHOW ALL SHIPS\n[4] ADD JOB\n[5] SHOW ON GOING JOBS\n[6] EXIT", 6);
        switch (choice){
            case 1:  
            	System.out.print("Add name: ");
            	String name = in.nextLine();
            	String postion = "";
            	int[] posArray = new int[2];
            	while(true) {
		        	System.out.println("Add postion: ");
		        	postion = in.nextLine();
		        	Object[] tempArray = convertPos(postion);
		        	boolean accepted = (boolean) tempArray[1];
		        	if(accepted == true) {
		        		posArray = (int[]) tempArray[0];
		    			if((posArray[0] >= 0 && posArray[0] <= 100) && (posArray[1] >= 0 && posArray[1] <= 100)) {
		    				break;
		    			}
		    			else {
		    				System.out.println("It is a grid with the size of 100x100!");
		    			}
		        	}
		        	else {
		        		System.out.println("Please enter number like this '50,40'.");
		        	}
            	}
            	System.out.println("Add bearing: ");
            	String bearing = in.nextLine();
            	
            	int cruiseKnots = takeIntInput("Add Cruise Knot: ", 50);
          		
            	int topKnots = takeIntInput("Add Top Knots: ", 50);
            	
            	int cargoCapacity = takeIntInput("Add cargocapacity: ", 1000);
            	
            	int cargoUnits = takeIntInput("Add Cargo units: ", cargoCapacity);
            	
            	db.writeToDatabase(name, postion, bearing, cruiseKnots, topKnots, cargoCapacity, cargoUnits);
            	String cargoshipInfo = db.selectLastShip();
            	System.out.println("| ID  | NAME       | POS      | BEARING | CRUISEKNOTS | TOPKNOTS | CARGOCAP | UNITS |\n"
            			+ "--------------------------------------------------------------------------------------");
            	
            	printShips(cargoshipInfo);
            	System.out.println("--------------------------------------------------------------------------------------");
            	
            	String[] tempArray = cargoshipInfo.split(",", -1);
            	int id = Integer.parseInt(tempArray[0]);
            	
            	cargoshipsList.add(createCargoship(true, name, bearing, posArray, cruiseKnots, topKnots, id, cargoCapacity, cargoUnits));
            	System.out.println("Press enter to continue...");
                in.nextLine();
            	break;
            case 2:
            	int idShip = takeIntInput("Enter the ID of the ship you wanna search on: ", 1000);
            	String shipInfo = db.searchForShip(idShip);
            	System.out.println("| ID  | NAME       | POS      | BEARING | CRUISEKNOTS | TOPKNOTS | CARGOCAP | UNITS |\n"
            			+ "--------------------------------------------------------------------------------------");
            	printShips(shipInfo);
            	System.out.println("--------------------------------------------------------------------------------------");
            	System.out.println("Press enter to continue...");
            	in.nextLine();
            	break;
            case 3:
            	System.out.println("| ID  | NAME       | POS      | BEARING | CRUISEKNOTS | TOPKNOTS | CARGOCAP | UNITS |\n"
            			+ "--------------------------------------------------------------------------------------");
            	List<String> allShips = db.readFromDb("cargoships");
            	for(String ship: allShips) {
            		printShips(ship);
            	}
            	System.out.println("--------------------------------------------------------------------------------------");
            	System.out.println("Press enter to continue...");
            	in.nextLine();
            	break;
            case 4:
            	boolean acceptedInpt = false;
            	int chosenId = 0;
            	String startPort = "";
	            while(acceptedInpt == false) {
	            	
	            	System.out.println("Ports: Stockholm, Göteborg, Gdansk, Åbo, Öland.");
	            	
	            	System.out.println("From what port would you like to travel from: ");
	            	
	            	startPort = in.nextLine();
	            	if(startPort.equalsIgnoreCase("x")) {
	            		acceptedInpt = true;
	            	}
	            	List<Integer> allShipsId = db.selectAvailableShips(startPort);
	            	int shipsInPort = allShipsId.size();
	            	if(shipsInPort >= 1) {
	            		System.out.println(String.format("There is %s ships in this port!", shipsInPort));
	            		int i = 1;
	            		
	            		for(int shipId: allShipsId) {
	            			for(Cargoships ship: cargoshipsList) {
		            			if(ship.getId() == shipId)
		            				System.out.println(String.format("[%s]. %s ", i, ship.getName()));
	            			}
		            		i ++;
	            		}
	            		int answerId = takeIntInput("What ship do you wanna use?", i - 1);
	            		chosenId = allShipsId.get(answerId - 1);
	            		String chosenShip = db.searchForShip(chosenId);

	            		System.out.println("| ID  | NAME       | POS      | BEARING | CRUISEKNOTS | TOPKNOTS | CARGOCAP | UNITS |\n"
	                			+ "--------------------------------------------------------------------------------------");
	            		printShips(chosenShip);
	            		System.out.println("--------------------------------------------------------------------------------------");
	            		acceptedInpt = true;
	            	}
	            	else if(shipsInPort == 0) {
	            		System.out.println("Sorry this port does not have any ships in it right now.");
	            	}
	            	else {
	            		System.out.println("Sorry we dont have that port.");
	            	}
            	}

            	int portStartIndex = getPortObjListIndex(startPort);
            	int cargoShipIndex = getShipObjListIndex(chosenId);
            	
            	int cargoInPort = portList.get(portStartIndex).getCargoInPort();
            	int shipCargoCapacity = cargoshipsList.get(cargoShipIndex).getCargoCapacity();
            	
            	System.out.println(String.format("%s has %s units!", portList.get(portStartIndex).getPortName(),
            			cargoInPort));
            	int cargoLimit = shipCargoCapacity;
            	if(cargoInPort < shipCargoCapacity) {
            		cargoLimit = cargoInPort;
            	}
            	int currentCargoUnits = takeIntInput("How many cargo units do you want with you?", cargoLimit);
            	
            	cargoshipsList.get(cargoShipIndex).loadCargo(currentCargoUnits);
            	
            	
            	portList.get(portStartIndex).unLoadCargo(currentCargoUnits);
            	
            	System.out.println("Where would you like to travel to: ");
            	String destinationPort = in.nextLine();

            	db.writeToLog(chosenId, startPort, destinationPort,"jobsinprocess");
            	db.writeToLog(chosenId, startPort, destinationPort, "travellog");
            	int portDestinationIndex = getPortObjListIndex(destinationPort);
            	Threads t = new Threads(cargoshipsList.get(cargoShipIndex),portList.get(portDestinationIndex));
            	System.out.println(String.format("Sent away boat %s from %s to %s.",cargoshipsList.get(cargoShipIndex).getName(),
            			startPort, destinationPort));
            	break;
            case 5:
            	List<String> currentJobList = db.readFromDb("jobsinprocess");
            	for(String job: currentJobList) {
            		String[] splittedString = job.split(",");
            		int shipId = Integer.parseInt(splittedString[0]);
            		String startPortJob = splittedString[1];
            		String destiPort = splittedString[2];
            		int i = getShipObjListIndex(shipId);
            		
            	    System.out.println(String.format("%s travelling to %s from %s, Position: %s,%s Bearing: %s", cargoshipsList.get(i).getName(), destiPort, startPortJob,
            	    		Integer.toString(cargoshipsList.get(i).getPosition()[0]),Integer.toString(cargoshipsList.get(i).getPosition()[1]), cargoshipsList.get(i).getsSBearing()));
            	}
            	if(currentJobList.size() == 0) {
            		System.out.println("There is no jobs in progress!");
            	}
            	System.out.println("Press enter to continue...");
            	in.nextLine();
            	break;
            case 6:
            	System.out.println("Goodbye!");
            	in.close();
            	run = false;
            	System.exit(0);
                break;                
      }
	}
  }   
}
