

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StartMenu {
    public static void cls() {
    	//Method that will clear the console.
        try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	static int takeIntInput(String question, int biggestNmbr) {
		Scanner s = new Scanner(System.in);
		int answer;
		System.out.println(question);
		String temp = s.nextLine();
		try {
			answer = Integer.parseInt(temp);
			if(answer > 0 && answer <= biggestNmbr) {
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
	static Cargoships createCargoship(boolean inDock, String name, String bearing, int[] position, int cruiseKnots, int topKnots, int id, int cargoCap, int cargoUnits) {
		Cargoships cargoship = new Cargoships(inDock, name, bearing, position, cruiseKnots, topKnots, id, cargoCap, cargoUnits);
		return cargoship;
		
	}
	public static void main(String[] args) {
		
		System.out.println("Welcome to the ShipSystem");
		Scanner in = new Scanner(System.in);
		DatabaseHandler db = new DatabaseHandler();
		ArrayList<Cargoships> shipList = new ArrayList<Cargoships>();
		boolean run = true;
    	while(run = true) {
		int choice = takeIntInput("Choose your option\n (1)ADD BOAT\n (2)SEARCH SHIP\n (3)SHOW SHIP\n (4)ADD JOB\n (5)EXIT", 5);
    	
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
            	System.out.println("Created: " + cargoshipInfo);
            	String[] tempArray = cargoshipInfo.split(",", -1);
            	int id = Integer.parseInt(tempArray[0]);
            	
            	shipList.add(createCargoship(true, name, bearing, posArray, cruiseKnots, topKnots, id, cargoCapacity, cargoUnits));
            	System.out.println("Press enter to continue...");
                in.nextLine();
            	break;
            case 2:
            	int idShip = takeIntInput("Enter the ID of the ship you wanna search on: ", 1000);
            	String shipInfo = db.searchForShip(idShip);
            	System.out.println(shipInfo + "\nPress enter to continue...");
            	break;
            case 3:
            	System.out.println("| ID |   NAME   | POS   | BEARING   | CRUISEKNOTS   | TOPKNOTS   | CARGOCAP   | UNITS");
            	List<String> allShips = db.readAllDatabase();
            	for(String ship: allShips) {
            		String[] splittedString = ship.split(",", -1);
            		System.out.println(String.format("| %s  |    %s  | %s | %s | %s | %s | %s | %s |", splittedString[0],splittedString[1],splittedString[2],
            				splittedString[3],splittedString[4],splittedString[5],splittedString[6],splittedString[7],splittedString[7]));
            	}
            	System.out.println("Press enter to continue...");
            	in.hasNextLine();
            	cls();
            	break;
            case 4:
            	boolean acceptedInpt = false;
	            while(acceptedInpt == false) {
	            	System.out.println("From what port would you like to travel from: ");
	            	String destinationPort = in.nextLine();
	            	if(destinationPort.equalsIgnoreCase("x")) {
	            		acceptedInpt = true;
	            	}
	            	int shipsInPort = db.getShipsInPorts(destinationPort);
	            	if(shipsInPort >= 1) {
	            		System.out.println(String.format("There is %s ships in this port!", shipsInPort));
	            		acceptedInpt = true;
	            	}
	            	else if(shipsInPort == 0) {
	            		System.out.println("Sorry this port does not have any ships in it right now.");
	            	}
	            	else {
	            		System.out.println("Sorry we dont have that port.");
	            	}
            	}
            	
            	System.out.println("Where would you like to travel to: ");
            	String TravelTo = in.nextLine();
            	//Decisions.OptionFour();
                break;
            case 5:
            	System.out.println("Goodbye!");
            	in.close();
            	run = false;
            	System.exit(0);
                break;                
      }
	}
  }   
}
