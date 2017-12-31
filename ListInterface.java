package v1;
/**
 * Purpose: Semester project Airport Simulator
 * Status: Complete and thoroughly tested
 * Last update: 12/04/17
 * Submitted:  12/05/17
 * Comment: test suite and sample run attached
 * @authors: Justin Davis and Mantas Pileckis
 * @version: 2017.12.04
 */

public interface ListInterface <T>
{
	boolean isEmpty();
	int size();
	void add(int index, T item)
			throws ListIndexOutOfBoundsException;
	T get(int index)
			throws ListIndexOutOfBoundsException;
	void remove(int index)
			throws ListIndexOutOfBoundsException;
	void removeAll();
}  // end ListInterface