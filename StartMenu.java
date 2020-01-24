package cargoships;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartMenu {
	//Lists that will store Port objects and Cargoships Objects.
	static List<Port> portList;
	static List<Cargoships> cargoshipsList;


    static List<Port> createPortFromDb(){
    	//Creates Port objects from the Database, will return a list of Port objects.
    	
    	DatabaseHandler db = new DatabaseHandler();
    	//This will be the return list.
    	List<Port> allPorts = new ArrayList<Port>();
    	//This is all the ports in strings.
    	List<String> allRows = db.readFromDb("ports");
    	
    	for(String port: allRows) {
    		//For loop that will split the row into each variable and then create a ship from it.
    		String[] splittedString = port.split(",");
    		int id = Integer.parseInt(splittedString[0]);
    		String name = splittedString[1];
    		int cargo = Integer.parseInt(splittedString[2]);
    		int x = Integer.parseInt(splittedString[3]);
    		int y = Integer.parseInt(splittedString[4]);
    		int[] pos = new int[2];
    		pos[0] = x;
    		pos[1] = y;
    		
    		Port portObj = new Port(pos, cargo, name, id);
    		allPorts.add(portObj);
    	}
    	return allPorts;
    }
    static List<Cargoships> createShipsFromDb() {
    	//Creates Cargoships from the database, will return a list of Cargoships objects.
    	
    	//Same design as createPortFromDb but with abit diffrent variables.
    	
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
		//Handles inputs if the answer should be an int, question is the question for the user and biggestNmbr is what the highest answer can be, for example 0-6 in our meny.
		//Will call upon itself until it gets the right answer.
		Scanner s = new Scanner(System.in);
		int answer;
		
		System.out.println(question);
		String temp = s.nextLine();
		try {
			//Try to convert to int and then check if the number is correct, if it is then return the answer.
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
			//If the user do not input a number.
			System.out.println("Please enter a number!");
			answer = takeIntInput(question, biggestNmbr);
		}
		return answer;
	}
	static Object[] convertPos(String pos) {
		//Will try to convert a string to a int array with 2 elements.
		//For ex "05,69" then the first two number will be in the first element in the array, and the last two number will be in the second element of the array.
		//The method retunrs a Object array, with the first element in it will be the int array and the second object will be a boolean.
		//If the conversion failed the boolean will be false, else true.
		
		Object[] returnArray = new Object[2];
		boolean accepted;
		int[] posArray = new int[2];
		String[] tempArray = new String[2];
		try {
			//Try to split and then convert the values to int.
			tempArray = pos.split(",",2);
			int xPos = Integer.parseInt(tempArray[0]);
			int yPos = Integer.parseInt(tempArray[1]);
			posArray[0] = xPos;
			posArray[1] = yPos;
			accepted = true;
			
		}catch(Exception e) {
			//If the split or convert went wrong, return false.
			accepted = false;
		}
		returnArray[0] = posArray;
		returnArray[1] = accepted;
		return returnArray;
	}
	static int getPortObjListIndex(String portName) {
		//Gets the index of the object in portList.
		//Argument is the portname of the object.
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
		//Gets the index of the object in cargoshipsList
		//Argument is the id of the ship.
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
		//Creates a Cargoships object and returns it.
		Cargoships cargoship = new Cargoships(inDock, name, bearing, position, cruiseKnots, topKnots, id, cargoCap, cargoUnits);
		return cargoship;
	}
	static void printShips(String row) {
		//Will print out "pretty" string of a cargoship.
		String[] splittedString = row.split(",", -1);
		System.out.println(String.format("| %-4s|%-12s| %s,%-6s| %-8s| %-12s| %-9s| %-9s| %-6s|", splittedString[0],splittedString[1],splittedString[2],
				splittedString[3],splittedString[4],splittedString[5],splittedString[6],splittedString[7],splittedString[8]));
	}
	static void startJobsInProgress() {
		//Will start the jobs if there is any that was not finsihed the last time.
		DatabaseHandler db = new DatabaseHandler();
		//List of all jobs, will be: ShipId, StartPort, DestinationPort
		//Reads from the table jobsinprocess.
		List<String> allJobs = db.readFromDb("jobsinprocess");
		if(allJobs.size() > 0) {
			for(String job: allJobs) {
				//For each loop that will split each row and create a Thread for each job.
				
				String[] splittedString = job.split(",");
				int shipId = Integer.parseInt(splittedString[0]);
				String startPort = splittedString[1];
				String destinationPort = splittedString[2];
				//This will be the index for the objects in each list. 
				int cargoObjIndex = getShipObjListIndex(shipId);
				int portObjIndex = getPortObjListIndex(destinationPort);
				//Sends in the objects to the thread.
				Threads t = new Threads(cargoshipsList.get(cargoObjIndex), portList.get(portObjIndex));
			}
		}		
	}
	public static void main(String[] args) {
		//Main program
		System.out.println("Welcome to the ShipSystem\n");
		
		Scanner in = new Scanner(System.in);
		DatabaseHandler db = new DatabaseHandler();
		//Gets information from the database about Ships and ports.
		portList = createPortFromDb();
		cargoshipsList = createShipsFromDb();
		//Checks if any jobs need to be started.
		startJobsInProgress();
		
		//Will run until the user press 6 "exit"
		boolean run = true;
    	while(run = true) {
    	
		int choice = takeIntInput("Choose your option\n[1] ADD SHIP\n[2] SEARCH SHIP\n[3] SHOW ALL SHIPS\n[4] ADD JOB\n[5] SHOW ON GOING JOBS\n[6] EXIT", 6);
        switch (choice){
        case 1:  
        	//Add ship
            	System.out.print("Add name: ");
            	String name = in.nextLine();
            	String postion = "";
            	int[] posArray = new int[2];
            	//Loop that will run until the user inputs right value.
            	boolean rightAnswer = false;
            	while(rightAnswer = false) {
		        	System.out.println("Add postion: ");
		        	postion = in.nextLine();
		        	//tempArray will be two elements, first one will be a int array and the second element will be a boolean.
		        	//If the boolean is true then the loop will break.
		        	
		        	Object[] tempArray = convertPos(postion);
		        	boolean accepted = (boolean) tempArray[1];
		        	if(accepted == true) {
		        		posArray = (int[]) tempArray[0];
		    			if((posArray[0] >= 0 && posArray[0] <= 100) && (posArray[1] >= 0 && posArray[1] <= 100)) {
		    				rightAnswer = true;
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
            	
            	//Writes cargoship info to the database.
            	db.writeToDatabase(name, postion, bearing, cruiseKnots, topKnots, cargoCapacity, cargoUnits);
            	//Will select the last ship from the database, in other words the one we just added.
            	String cargoshipInfo = db.selectLastShip();
            	
            	//Some pretty printing.
            	System.out.println("| ID  | NAME       | POS      | BEARING | CRUISEKNOTS | TOPKNOTS | CARGOCAP | UNITS |\n"
            			+ "--------------------------------------------------------------------------------------");
            	printShips(cargoshipInfo);
            	System.out.println("--------------------------------------------------------------------------------------");
            	
            	//Gets the id from the database.
            	//The program or user will not choose the id for the ship.
            	//Its an Auto incremnted column in our database that will create it. 
            	//So it will never be the same.
            	String[] tempArray = cargoshipInfo.split(",", -1);
            	int id = Integer.parseInt(tempArray[0]);
            	
            	//Creates a cargoship object and adds it to the list.
            	cargoshipsList.add(createCargoship(true, name, bearing, posArray, cruiseKnots, topKnots, id, cargoCapacity, cargoUnits));
            	System.out.println("Press enter to continue...");
                in.nextLine();
            	break;
            case 2:
            	//Search for a ship with its ID.
            	int idShip = takeIntInput("Enter the ID of the ship you wanna search on: ", 1000);
            	//Asks the database to get the info.
            	String shipInfo = db.searchForShip(idShip);
            	System.out.println("| ID  | NAME       | POS      | BEARING | CRUISEKNOTS | TOPKNOTS | CARGOCAP | UNITS |\n"
            			+ "--------------------------------------------------------------------------------------");
            	printShips(shipInfo);
            	System.out.println("--------------------------------------------------------------------------------------");
            	System.out.println("Press enter to continue...");
            	in.nextLine();
            	break;
            case 3:
            	//Prints all the ships in our database.
            	System.out.println("| ID  | NAME       | POS      | BEARING | CRUISEKNOTS | TOPKNOTS | CARGOCAP | UNITS |\n"
            			+ "--------------------------------------------------------------------------------------");
            	//All ships in string per row.
            	List<String> allShips = db.readFromDb("cargoships");
            	for(String ship: allShips) {
            		printShips(ship);
            	}
            	System.out.println("--------------------------------------------------------------------------------------");
            	System.out.println("Press enter to continue...");
            	in.nextLine();
            	break;
            case 4:
            	//Add job
            	
            	boolean acceptedInpt = false;
            	int chosenId = 0;
            	String startPort = "";
            	//Loop that keeps asking the the start port.
	            while(acceptedInpt == false) {
	            	System.out.println("Ports: Stockholm, Göteborg, Gdansk, Åbo, Öland.");
	            	System.out.println("From what port would you like to travel from: ");
	            	startPort = in.nextLine();
	            	
	            	//List will all the ships id that is in the startPort.
	            	List<Integer> allShipsId = db.selectAvailableShips(startPort);
	            	int shipsInPort = allShipsId.size();
	            	if(shipsInPort >= 1) {
	            		//If there is any ship in the port.
	            		
	            		System.out.println(String.format("There is %s ships in this port!", shipsInPort));
	            		int i = 1;
	            		//Nested loop that will print out each name of the avaliabe ship.
	            		
	            		for(int shipId: allShipsId) {
	            			for(Cargoships ship: cargoshipsList) {
		            			if(ship.getId() == shipId)
		            				System.out.println(String.format("[%s]. %s ", i, ship.getName()));
	            			}
		            		i++;
	            		}
	            		int answerId = takeIntInput("What ship do you wanna use?", i - 1);
	            		chosenId = allShipsId.get(answerId - 1);
	            		//Prints out the chosen ship.
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
	            
	            //Gets the index of the port and cargoship objects in each list.
            	int portStartIndex = getPortObjListIndex(startPort);
            	int cargoShipIndex = getShipObjListIndex(chosenId);
            	
            	//How much cargo that is currently in the port.
            	int cargoInPort = portList.get(portStartIndex).getCargoInPort();
            	//How much units the ship can carry
            	int shipCargoCapacity = cargoshipsList.get(cargoShipIndex).getCargoCapacity();
            	
            	System.out.println(String.format("%s has %s units!", portList.get(portStartIndex).getPortName(),
            			cargoInPort));
            	int cargoLimit = shipCargoCapacity;
            	//Sets the limit to how much the user can take.
            	//If cargoInPort is lower then the ships capacity,
            	//then cargoInPort is the limit. So the ships cant take more then there is.
            	if(cargoInPort < shipCargoCapacity) {
            		cargoLimit = cargoInPort;
            	}
            	int currentCargoUnits = takeIntInput("How many cargo units do you want with you?", cargoLimit);
            	
            	//Loads to the cargoship.
            	cargoshipsList.get(cargoShipIndex).loadCargo(currentCargoUnits);
            	//Unloads from the port.
            	portList.get(portStartIndex).unLoadCargo(currentCargoUnits);
            	
            	String[] ports = new String[5];
            	ports[0] = "Stockholm";
            	ports[1] = "Göteborg";
            	ports[2] = "Gdansk";
            	ports[3] = "Åbo";
            	ports[4] = "Öland";
            	String destinationPort = "";
            	boolean rightInpt = false;
            	while(rightInpt == false) {
            		System.out.println("Where would you like to travel to: ");
                	destinationPort = in.nextLine();
                	for(int i = 0; i < ports.length; i++) {
                		if(ports[i].equalsIgnoreCase(destinationPort))
                			rightInpt = true;
                	}

            	}
            	//Writes to both logs, the s4hipid, startport and destinationPort.
            	db.writeToLog(chosenId, startPort, destinationPort,"jobsinprocess");
            	db.writeToLog(chosenId, startPort, destinationPort, "travellog");
            	
            	//Gets the index for the destinationPort index.
            	int portDestinationIndex = getPortObjListIndex(destinationPort);
            	//Create a new thread with cargoship and destinationport object.
            	Threads t = new Threads(cargoshipsList.get(cargoShipIndex),portList.get(portDestinationIndex));
            	
            	break;
            case 5:
            	//Prints out all current jobs.
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
            	//Exit the program
            	System.out.println("Goodbye!");
            	in.close();
            	run = false;
            	System.exit(0);
                break;                
      }
	}
  }   
}
