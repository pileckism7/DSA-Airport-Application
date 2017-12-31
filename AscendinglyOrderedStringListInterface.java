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

public interface AscendinglyOrderedStringListInterface 
{
	boolean isEmpty();
	int size();
	void add(String item) throws ListIndexOutOfBoundsException;
	String get(int index) throws ListIndexOutOfBoundsException;
	void remove(int index) throws ListIndexOutOfBoundsException;
	int search(String item);
	void clear();
} 