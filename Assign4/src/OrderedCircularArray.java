
/**
 * @author Julia Anantchenko
 * @param <T>: the generic type
 */
public class OrderedCircularArray<T> implements SortedListADT<T> {

	/** The array that holds the CellData objects */
	private CellData[] list;

	/** The index of the first data item in the list */
	private int front;

	/** The index of the last data item in the list*/
	private int rear;

	/** The number of data items in the list */
	private int count;

	/**
	 * The constructor, creates an empty list
	 */
	public OrderedCircularArray() {
		list = (CellData<T>[]) new CellData[5];
		front = 1;
		rear = 0;
		count = 0;
	}

	/**
	 * Adds one CellData object storing the given dataItem and value to the  
	 * ordered list in the correct place.
	 * 
	 * @param dataItem and value to be added to the ordered list
	 */
	public void insert(T id, int value) {

		// increases the size of the array if it is full
		if (count == list.length)
			expandCapacity();

		// creates a new cell based on the parameters
		CellData<T> newCell = new CellData<T>(id, value);

		// moves everything with a higher value over to make room for the new cell
		int i;
		for (i = count+front; i > front && list[(i-1)%list.length].getValue() > value; i--)
			list[(i)%list.length] = list[(i-1)%list.length];

		// inserts the new cell
		list[i%list.length] = newCell;

		// increasing rear and count
		rear = (rear+1)%list.length;
		count++;
	}

	/**
	 * Returns the value of the given dataItem. Throws an InvalidDataItemException if
	 * no CellData object of the ordered list stores the given dataItem.
	 * 
	 * @param dataItem whose value is being sought
	 * @return value of this dataItem
	 */
	public int getValue(T id) throws InvalidDataItemException {

		// loops through the array to find the id, returns the value
		for (int i = front; i < count+front; i++) {
			if (list[i%list.length].getId().equals(id))
				return list[i%list.length].getValue();
		}
		
		// throws exception if it was not found
		throw new InvalidDataItemException("No data item with id: " + id);
	}

	/**
	 * Removes the given data item. An InvalidDataItemException is thrown if
	 * the given data item is not stored in the sorted list.
	 *
	 * @param data item to remove
	 */
	public void remove(T id) throws InvalidDataItemException {

		// variable to know if the data item was found
		boolean exists = false;

		// loops through the list, once found it shifts the elements over to replace the element
		for (int i = front; i < count+front; i++) {
			if (list[i%list.length].getId().equals(id))
				exists = true;
			if (exists) 
				list[i%list.length] = list[(i+1)%list.length];
		}

		// throws exception if it was not found
		if (!exists)
			throw new InvalidDataItemException("No data item with id: " + id);

		// sets the rear element to null in case it wasn't
		list[rear] = null;
		
		// decreases count and rear
		count--;
		rear = (rear+list.length-1)%list.length;
	}

	/**
	 * Changes the value associated to the given dataItem to the specified new value. Throws an  
	 * InvalidDataItemException if no object of the list stores the given dataItem.
	 * 
	 * @param dataItem whose value is to be changed
	 * @param newValue new value for this dataItem
	 */
	public void changeValue(T id, int newValue) throws InvalidDataItemException {
		
		// tries to remove the data item and insert it with the new value
		try {
			remove(id);
			insert(id, newValue);
		} 
		
		// throws exception if not successful
		catch (InvalidDataItemException e) {
			throw new InvalidDataItemException(e.getMessage());
		}
	}

	/**
	 * Removes and returns the data item of the ordered list with smallest
	 * associated value. Throws an EmptyListException if the ordered list is empty.
	 * 
	 * @return T data item in the ordered list with smallest associated value
	 */
	public T getSmallest() throws EmptyListException {
		
		// throws an exception if the list is empty
		if (isEmpty())
			throw new EmptyListException("Empty list, cannot getSmallest");

		// temporary variable to hold the value of smallest
		T smallest = (T) list[front].getId();
		
		// removes the item and remarks front
		list[front] = null;
		front = (front+1)%list.length;
		count--;
		
		// returns the data item
		return smallest;
	}

	/**
	 * Returns true if this ordered list is empty.
	 * 
	 * @return true if this ordered list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return (count == 0);
	}

	/**
	 * Returns the number of data items in this ordered list.
	 * 
	 * @return int number of data items in this ordered list
	 */
	public int size() {
		return count;

	}

	/**
	 * Returns the value of front.
	 * 
	 * @return int front
	 */
	public int getFront() {
		return front;
	}

	/**
	 * Returns the value of rear.
	 * 
	 * @return int rear
	 */
	public int getRear() {
		return rear;
	}

	/**
	 * Expands the capacity of the array
	 */
	private void expandCapacity() {
		
		// creates a larger array
		CellData[] larger = (CellData<T>[]) new CellData[list.length * 2];

		// puts data from smaller array into the larger one
		for (int i = front; i < list.length+front; i++) {
			larger[i%larger.length] = list[i%list.length];
			rear = i%larger.length;
		}

		// sets the original list to be the larger list
		list = larger;
	}

}
