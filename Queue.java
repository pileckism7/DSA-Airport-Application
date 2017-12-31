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
 * Queue ADT
 */

public class Queue<T> implements QueueInterface<T> {

	protected T[] items;
	protected int front;
	protected int back;
	protected int numItems;

	/**
	 * Queue constructor 
	 */
	public Queue() {
		this.items = (T[]) new Object[3];
		this.front = this.back = this.numItems = 0;
	} //end constructor

	/**
	 * Method to check if the queue is empty or not.
	 * @return True if empty, False if at last 1 item is present. 
	 */
	public boolean isEmpty() {
		return (numItems == 0);
	} //end isEmpty

	/**
	 * Method to add an item to the Queue.
	 * @param newItem the item which is going to be added to the queue.
	 * @exception QueueException, improved implementation, not using the Exception.
	 */
	public void enqueue(T newItem) throws QueueException {
		if (numItems == items.length) { //if array is full, resizes
			resize();
		}
		items[back] = newItem;
		back = (back + 1)%items.length;		
		numItems++;
	} //end enqueue

	/**
	 * Method to remove the first item in the Queue.
	 * @return returns the item removed from the Queue.
	 * @exception QueueException Throws an exception if the Queue is emtpy.
	 */
	public T dequeue() throws QueueException {
		if (numItems != 0) { //array is not empty
			T temp = items[front];
			items[front] = null;
			numItems--;
			front = (front + 1)%items.length;
			return temp;
		}
		else { //array is empty
			throw new QueueException("Queue Exception on dequeue");
		} //end if
	} //end dequeue

	/**
	 * Method to remove all the objects from the Queue.
	 */
	public void dequeueAll() {
		//marks old array for garbage collection and assigns new array
		items = (T[]) new Object[3];
		//resets values of front, back and numItems
		numItems = front = back = 0;
	} //end dequeueAll

	/**
	 * Method to access the first item in the Queue.
	 * @return the first item in the queue.
	 * @exception throws an exception if the queue is empty.
	 */
	public T peek() throws QueueException {
		if (numItems != 0) { //if array is not empty
			return items[front];
		}
		else { //array is empty
			throw new QueueException("Queue Exception on peek");
		} //end if
	} //end peek

	/**
	 * Method to resize the Queue, once it fills up. 
	 */
	protected void resize() {
		//determines new size of the array; double the old size
		int newSize = numItems << 1;
		T[] temp = (T[]) new Object[newSize];
		//adds elements to the beginning of the new array
		for (int i = 0; i < numItems; i++) {
			temp[i] = items[(front+i)%items.length];
		} //end for
		//marks old array for garbage collection and assigns new array
		items = temp;
		//resets values of front, back and numItems
		front = 0;
		back = numItems;
	} //end resize

	/**
	 * Method to create a string with the information in the queue.
	 * @return Formated String with the information in the queue. 
	 */

	public String toString() {
		String info = "";
		for (int count = 0; count < numItems; count++) {
			info += items[(front+count)%items.length] + " ";
		} //end for
		return info;
	} //end toString

} //end Queue