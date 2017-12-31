package v1;
/**
 *  
 * Purpose: Semester project Airport Simulator
 * Status: Complete and thoroughly tested
 * Last update: 12/04/17
 * Submitted:  12/05/17
 * Comment: test suite and sample run attached
 * @authors: Justin Davis and Mantas Pileckis
 * @version: 2017.12.04
 * 
 * List Array Based ADT.
 */

public class ListArrayBased<T> implements ListInterface<T>
{

	private static final int MAX_LIST = 3;
	protected T[] items;  // an array of list items
	protected int numItems;  // number of items in list

	/**
	 * Constructor for array based list 
	 */
	public ListArrayBased()
	{
		items = (T[]) new Object[MAX_LIST];
		numItems = 0;
	}  // end default constructor

	/**
	 * Method to check if list contains anything.
	 * @return True if its empty, False if atleast 1 item is in the list.
	 */
	public boolean isEmpty()
	{
		return (numItems == 0);
	} // end isEmpty

	/**
	 * Method to return the size of the list.
	 * @return number of items in the list.
	 */
	public int size()
	{
		return numItems;
	}  // end size

	/**
	 * Method which removes everything from the list.
	 */
	public void removeAll()
	{
		// Creates a new array; marks old array for
		// garbage collection.
		items = (T[]) new Object[MAX_LIST];
		numItems = 0;
	} // end removeAll

	/**
	 * Method to add an item to the list based on the index.
	 * @param index the index in which the item will be added.
	 * @param item the item which is going to be added.
	 * @exception ListIndexOutOfBoundsException throws an exception if index specified is invalid.
	 * @exception ListException throws an exception if the list is full.
	 */
	public void add(int index, T item)
			throws  ListIndexOutOfBoundsException
	{
		if(numItems == items.length) //fixes programming style and implementation errors
		{
			throw new ListException("ListException on add");
		}  // end if
		if (index >= 0 && index <= numItems)
		{
			// make room for new element by shifting all items at
			// positions >= index toward the end of the
			// list (no shift if index == numItems+1)
			for (int pos = numItems-1; pos >= index; pos--)  //textbook code modified to eliminate logic error causing ArrayIndexOutOfBoundsException
			{
				items[pos+1] = items[pos];
			} // end for
			// insert new item
			items[index] = item;
			numItems++;
		}
		else
		{
			// index out of range
			throw new ListIndexOutOfBoundsException(
					"ListIndexOutOfBoundsException on add");
		}  // end if
	} //end add

	/**
	 * Method to get an item from the list based on the index.
	 * @param index the index by which an item will be retrieved.
	 * @return the item at the specified index.
	 * @exception ListIndexOutOfBoundsException throws an exception if the Index is invalid.
	 */
	public T get(int index)
			throws ListIndexOutOfBoundsException
	{
		if (index >= 0 && index < numItems)
		{
			return items[index];
		}
		else
		{
			// index out of range
			throw new ListIndexOutOfBoundsException(
					"ListIndexOutOfBoundsException on get");
		}  // end if
	} // end get

	/**
	 * Remove method for the list, uses index to locate an item.
	 * @param index The index in which an item will be removed from the list from. 
	 * @exception ListIndexOutOfBoundsException throws an exception if the Index is invalid.
	 */
	public void remove(int index)
			throws ListIndexOutOfBoundsException
	{
		if (index >= 0 && index < numItems)
		{
			// delete item by shifting all items at
			// positions > index toward the beginning of the list
			// (no shift if index == size)
			for (int pos = index+1; pos < numItems; pos++) //textbook code modified to eliminate logic error causing ArrayIndexOutOfBoundsException

			{
				items[pos-1] = items[pos];
			}  // end for
			items[--numItems] = null; //fixes memory leak
		}
		else
		{
			// index out of range
			throw new ListIndexOutOfBoundsException(
					"ListIndexOutOfBoundsException on remove");
		}  // end if
	} //end remove

} //end ListArrayBased