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
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;

public class FixedListMap<K, V> extends AbstractList<Map<K, V>> implements ListMap<K, V> {

	private final MultiDimensionalArray<V> array;
	private final K[] keys;

	public FixedListMap(MultiDimensionalArray<V> array, K... keys) {
		if (array.length()[1] != keys.length)
			throw new IllegalArgumentException("Second dimension must have exact number of keys");
		
		this.array = array;
		this.keys = keys.clone();
		Arrays.sort(this.keys);
	}

	@Override
	public Map<K, V> get(int index) {
		return new FixedMap(index);
	}

	@Override
	public int size() {
		return array.length()[0];
	}

	private class FixedMap extends AbstractMap<K, V> {
		private final int index;

		public FixedMap(int index) {
			this.index = index;
		}

		@Override
		public Set<Entry<K, V>> entrySet() {
			return new FixedSet(index);
		}

		@Override
		public V get(Object key) {
			int idx = Arrays.binarySearch(keys, key);
			if (idx < 0)
				throw new IllegalArgumentException();
			
			return array.get(index, idx);
		}

		@Override
		public V put(K key, V value) {
			int idx = Arrays.binarySearch(keys, key);
			if (idx < 0)
				throw new IllegalArgumentException();
			
			return array.set(value, index, idx);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public V remove(Object key) {
			return put((K)key, null);
		}

		@Override
		public boolean containsKey(Object key) {
			return Arrays.binarySearch(keys, key) >= 0;
		}
	}

	private class FixedSet extends AbstractSet<Entry<K, V>> {

		private final int index;

		public FixedSet(int index) {
			this.index = index;
		}

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new FixedIterator(index);
		}

		@Override
		public int size() {
			return array.length()[1];
		}
	}
	
	public class FixedIterator implements Iterator<Entry<K, V>> {

		private final int index;
		private int key;

		public FixedIterator(int index) {
			this.index = index;
			this.key = 0;
		}

		public boolean hasNext() {
			return key < array.length()[1];
		}

		public Entry<K, V> next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			try {
				return new FixedEntry(index, key);
			} finally {
				key++;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public class FixedEntry implements Entry<K, V> {

		private final int index;
		private final int key;

		public FixedEntry(int index, int key) {
			this.index = index;
			this.key = key;
		}

		public K getKey() {
			return keys[key];
		}

		public V getValue() {
			return array.get(index, key);
		}

		public V setValue(V value) {
			return array.set(value, index, key);
		}

	}

}
