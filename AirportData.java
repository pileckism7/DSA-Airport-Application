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
 * Airport Data Class : Keeps the data associated with the Airport application.
 */

public class AirportData {
	//Counter for how many planes have taken off.
	private int takenOff;
	//Counter to keep track of order for the runways.
	private int runwayCounter;

	/**
	 * Constructor for the airport data.
	 */
	public AirportData() {
		this.takenOff = 0;
		this.runwayCounter = 0;
	} //end constructor

	/**
	 * Getter method for the take off counter
	 * @return the number of the planes that have taken off.
	 */
	public int getTakenOff() {
		return takenOff;
	} //getTakenOff

	/**
	 * Setter method to set the counter of the planes taken off.
	 * @param takenOff the number to set.
	 */
	public void setTakenOff(int takenOff) {
		this.takenOff = takenOff;
	} //setTakenOff

	/**
	 * Getter method for the runway counter.
	 * @return the number associated with runways keeping the right track.
	 */
	public int getRunwayCounter() {
		return runwayCounter;
	} //getRunwayCount

	/**
	 * Setter method for the counter associated witht the runways.
	 * @param runwayCounter The number which is going to be replaced.
	 */
	public void setRunwayCounter(int runwayCounter) {
		this.runwayCounter = runwayCounter;
	} //setRunwayCounter

} //end AirportData