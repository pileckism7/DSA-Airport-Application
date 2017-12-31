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
 * Ascendingly Ordered String list ADT.
 */

public class AscendinglyOrderedStringList implements AscendinglyOrderedStringListInterface {

	private static final int MAX_LIST = 3;
	protected String[] items;  // an array of String items
	protected int numItems;  // number of items in list

	/**
	 * Constructor for the Ascendignly ordered String list
	 */
	public AscendinglyOrderedStringList() {
		items = new String[MAX_LIST];
		numItems = 0;
	}  // end default constructor

	/**
	 * Method to check if the list is emtpy.
	 * @return True if the list is empty, False is the list contains at least 1 item.
	 */
	public boolean isEmpty() {
		return (numItems == 0);
	} // end isEmpty

	/**
	 * Method which returns the size (amount of items) in the list.
	 * @return The number of items in the list.
	 */
	public int size() {
		return numItems;
	}  // end size

	/**
	 * Method which clears the list.
	 */
	public void clear() {
		// Creates a new array; marks old array for
		// garbage collection.
		items = new String[MAX_LIST];
		numItems = 0;
	} // end removeAll

	/**
	 * Method to add an item to the list.
	 * @param item The item which is going to be addded. 
	 * @exception ListIndexOutOfBoundsException throws exception if the item is not unique / 
	 * also if item is within the range of the list.
	 */
	public void add(String item) throws ListIndexOutOfBoundsException {
		int searchValue = search(item);
		if(numItems == items.length) { //resize if full
			resize();
		} // end if
		if (searchValue >= 0) { //trying to add an item that is not unique
			throw new ListException("ListException on add");
		}
		else {
			int decodedPos = searchValue + (numItems + 1); //encoding/decoding value is (numItems + 1)
			if (decodedPos >= 0 && decodedPos <= numItems) {
				// make room for new element by shifting all items at
				// positions >= decodedPos toward the end of the
				// list (no shift if decodedPos == numItems+1)
				for (int pos = numItems-1; pos >= decodedPos; pos--) {
					items[pos+1] = items[pos];
				} // end for
				// insert new item
				items[decodedPos] = item;
				numItems++;
			}
			else {
				throw new ListIndexOutOfBoundsException("ListIndexOutOfBoundsException on add");
			} //end if
		} //end if
	} //end add

	/**
	 * Method to get the item from the list.
	 * @param index, index which we are going to be accessing the list with.
	 * @exception ListIndexOutOfBoundsException throws an exception if the 
	 * index is out of range within the list.
	 */
	public String get(int index) throws ListIndexOutOfBoundsException {
		if (index >= 0 && index < numItems) {
			return items[index];
		}
		else{
			// index out of range
			throw new ListIndexOutOfBoundsException(
					"ListIndexOutOfBoundsException on get");
		}  // end if
	} // end get

	/**
	 * Method that removes an item from the list based on the index.
	 * @param index index which is going to be searching in the list to remove.
	 * @exception ListIndexOutOfBoundsException throws exception if index is out of the range within the list.
	 */
	public void remove(int index) throws ListIndexOutOfBoundsException {
		if (index >= 0 && index < numItems) {
			// delete item by shifting all items at
			// positions > index toward the beginning of the list
			// (no shift if index == size)
			for (int pos = index+1; pos < numItems; pos++) {
				items[pos-1] = items[pos];
			}  //end for
			items[--numItems] = null;
		}
		else {
			// index out of range
			throw new ListIndexOutOfBoundsException("ListIndexOutOfBoundsException on remove");
		}  // end if
	} //end remove

	/*
	 * Uses Binary Search Method II
	 * Eagerly advances until we reach a sublist of size 1
	 */

	/**
	 * Search method for the list
	 * @param key, the key which is going to be searching within the list.
	 * @return the index of the item that is found in the list. 
	 */
	public int search(String key) {
		int encodeValue = numItems + 1, pos = 0, low = 0, high = numItems - 1, mid = 0;
		if (numItems > 0) { //checks to make sure the list has at least one item
			while (low != high) { //eagerly reduce until a subList of 1
				mid = (low + high)/2;
				if (key.compareTo(items[mid]) > 0) { //key is lexicographically after mid
					low = mid + 1;
				}
				else { //key comes lexicographically before mid
					high = mid;
				} //end if
			}
			if (key.compareTo(items[low]) == 0) {
				//successful search!
				pos = low;
			}
			else {
				//unsuccessful search!
				if (key.compareTo(items[low]) > 0) { //Option 1: adding to end of the list
					pos = numItems - encodeValue;
				}
				else { //Option 2: adding somewhere else
					pos = low - encodeValue;
				}
			} //end if
		} 
		else { //list has zero items
			pos = -1;			
		} //end if
		return pos;
	} //end search

	/**
	 * Method to resize the collection 
	 */
	private void resize() { //resizes the array when the capacity of the old one is reached 
		//doubles the size of the old array
		int newSize = this.size() << 1;
		String[] tempItems = new String[newSize];
		for(int pos = 0; pos < numItems; pos++) {
			tempItems[pos] = items[pos];
		}
		items = tempItems;
	} //end resize

	/**
	 * Method to build the string containing the information about the list.
	 * @return the formated string.
	 */
	public String toString() {
		String info = "";
		for (int i = 0; i < numItems; i++) {
			info += items[i] + " ";
		} //end for
		return info;
	} //end toString

} //end AscendinglyOrderedStringList