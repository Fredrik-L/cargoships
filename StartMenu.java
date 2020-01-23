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
		System.out.println(allPorts.size());
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
	static void printShips(String row) {
		String[] splittedString = row.split(",", -1);
		System.out.println(String.format("| %-4s| %-12s| %s,%-6s| %-8s| %-12s| %-9s| %-9s| %-6s|", splittedString[0],splittedString[1],splittedString[2],
				splittedString[3],splittedString[4],splittedString[5],splittedString[6],splittedString[7],splittedString[8]));
	}
	public static void main(String[] args) {


		System.out.println("Welcome to the ShipSystem");


		Scanner in = new Scanner(System.in);
		DatabaseHandler db = new DatabaseHandler();
		List<Port> portList = createPortFromDb();
		List<Cargoships> cargoshipsList = new ArrayList<Cargoships>();
		cargoshipsList = createShipsFromDb();
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

					cargoshipsList.add(createCargoship(true, name, bearing, posArray, cruiseKnots, topKnots, id, cargoCapacity, cargoUnits));
					System.out.println("Press enter to continue...");
					in.nextLine();
					break;
				case 2:
					int idShip = takeIntInput("Enter the ID of the ship you wanna search on: ", 1000);
					String shipInfo = db.searchForShip(idShip);
					System.out.println(shipInfo + "\nPress enter to continue...");
					break;
				case 3:
					System.out.println("| ID  | NAME        | POS      | BEARING | CRUISEKNOTS | TOPKNOTS | CARGOCAP | UNITS |\n"
							+ "--------------------------------------------------------------------------------------");
					List<String> allShips = db.readAllDatabase();
					for(String ship: allShips) {
						printShips(ship);
					}
					System.out.println("--------------------------------------------------------------------------------------");
					System.out.println("Press enter to continue...");
					in.hasNextLine();
					cls();
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
						System.out.println(shipsInPort);
						if(shipsInPort >= 1) {
							System.out.println(String.format("There is %s ships in this port!", shipsInPort));
							int i = 1;

							for(int shipId: allShipsId) {
								System.out.println(String.format("[%s]. %s ", i, shipId));
								i ++;
							}
							int answerId = takeIntInput("What ship do you wanna use?", i - 1);
							chosenId = allShipsId.get(answerId - 1);
							System.out.println("detta id valde jag: " + chosenId);
							String chosenShip = db.searchForShip(chosenId);

							System.out.println("| ID  | NAME        | POS      | BEARING | CRUISEKNOTS | TOPKNOTS | CARGOCAP | UNITS |\n"
									+ "--------------------------------------------------------------------------------------");
							printShips(chosenShip);
							System.out.println("-----------------------------------------------------------------------------------");
							acceptedInpt = true;
						}
						else if(shipsInPort == 0) {
							System.out.println("Sorry this port does not have any ships in it right now.");
						}
						else {
							System.out.println("Sorry we dont have that port.");
						}
					}
					int maxCargo = 0;
					for(Cargoships ship: cargoshipsList) {
						if(ship.getId() == chosenId) {
							maxCargo = ship.getCargoCapacity();
							System.out.println(maxCargo);
							break;
						}
					}

					int currentCargoUnits = takeIntInput("How many cargo units do you want with you?", maxCargo);


					System.out.println("Where would you like to travel to: ");
					String destinationPort = in.nextLine();
					db.writeToTravelLog(chosenId, startPort, destinationPort);
					int portIndex = 0;
					int cargoshipIndex = 0;
					int i = 0;
					for(Port p: portList) {
						if(p.getPortName().equalsIgnoreCase(destinationPort)) {
							portIndex = i;
							break;
						}
						i++;
					}

					int j = 0;
					for(Cargoships s: cargoshipsList) {
						if(s.getId() == chosenId) {
							cargoshipIndex = j;
							break;
						}
						j++;
					}
					System.out.println(portList.get(portIndex).getPortName());
					System.out.println(cargoshipsList.get(cargoshipIndex).getId());
					Threads t = new Threads(cargoshipsList.get(cargoshipIndex),portList.get(portIndex));

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