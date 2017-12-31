package v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * An Airport object consists of a list of Runways, a list of Planes and a list of Plane names.
 * The Airport object controls any number of Runways and Planes and is used to model a
 * simplified airport environment. An Airport object allows Planes to enter the system, allows Planes
 * to takeoff or wait for clearance, closes a Runway, opens a Runways, displays information about planes
 * waiting to takeoff, displays information about planes waiting to be allowed to re-enter a runway and
 * displays the number of planes that have taken off.
 * 
 * Purpose: Data Structure and Algorithms Project
 * Status: Complete and thoroughly tested
 * Last update: 11/7/17
 * Submitted:  12/5/17
 * Comment: test suite and sample run attached
 * @authors: Justin Davis and Mantas Pileckis
 * @version: 2017.11.7
 */
public class Airport {

	static BufferedReader brin = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String args[]) throws IOException {
		//collection of runways
		ListArrayBasedPlus<TakeoffRunway> takeOff = new ListArrayBasedPlus<TakeoffRunway>();
		//collection of plane names - used to check for name uniqueness
		AscendinglyOrderedStringList planeNames = new AscendinglyOrderedStringList();
		//collection of planes waiting for clearance
		ListArrayBasedPlus<Plane> clearanceList = new ListArrayBasedPlus<Plane>();
		//instance  used to keep track of counting variables
		AirportData data = new AirportData();
		startAirport(takeOff);
		while(true) {
			System.out.print(displayMenu());
			String choice = brin.readLine();
			System.out.println(choice);
			switch (choice) {
			case "1": //plane enters the system
				planeEnters(takeOff, planeNames);
				break;
			case "2": //plane takes off
				takeOff(takeOff, data, clearanceList, planeNames);
				break;
			case "3": //plane re-enters the runway's queue
				planeReenters(takeOff, clearanceList);
				break;
			case "4": //adds a new runway to the collection
				addNewRunway(takeOff);
				break;
			case "5": //closes a runway
				closeRunway(takeOff, clearanceList);
				break;
			case "6": //display planes waiting to takeoff
				displayTakeOff(takeOff);
				break;
			case "7": //display planes waiting in clearance
				displayClearance(clearanceList);
				break;
			case "8": //displays number of planes that have taken off
				System.out.println(data.getTakenOff() + " planes have taken off from the airport.\n");
				break;
			case "9": //closes the program
				System.out.println("Airport is now closing!");
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice. Please select again.\n");
			}
		}
	} //end main

	/**
	 * Generates a number of TakeoffRunways specified by the user.
	 * Enforces name uniqueness for the runways.
	 * 
	 * @param takeoffRunways list of TakeoffRunways
	 * @throws NumberFormatException if input cannot be parsed to an int
	 * @throws IOException if the user input cannot be read as String
	 */
	public static void startAirport(ListArrayBasedPlus<TakeoffRunway> takeoffRunways) throws NumberFormatException, IOException {
		System.out.println("Welcome to the Airport program!");
		System.out.println("Enter number of runways: ");
		//number of runways at start
		int runways = Integer.parseInt(brin.readLine());
		System.out.println(runways);
		for (int i = 1; i <= runways; i++) {
			System.out.println("Enter the name of runway " + i + ": ");
			String runwayName = brin.readLine();
			System.out.println(runwayName);
			//checks to see if the runway name is unique
			while(searchRunway(takeoffRunways, runwayName) != null) {
				System.out.println("Runway " + runwayName + " already exists, please choose another name.");
				runwayName = brin.readLine();
				System.out.println(runwayName);
			}
			//adds the runway to the end of the list of runways
			takeoffRunways.add(i-1, new TakeoffRunway(runwayName));
		}
		System.out.println();
	} //end startAirport

	/**
	 * Search through the collection of Runways to see if a specified Runway exists with a certain name.
	 * 
	 * @param takeoffRunways collection of runways
	 * @param runway name of runway that is trying to be located
	 * @return null if the name does not exist or a Runway object if the name matches
	 */
	public static Runway searchRunway(ListArrayBasedPlus<TakeoffRunway> takeoffRunways, String runway) {
		//keeps track of the current Runway - no 
		Runway currRunway = null;
		//number of runways in the collection
		int size = takeoffRunways.size();
		//flag used to see if we are searching for a Runway
		boolean searching = true;
		for (int i = 0; i < size && searching; i++) {
			currRunway = takeoffRunways.get(i);
			if (currRunway.getName().equals(runway)) {
				//a match is found - we can stop searching the rest of the list
				searching = false;
			} //end if
		}
		if (searching) {
			//if the end of the collection has been reached, but we are still searching
			currRunway = null;
		} //end if
		return currRunway;
	} //end uniqueRunway

	/**
	 * Searches the list of clearance planes for a plane that matches an input name.
	 *  
	 * @param clearanceList collection of planes waiting for clearance
	 * @param planeName name of plane that is being searched for
	 * @return -1 if the name specified does not match any planes in the collection or a number 
	 * 			  greater than or equal to 0 if it does exist
	 */
	public static int searchClearanceList(ListArrayBasedPlus<Plane> clearanceList, String planeName){
		//flag used to keep track of the search
		boolean searching = true;
		//number of planes in the collection
		int size = clearanceList.size();
		//variable to keep track of index - initially 0
		int index = 0;
		for (int i  = 0; i < size && searching; i++) {
			if (clearanceList.get(i).getFlightNumber().equals(planeName)) {
				//if the name of the plane matches the name we are searching for
				//set index variable to the current position in the list
				index = i;
				searching = false;
			} //end if
		}
		if (searching) {
			//set index to -1 (outside the bounds of the list)
			index = -1;
		} //end if
		return index;
	} //end searchPlane

	/**
	 * A new plane attempts to get added into the airport system.
	 * Will not allow a plane to enter if there are no runways.
	 * Enforces plane name uniqiueness.
	 * 
	 * @param takeoffRunways collection of planes waiting to takeoff
	 * @param planeNames collection of plane names
	 * @throws IOException if the user input cannot be read as String
	 */
	public static void planeEnters(ListArrayBasedPlus<TakeoffRunway> takeoffRunways, AscendinglyOrderedStringList planeNames) throws IOException {
		//check for uniqueness
		if (takeoffRunways.isEmpty()) {
			//there are no planes in the collection
			System.out.println("Cannot add a plane! No open runways!\n");
		}
		else {
			System.out.println("Enter flight number: ");
			String flightNumber = brin.readLine();
			System.out.println(flightNumber);
			while (planeNames.search(flightNumber) >= 0) {
				//the name matches an existing plane name in the collection
				//prompts for a new name until it is unique
				System.out.println("Flight number already assigned to a plane!");
				System.out.println("Enter flight number: ");
				flightNumber = brin.readLine();
				System.out.println(flightNumber);
			}
			//adds the plane's name to the collection of plane names
			planeNames.add(flightNumber);
			System.out.println("Enter destination: ");
			String destination = brin.readLine();
			System.out.println(destination);
			System.out.println("Enter runway: ");
			String runway = brin.readLine();
			System.out.println(runway);
			//variable to keep track of runway we want to add new plane to
			Runway currRunway = searchRunway(takeoffRunways, runway);
			while(currRunway == null) {
				//runway name entered does not match any in the collection
				//will prompt until a runway that exists is selected
				System.out.println("No such runway!");
				System.out.println("Enter runway: ");
				runway = brin.readLine();
				System.out.println(runway);
				currRunway = searchRunway(takeoffRunways, runway);
			}
			//creates a new Plane object and adds it to the specified Runway
			Plane newPlane = (new Plane(flightNumber, destination, runway));
			currRunway.addPlane(newPlane);
			System.out.println("Flight " + flightNumber + " is now waiting for takeoff on runway " + runway + ".\n");
		} //end if
	} //end planeEnters

	/**
	 * Attempts to move a plane from the collection of clearance planes back into a runway.
	 * 
	 * @param takeoffRunways collection of TakeoffRunways
	 * @param clearanceList collection of planes that need to get clearance to re-enter a runway
	 * @throws IOException if the user input cannot be read as String
	 */
	public static void planeReenters(ListArrayBasedPlus<TakeoffRunway> takeoffRunways,ListArrayBasedPlus<Plane> clearanceList ) throws IOException {
		if (!clearanceList.isEmpty()) {
			//the list of clearance planes is not empty
			System.out.println("Enter flight number: ");
			String flightNumber = brin.readLine();
			System.out.println(flightNumber);
			//variable to keep track of the index of the flight number in the collection
			int index = searchClearanceList(clearanceList, flightNumber);
			while (index < 0) {
				//the index will be less than 0 if the flight number was not found
				//will prompt for a new flight number until it matches one in the clearance list
				System.out.println("Flight " + flightNumber + " is not waiting for clearance.");
				System.out.println("Enter flight number: ");
				flightNumber = brin.readLine();
				System.out.println(flightNumber);
				//updates the index variable
				index = searchClearanceList(clearanceList, flightNumber);
			}
			
			//variable to keep track of the 
			Plane temp = clearanceList.get(index);
			//removes the plane from the clearance list
			clearanceList.remove(index);
			String runway = temp.getRunway();
			//variable to keep track of the Runway to add to
			TakeoffRunway tempRunway = (TakeoffRunway) searchRunway(takeoffRunways, runway);
			//adds the Plane removed from the clearance list back into the Runway it was waiting for clearance on
			tempRunway.addPlane(temp);
			System.out.println("Flight " + flightNumber + " is now waiting for takeoff on runway " + tempRunway.getName() + ".\n");
		}
		else {
			System.out.println("There are no planes waiting for clearance!");
		} //end if
	} //end planeReenters

	/**
	 * Checks to see if there are any planes waiting for takeoff in any of the runways.
	 * 
	 * @param takeoffRunways collection of TakeoffRunways
	 * @return true if there are no planes waiting false if there are planes waiting
	 */
	public static boolean checkForPlanesWaiting(ListArrayBasedPlus<TakeoffRunway> takeoffRunways) {
		//flag used to keep track of whether planes are waiting to takeoff
		boolean noPlanes = true;
		//number of runways in the collection
		int size = takeoffRunways.size();
		for (int i = 0; i < size && noPlanes; i++) {
			if (!takeoffRunways.get(i).noPlanes()) {
				//there are planes waiting
				noPlanes = false;
			} //end if
		}
		return noPlanes;
	} //end checkForClearance

	/**
	 * Displays information about planes waiting to takeoff on every runway.
	 * Calls the toString method of the TakeoffRunway class.
	 * 
	 * @param takeoffRunways collection of TakeoffRunways
	 */
	public static void displayTakeOff(ListArrayBasedPlus<TakeoffRunway> takeoffRunways) {
		if (takeoffRunways.isEmpty()) {
			//there are no runways
			System.out.println("There are no runways open!\n");
		}
		else {
			
			//there is atleast one runway
			String info = "";
			info = takeoffRunways.toString().trim();
			info = info.trim();
			info = info.replaceAll("\\.\\s", ".");
			info = info.replaceAll("\\.\\s", ".");
			info = info.replaceAll("\\.", ".\n");
			System.out.println(info);
		} //end if
	} //end displayTakeOff

	/**
	 * Displays information about the planes waiting for clearance.
	 * Calls the toString method of the ListArrayBasedPlus class.
	 *  
	 * @param clearanceList List of planes that are waiting for clearance to re-enter a runway
	 */
	public static void displayClearance(ListArrayBasedPlus<Plane> clearanceList) {
		if (!clearanceList.isEmpty()) {
			//the clearance collection is not empty
			String info = "";
			info = clearanceList.toString();
			info = info.trim();
			info = info.replaceAll("\\.\\s", ".");
			info = info.replaceAll("\\.", ".\n");
			System.out.println("These planes are waiting to be cleared to re-enter a runway:");
			System.out.println(info);
		}
		else{
			//the clearance collection is not empty
			System.out.println("No planes are waiting to be cleared to re-enter a runway!\n");
		} //end if
	} //end displayClearance

	/**
	 * Attempts to have a plane takeoff from the airport.
	 * If the user specifies that the Plane is able to takeoff, removes it from the Runway collection
	 * and removes its name from the list of plane names.
	 * If the user specifies that the plane needs clearance, adds it to the collection of planes that need
	 * clearance to be allowed to re-enter a runway.
	 * 
	 * @param takeOff collection of planes waiting for takeoff
	 * @param data object used to keep track of counting variables
	 * @param clearanceList collection of planes that need clearance to re-enter a runway
	 * @param planeNames collection of plane names
	 * @throws IOException if the user input cannot be read as String
	 */
	public static void takeOff(ListArrayBasedPlus<TakeoffRunway> takeOff, AirportData data, ListArrayBasedPlus<Plane> clearanceList, AscendinglyOrderedStringList planeNames) throws IOException{
		//Check if any planes are available for take off
		if (checkForPlanesWaiting(takeOff)) {
			//there are no planes waiting
			System.out.println("No plane on any runway!\n");
		}
		else {
			//there are planes waiting
			//the number of runways
			int size = takeOff.size();
			//counting variable used to access list of Runways in a circular fashion
			int runwayCounter = data.getRunwayCounter();
			//variable to keep track of current Runway - rotation of the available runways
			TakeoffRunway current = takeOff.get(runwayCounter);
			//if runway is empty, increment to the next one
			while(current.noPlanes()){
				//the current runway has no planes on it
				//move on to the next runway circularly
				current = takeOff.get((++runwayCounter)%size);	
			}
			//variable to keep track of current plane
			Plane currPlane = current.removePlane();
			System.out.println("Is flight " +currPlane.getFlightNumber() + " cleared for takeoff(Y/N): ");
			String choice = brin.readLine();
			System.out.println(choice);
			if(choice.equalsIgnoreCase("Y")){
				//user specified that the plane can take off
				System.out.println("Flight " +currPlane.getFlightNumber() + " has now taken off from runway " + current.getName() + ".\n");
				//updates counting variable for planes that have taken off by 1
				data.setTakenOff(data.getTakenOff()+1);
				//removes the plane'sname from the collection of plane names
				planeNames.remove(planeNames.search((currPlane.getFlightNumber())));
			}
			else{
				//user says that the plane is not cleared for takeoff
				System.out.println("Flight " +currPlane.getFlightNumber() + " is now waiting to be allowed to re-enter a runway.\n");
				//size of the clearance list
				int clearSize = clearanceList.size();
				//adds the plane to the end of the list of clearance planes
				clearanceList.add(clearSize, currPlane);
			} //end if
			//increments the counting variable that accesses the list of runways circularly by 1
			data.setRunwayCounter((++runwayCounter)%size);
		} //end if
	}

	/**
	 * Adds a new Runway to the collection of Runways.
	 * Only adds the Runway if the name specified is unique.
	 * 
	 * @param takeoffRunways collection of TakeoffRunways
	 * @throws IOException if the user input cannot be read as String
	 */
	public static void addNewRunway(ListArrayBasedPlus<TakeoffRunway> takeoffRunways) throws IOException{
		System.out.println("Enter the name of the new runway: ");
		String runway = brin.readLine();
		System.out.println(runway);
		while(searchRunway( takeoffRunways, runway ) != null) {
			//the runway name specified already exists
			//prompts the user to enter a new name until it is unique
			System.out.println("Runway " + runway +" already exists, please choose another name.");
			System.out.println("Please Enter a different runway name: ");
			runway = brin.readLine();
			System.out.println(runway);
		}
		//adds a new Runway to the end of the collection of Runways
		takeoffRunways.add(takeoffRunways.size(), new TakeoffRunway(runway));
		System.out.println("Runway " + runway + " has opened.\n");
	} //end addNewRunway

	/**
	 * Closes a runway specified by the user by name, if it exists.
	 * Will prompt the user to re-enter a name until it matches one in the collection of TakeoffRunways.
	 * This method will not allow a user to close a runway if there is only one runway opem and it has plane
	 * since there will be no runway for these planes to be assigned to. If there are no planes on a runway
	 * and it is the only one, it is able to be closed.
	 * 
	 * @param takeoffRunways collection of TakeoffRunways
	 * @param clearanceList collection of Planes that need clearance to re-enter a runway 
	 * @throws IOException if the user input cannot be read as String
	 */
	public static void closeRunway(ListArrayBasedPlus<TakeoffRunway> takeoffRunways, ListArrayBasedPlus<Plane> clearanceList) throws IOException{
		if (takeoffRunways.isEmpty()) {
			//there are no runways
			System.out.println("There are no runways open to close!\n");
		}
		else {
			//there is atleast one runway
			System.out.println("Enter runway: ");
			//name of the runway we are trying to close
			String runway = brin.readLine();
			System.out.println(runway);
			while(searchRunway( takeoffRunways, runway ) == null) {
				//the name specified does not match any runways in the collection
				//will prompt until there is a match
				System.out.println("No such runway!!!");
				System.out.println("Enter runway: ");
				runway = brin.readLine();
				System.out.println(runway);
			}
			//Runway we are trying to close
			TakeoffRunway  currentRunway = (TakeoffRunway) searchRunway( takeoffRunways, runway );
			if (takeoffRunways.size() == 1) {
				//there is only one runway open
				if (currentRunway.noPlanes()) {
					//the runway has no planes
					//removes all Runways from the collection
					takeoffRunways.removeAll();
					System.out.println("Runway " + currentRunway.getName() + " has been closed.\n");
				}
				else {
					//the only runway open still has planes on it
					System.out.println("Cannot close this runway! No runway(s) open to move waiting planes to!\n");
				} //end if
			}
			else {
				//Checks the planes waiting to takeoff
				while(!currentRunway.noPlanes()){
					//the current runway has planes waiting to takeoff still
					//variable to keep track of plane that was most recently removed
					Plane dequeed = currentRunway.removePlane();
					System.out.println("Enter new runway for plane " +dequeed.getFlightNumber() +": " );
					String newRunway = brin.readLine();
					System.out.println(newRunway);
					//variable to keep track of Runway we are attempting to reassign plane to
					TakeoffRunway temp =(TakeoffRunway) searchRunway(takeoffRunways, newRunway);
					while(temp == null || temp.getName().equalsIgnoreCase(runway) ){
						//there is no Runway that matches the name specified or the user specified the runway
						//that they are trying to close
						if(temp == null){
							//the runway does not match any
							System.out.println("No such runway!");
						}
						else{
							//the user specified the runway they are trying to close
							System.out.println("This is the runway that is closing!");
						}
						//prompts the user to re-enter a name for the runway they want to add the current plane to
						System.out.println("Enter new runway for plane " +dequeed.getFlightNumber() +": " );
						newRunway = brin.readLine();
						temp =(TakeoffRunway) searchRunway(takeoffRunways, newRunway);
						System.out.println(newRunway);
					}
					//add the current plane to the specified runway
					temp.addPlane(dequeed);
					System.out.println("Flight " + dequeed.getFlightNumber() + " is now waiting for takeoff on runway " + temp.getName() + ".");
				}
				
				//Check the clearance list collection now
				for(int i = 0; i< clearanceList.size(); i++){
					Plane clearancePlane = clearanceList.get(i);
					//if the clearance list is not empty, and the runway matches
					if(!clearanceList.isEmpty() && clearancePlane.getRunway().equalsIgnoreCase(runway)){
						System.out.println("Enter new runway for plane " + clearancePlane.getFlightNumber() + ": " );
						String newRunway = brin.readLine();
						System.out.println(newRunway);
						//variable to keep track of Runway we are attempting to reassign plane to
						TakeoffRunway temp =(TakeoffRunway) searchRunway(takeoffRunways, newRunway);
						while(temp == null || temp.getName().equalsIgnoreCase(runway) ){
							//there is no runway that matches the specified name or the runway
							//name specified matches the name of the runway we are trying to close
							if(temp == null){
								//there is no runway that matches the name specified
								System.out.println("No such runway!");
							}
							else{
								//the name specified matches the name of the runway we are trying to close
								System.out.println("This is the runway that is closing!");
							} //end if
							//prompts the user to specify a new runway again
							System.out.println("Enter new runway for plane " + clearancePlane.getFlightNumber() +": " );
							newRunway = brin.readLine();
							System.out.println(newRunway);
							temp =(TakeoffRunway) searchRunway(takeoffRunways, newRunway);
						}
						//sets the new Runway for the removed plane
						//clearanceList.get(i).setRunway(newRunway);
						clearancePlane.setRunway(temp.getName());
						System.out.println("Flight " + clearancePlane.getFlightNumber() + " is now waiting for takeoff on runway " + temp.getName() + ".");
					} //end if
				}
				//searches through the collection of Runways and removes the Runway we are closing
				for(int k = 0; k < takeoffRunways.size();k++){
					if(takeoffRunways.get(k).getName().equalsIgnoreCase(currentRunway.getName())){
						//removes the Runway
						takeoffRunways.remove(k);
						System.out.println("Runway " +currentRunway.getName() + " has been closed.\n");
					} //end if
				}
			} //end if
		}
	}

	/**
	 * Creates and returns a String object that representing the menu options for the user.
	 * 
	 * @return String representing the menu options for the application
	 */
	public static String displayMenu() {
		String menu = "Select from the following menu:\n";
		menu += "\t1. Plane enters the system.\n";
		menu += "\t2. Plane takes off.\n";
		menu += "\t3. Plane is allowed to re-enter a runway.\n";
		menu += "\t4. Runway opens.\n";
		menu += "\t5. Runway closes.\n";
		menu += "\t6. Display info about planes waiting to take off.\n";
		menu += "\t7. Display info about planes waiting to be allowed to re-enter a runway.\n";
		menu += "\t8. Display number of planes who have taken off.\n";
		menu += "\t9. End the program.\n";
		menu += "Make your selection now: ";
		return menu;
	} //end displayMenu

} //end Airport