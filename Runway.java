package v1;
/**
 * Purpose: Semester project Airport Simulator
 * Status: Complete and thoroughly tested
 * Last update: 11/7/17
 * Submitted:  12/05/17
 * Comment: test suite and sample run attached
 * @authors: Justin Davis and Mantas Pileckis
 * @version: 2017.11.7
 * 
 * Runway class : Abstract class that contains information about runway.
 */

public abstract class Runway {
	//Name of the Runway
	protected String name;
	//Queue for the planes waiting to take off from the Runway
	protected Queue<Plane> planesWaiting;

	/**
	 * Runway Constructor 
	 * @param name The name of the runway. 
	 */
	public Runway(String name) {
		//Name assignment.
		this.name = name;
		//Queue assignment. 
		this.planesWaiting = new Queue<Plane>();
	} //end constructor

	/**
	 * Boolean method to check if there are any planes in the Queue. 
	 * @return True if there are none, False if there is at least 1 plane.
	 */
	public boolean noPlanes() {
		return planesWaiting.isEmpty();
	} //end isEmpty

	/**
	 * Method to add the plane into planes waiting to take off Queue.
	 * @param plane the plane which is going to be added to the Queue.
	 */
	public void addPlane(Plane plane) {
		planesWaiting.enqueue(plane);
	} //end addPlane

	/**
	 * Method to remove a plane from the Queue.
	 * @return Returns the plane which is removed from the Queue.
	 * @exception QueueException if collection is empty 
	 */
	public Plane removePlane() {
		Plane plane = planesWaiting.dequeue();
		return plane;
	} //end takeOff
	/**
	 * Getter method for the name
	 * @return The name of the runway.
	 */
	public String getName() {
		return name;
	} //end getName

	/**
	 * Setter method for the name of the runway
	 * @param name The name which is going to be associated with the runway
	 */
	public void setName(String name) {
		this.name = name;
	} //end setName

	/**
	 * Method to create a string containing the information about the planes waiting to take off.
	 * @return Formated string with planes waiting to take off. 
	 */
	public String toString() {
		String info = planesWaiting.toString();
		return info;
	} //end toString

} //end Runway