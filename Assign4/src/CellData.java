
/**
 * This class represents the data items that will be stored in the array.
 * @author Julia Anantchenko
 *
 * @param <T>: the generic type
 */
public class CellData<T> {
	
	/** The identifier of the object */
	private T id;
	
	/** The value of the data item */
	private int value;
	
	/**
	 * The constructor, initializes the identifier and value
	 *
	 * @param theId: the identifier
	 * @param theValue:the value
	 */
	public CellData(T theId, int theValue) {
		id = theId;
		value = theValue;
	}
	
	/**
	 * Returns the value of the object
	 * 
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Returns the identifier of the object
	 * 
	 * @return the identifier
	 */
	public T getId() {
		return id;
	}
	
	/**
	 * Sets a new value to the object
	 * 
	 * @param newValue: the new value
	 */
	public void setValue(int newValue) {
		value = newValue;
	}
	
	/**
	 * Sets a new identifier to the object
	 * 
	 * @param newId: the new identifier
	 */
	public void setId(T newId) {
		id = newId;
	}
}
