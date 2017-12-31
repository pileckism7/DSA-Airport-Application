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
 * List Array Based Plus ADT class.
 */
public class ListArrayBasedPlus<T> extends ListArrayBased<T>
{

	/**
	 * Constructor for the improved array based list
	 */
	public ListArrayBasedPlus()
	{
		super();
	} //end default Constructor

	/**
	 * Method to resize the list once its full.
	 */
	private void resize() { //resizes the array when the capacity of the old one is reached; 
		//doubles the size of the old array
		int newSize = this.size() << 1;
		T[] tempItems = (T[]) new Object[newSize];
		for(int pos = 0;pos < numItems;pos++) {
			tempItems[pos] = items[pos];
		}
		items = tempItems;
	} //end resize

	/**
	 * Method to reverse order of the list.
	 */
	public void reverse() { //reverse the contents of the array
		int numFlips = numItems/2;
		for (int front = 0, back = numItems-1, flips = 0; flips < numFlips; front++, back--, flips++) {
			T temp = items[front];
			items[front] = items[back];
			items[back] = temp;
		}
	} //end reverse

	/**
	 * Method to format a string with all items in the list.
	 * @return a formated string.
	 */
	public String toString() { //returns a String representation of the array
		String info = "";
		for(int pos = 0; pos < numItems; pos++) {
			info += items[pos] + " ";
		}
		return info;
	} //end toString

	/**
	 * Add method for the list, uses index.
	 * @param index the index at which item will be added.
	 * @param item the item which is going to be added to the list.
	 * @exception ListIndexOutOfBoundsException throws an exception if the list is full /// not in this case.
	 */
	public void add(int index, T item) throws ListIndexOutOfBoundsException {
		if (numItems == items.length)
		{
			resize();
		}
		super.add(index, item);
	} //end add

} //end ListArrayBasedPlus
