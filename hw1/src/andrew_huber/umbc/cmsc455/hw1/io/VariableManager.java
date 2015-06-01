package andrew_huber.umbc.cmsc455.hw1.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

/**
 * This class manages variables and their associated values
 * @author andrew_huber
 */
public class VariableManager {
	
	/** Stores the variables as Strings and and their associated values as Doubles */
	private HashMap<String, Double> manager;
	
	/** Creates a new {@link VariableManager} */
	public VariableManager() {
		manager = new HashMap<String, Double>();
	}
	
	/**
	 * Adds a key-value pair to the variable manager
	 * @param variableName the name of the variable to store in this manager
	 * @param value the value that is associated with {@code variableName}
	 */
	public void addValue(String variableName, Double value) {
		if (manager.containsKey(variableName)) {
			manager.remove(variableName);
		}
		
		manager.put(variableName, value);
	}
	
	/**
	 * Gets the variable associated with a particular variable
	 * @param variableName the name of the variable
	 * @return the value associated with {@code variableName}
	 */
	public Double getValue(String variableName) {
		Double val = manager.get(variableName);
		
		if(val != null)
			return val;
		else
			throw new RuntimeException(String.format("Unable to find value \"%s\"", variableName));
	}
	
	/**
	 * Gets {@link Set} of the variable names stored in this {@link VariableManager}
	 * @return a {@link Set} of the variable names
	 */
	public Set<String> getVariableNames() {
		return manager.keySet();
	}
	
	/**
	 * Gets a {@link Collection} of all values stored in this {@link VariableManager}
	 * @return a {@link Collection} of all the values
	 */
	public Collection<Double> getValues() {
		return manager.values();
	}
}