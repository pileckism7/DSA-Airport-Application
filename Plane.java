package v1;
/**
 * Purpose: Semester project Airport Simulator
 * Status: Complete and thoroughly tested
 * Last update: 12/04/17
 * Submitted:  12/05/17
 * Comment: test suite and sample run attached
 * @authors: Justin Davis and Mantas Pileckis
 * @version: 2017.12.04
 * 
 * Plane class : Keeps information related to the plane object.
 */

public class Plane {
	//Flight name 
	private String flightNumber;
	//Flight destination
	private String destination;
	//Flight runway
	private String runway;

	/**
	 * Getter method for the runway
	 * @return runway associated with the plane.
	 */
	public String getRunway() {
		return runway;
	}

	/**
	 * Setter method for the runway
	 * @param runway which is going to be assigned for the plane.
	 */
	public void setRunway(String runway) {
		this.runway = runway;
	}

	/**
	 * Constructor for the plane class.
	 * @param flightNumber Flight number associated with the plane.
	 * @param destination Flight Desitination associated with the plane.
	 * @param runway Runway associated with the plane.
	 */
	public Plane(String flightNumber, String destination, String runway) {
		this.flightNumber = flightNumber;
		this.destination = destination;
		this.runway = runway;
	} //end constructor

	/**
	 * Getter method for the flight number
	 * @return Flight number associated with the plane.
	 */
	public String getFlightNumber() {
		return flightNumber;
	} //end getFlightNumber

	/**
	 * Setter method for the flight number.
	 * @param flightNumber The flight number which is going to be associated with the plane.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	} //end setFlightNumber

	/**
	 * Getter method for the destination.
	 * @return The destination associated with the plane.
	 */
	public String getDestination() {
		return destination;
	} //end getDestination

	/**
	 * Setter method for the destination.
	 * @param destination The destination which is going to be associated with the plane. 
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	} //end setDestination

	/**
	 * Method which formats the information about plane to a string.
	 * @return Returns the formated string of the information about the plane. 
	 */
	public String toString() {
		return "Flight " + flightNumber + " to " + destination + ".";
	} //end toString

} //end Plane