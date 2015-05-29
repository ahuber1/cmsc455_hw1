package andrew_huber.umbc.cmsc455.hw1.io;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class VariableManager<K, V> {
	private HashMap<K, V> manager;
	
	public VariableManager() {
		manager = new HashMap<K, V>();
	}
	
	public void addValue(K key, V value) {
		if (manager.containsKey(key)) {
			manager.remove(key);
			manager.put(key, value);
		}
		else {
			StringBuilder builder = new StringBuilder();
			builder.append(String.format("The key \"%s\" is not in this "
					+ "VariableManager\n\n", key));
			builder.append("The keys within this VariableManager are:\n\n");
			
			Set<K> keys = manager.keySet();
			
			for(K k : keys) {
				builder.append(String.format("\t%s\n", k));
			}
			
			throw new IllegalArgumentException(builder.toString());
		}
	}
	
	public V getValue(K key) {
		V val = manager.get(key);
		
		if(val != null)
			return val;
		else
			throw new RuntimeException(String.format("Unable to find value \"%s\"", key));
	}
	
	public Set<K> getKeys() {
		return manager.keySet();
	}
	
	public Collection<V> getValues() {
		return manager.values();
	}
}