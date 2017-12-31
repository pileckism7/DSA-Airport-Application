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
 * TakeoffRunway class : Class for the take off runways. 
 */

public class TakeoffRunway extends Runway {

	/**
	 * Take off runway class Constructor.
	 * @param name The name of the runway.
	 */
	public TakeoffRunway(String name) {
		//Super call to the runway class. 
		super(name);
	} //end constructor
	/**
	 * Method that formats a string based on all the items in the list.
	 * @return formated string.
	 */
	public String toString() {
		String info = "";
		if (this.noPlanes()) {
			info = "There are no planes waiting for takeoff on runway " + this.name + ". ";
		}
		else {
			info += "These planes are waiting for takeoff on runway " + this.name + ":\n";
			info += this.planesWaiting.toString();
		} //end if
		return info;
	} //end toString
} //end TakeoffRunway