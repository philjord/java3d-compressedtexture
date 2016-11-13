package tools;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class WeakValueHashMap<K, V> implements Map<K, V>
{
	private final ReferenceQueue<WeakReferenceKey<V>> queue = new ReferenceQueue<WeakReferenceKey<V>>();

	private LinkedHashMap<K, WeakReferenceKey<V>> map = new LinkedHashMap<K, WeakReferenceKey<V>>();

	public V get(Object key)
	{
		expungeStaleEntries();
		WeakReferenceKey<V> ref = map.get(key);
		if (ref != null)
		{
			V v = ref.get();

			return v;
		}
		return null;

	}

	public V put(K key, V value)
	{
		expungeStaleEntries();
		V oldV = get(key);
		WeakReferenceKey<V> kv = new WeakReferenceKey<V>(key, value, queue);
		map.put(key, kv);
		return oldV;
	}

	public void clear()
	{
		map.clear();
	}

	public int size()
	{
		return map.size();
	}

	public Set<K> keySet()
	{
		return map.keySet();
	}

	/**
	* Expunges stale entries from the table.
	*/
	@SuppressWarnings("unchecked")
	private void expungeStaleEntries()
	{
		WeakReferenceKey<V> ref;
		while ((ref = (WeakReferenceKey<V>) queue.poll()) != null)
		{
			map.remove(ref.key);
		}
	}

	private static class WeakReferenceKey<Z> extends WeakReference<Z>
	{
		public Object key;

		@SuppressWarnings(
		{ "unchecked", "rawtypes" })
		public WeakReferenceKey(Object k, Z v, ReferenceQueue queue)
		{
			super(v, queue);
			this.key = k;
		}
	}

	@Override
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key)
	{
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value)
	{
		return map.containsValue(value);
	}

	@Override
	public V remove(Object key)
	{
		expungeStaleEntries();
		WeakReferenceKey<V> ref = map.remove(key);
		if (ref != null)
		{
			V v = ref.get();

			return v;
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m)
	{
		for (K key : m.keySet())
		{
			put(key, m.get(key));
		}
	}

	@Override
	public Collection<V> values()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet()
	{
		throw new UnsupportedOperationException();
	}

}
