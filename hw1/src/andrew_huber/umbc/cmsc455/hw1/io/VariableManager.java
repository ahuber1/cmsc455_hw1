package andrew_huber.umbc.cmsc455.hw1.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class VariableManager {
	private HashMap<String, Double> manager;
	
	public VariableManager() {
		manager = new HashMap<String, Double>();
	}
	
	public void addKeys(String... keys) {
		for(String key : keys) {
			manager.put(key, null);
		}
	}
	
	public void updateValue(String key, Double value) {
		if (manager.containsKey(key)) {
			manager.remove(key);
			manager.put(key, value);
		}
		else {
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("The key \"%s\" is not in this "
					+ "VariableManager\n\n", key));
			builder.append("The keys within this VariableManager are:\n\n");
			
			Set<String> keys = manager.keySet();
			
			for(String k : keys) {
				builder.append(String.format("\t%s\n", k));
			}
			
			throw new IllegalArgumentException(builder.toString());
		}
	}
	
	public Double getValue(String key) {
		return manager.get(key);
	}
	
	public Set<String> getKeys() {
		return manager.keySet();
	}
	
	public Collection<Double> getValues() {
		return manager.values();
	}
}
