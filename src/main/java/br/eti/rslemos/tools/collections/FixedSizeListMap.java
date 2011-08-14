/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Collections"
 * Copyright 2011  Rodrigo Lemos
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.tools.collections;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class FixedSizeListMap<K, V> extends AbstractList<Map<K, V>> implements List<Map<K, V>> {

	protected MultiDimensionalArray<V> array;
	protected final K[] keys;

	public FixedSizeListMap(int length, K... keys) {
		this(new SimplePackedArray<V>(length, keys.length), keys);
	}

	protected FixedSizeListMap(MultiDimensionalArray<V> array, K... keys) {
		if (array.length()[1] != keys.length)
			throw new IllegalArgumentException("Second dimension must have exact number of keys");
		
		this.array = array;
		this.keys = keys.clone();
		Arrays.sort(this.keys);
	}

	protected V get(int i, int j) {
		return array.get(i, j);
	}

	protected V get(int i, Object key) {
		int idx = Arrays.binarySearch(keys, key);
		if (idx < 0)
			return null;
		
		return get(i, idx);
	}

	protected V set(V value, int i, int j) {
		return array.set(value, i, j);
	}

	protected V put(int i, K key, V value) {
		int idx = Arrays.binarySearch(keys, key);
		if (idx < 0)
			throw new IllegalArgumentException();
		
		return set(value, i, idx);
	}

	protected V remove(int i, Object key) {
		throw new UnsupportedOperationException();
	}

	protected boolean containsKey(int i, Object key) {
		return Arrays.binarySearch(keys, key) >= 0;
	}

	protected K getKey(int i, int j) {
		return keys[j];
	}
	
	protected int size(int index) {
		return array.length()[1];
	}
	
	@Override
	public Map<K, V> get(int i) {
		if (i >= 0 && i < size())
			return new FixedMap(i);
		else
			throw new IndexOutOfBoundsException();
	}
	
	@Override
	public Map<K, V> set(int i, Map<K, V> element) {
		Map<K, V> map = get(i);
		
		for (K key : keys) {
			map.put(key, element.get(key));
		}
		
		return map;
	}

	@Override
	public int size() {
		return array.length()[0];
	}

	private class FixedMap extends AbstractMap<K, V> {
		private final int i;

		public FixedMap(int i) {
			this.i = i;
		}

		@Override
		public Set<Entry<K, V>> entrySet() {
			return new FixedSet(i);
		}

		@Override
		public V get(Object key) {
			return FixedSizeListMap.this.get(i, key);
		}

		@Override
		public V put(K key, V value) {
			return FixedSizeListMap.this.put(i, key, value);
		}
		
		@Override
		public V remove(Object key) {
			return FixedSizeListMap.this.remove(i, key);
		}

		@Override
		public boolean containsKey(Object key) {
			return FixedSizeListMap.this.containsKey(i, key);
		}
	}

	private class FixedSet extends AbstractSet<Entry<K, V>> {

		private final int i;

		public FixedSet(int i) {
			this.i = i;
		}

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new FixedIterator(i);
		}

		@Override
		public int size() {
			return FixedSizeListMap.this.size(i);
		}
	}
	
	public class FixedIterator implements Iterator<Entry<K, V>> {

		private final int i;
		private int j;

		public FixedIterator(int i) {
			this.i = i;
			this.j = 0;
		}

		public boolean hasNext() {
			return j < array.length()[1];
		}

		public Entry<K, V> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			try {
				return new FixedEntry(i, j);
			} finally {
				j++;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public class FixedEntry implements Entry<K, V> {

		private final int i;
		private final int j;

		public FixedEntry(int i, int j) {
			this.i = i;
			this.j = j;
		}

		public K getKey() {
			return FixedSizeListMap.this.getKey(i, j);
		}

		public V getValue() {
			return FixedSizeListMap.this.get(i, j);
		}

		public V setValue(V value) {
			return FixedSizeListMap.this.set(value, i, j);
		}

		@Override
		public boolean equals(Object o) {
			// adapted from java.util.AbstractMap.SimpleEntry.equals(Object)
			if (!(o instanceof Entry))
				return false;

			K key = getKey();
			V value = getValue();

			@SuppressWarnings("rawtypes")
			Entry e = (Entry)o;
			return eq(key, e.getKey()) && eq(value, e.getValue());
		}

		@Override
		public int hashCode() {
			// adapted from java.util.AbstractMap.SimpleEntry.hashCode()
			K key = getKey();
			V value = getValue();

			return (key   == null ? 0 :   key.hashCode()) ^
					(value == null ? 0 : value.hashCode());
		}

		@Override
		// though not mandated by java.util.Map.Entry
		public String toString() {
			// adapted from java.util.AbstractMap.SimpleEntry.toString()
			return getKey() + "=" + getValue();
		}
	}

	private static boolean eq(Object o1, Object o2) {
		// copied from java.util.AbstractMap.eq(Object, Object)
		return o1 == null ? o2 == null : o1.equals(o2);
	}
}
