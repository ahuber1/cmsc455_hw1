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
		}
		
		manager.put(key, value);
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