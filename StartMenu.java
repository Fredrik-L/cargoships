package Menu;

import java.util.Scanner;


public class StartMenu {

	public static void main(String[] args) {

		
		
		
		//MenuOptions Decisions = new MenuOptions();
		
		
		System.out.println("Welcome to the ShipSystem");
		System.out.println("Choose your option\n (1)ADD BOAT\n (2)SEARCH SHIP\n (3)SHOW SHIP\n (4)ADD JOB\n (5)EXIT\n ");
        Scanner in = new Scanner(System.in);
        
       
        
    	int choice = in.nextInt();

        switch (choice){
            case 1:  
            	
            	System.out.print("Add name:");
            	String name = in.nextLine();
            	
            	System.out.println("Add postion:");
            	String postion = in.nextLine();
            	
            	System.out.println("Add bearing:");
            	String bearing = in.nextLine();
            	
          	  	System.out.println("Add cruisenot:");
            	int cruisenot = in.nextInt();
            	
            	System.out.println("Add topknots:");
            	int topknots = in.nextInt();
            	
            	System.out.println("Add cargocapacity:");
            	int cargocapacity = in.nextInt();
            	
            	System.out.println("Add cargoweight:");  
            	int cargoweight = in.nextInt();	
            	
                break;
            case 2: 
            	System.out.println("Type the id for the ship you would like to search on: ");
            	int IdShip = in.nextInt();
            	//Decisions.OptionTwo();
                break;
            case 3:
            	System.out.println("To display all ships press (enter): ");
            	//Decisions.OptionThree();
            	break;
            case 4:   
            	System.out.println("The Boats that are available are:");
            	
            	System.out.println("Available Boats are:");
            	System.out.println("From what port would you like to travel from: ");
            	String TravelFrom = in.nextLine();
            	
            	System.out.println("Where would you like to travel to: ");
            	String TravelTo = in.nextLine();
            	//Decisions.OptionFour();
                break;
            case 5:
            	System.out.println("The program will exit now");
            	in.close();
            	//Decisions.OptionFive();
                break;
            default :
                System.out.println("Invalid input");    
                	
	}
              
        
        
  }      

}
