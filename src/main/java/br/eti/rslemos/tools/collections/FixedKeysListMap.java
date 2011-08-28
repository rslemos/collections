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

import java.util.Collections;
import java.util.Map;

public class FixedKeysListMap<K, V> extends FixedSizeListMap<K, V> {

	private int size;
	
	public FixedKeysListMap(K... keys) {
		super(new SimplePackedArray<V>(0, keys.length), keys);
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public void add(int index, Map<K, V> element) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		
		if (element == null)
			throw new NullPointerException();
		
		ensureCapacity(size + 1);
		
		size++;
		try {
			MultiDimensionalArrays.copy(array, new int[] {index, 0}, array, new int [] {index + 1, 0});
			set(index, element);
		} catch (RuntimeException e) {
			size--;
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> remove(int index) {
		if (index < 0 || index >= size()) 
			throw new IndexOutOfBoundsException();

		modCount++;
		
		Map<K, V> previous = get(index);
        int numMoved = size - index - 1;
        if (numMoved > 0)
        	MultiDimensionalArrays.copy(array, new int[] {index + 1, 0}, array, new int [] {index, 0}, new int[] {numMoved, keys.length});
        
		set(size - 1, (Map<K, V>) Collections.emptyMap());
		size--;
		
		return previous;
	}

	private void ensureCapacity(int minCapacity) {
		// adapted from java.util.ArrayList.ensureCapacity(int)
		modCount++;
		int oldCapacity = array.length()[0];
		if (minCapacity > oldCapacity) {
			MultiDimensionalArray<V> oldData = array;
			int newCapacity = (oldCapacity * 3)/2 + 1;
			if (newCapacity < minCapacity)
				newCapacity = minCapacity;
			// minCapacity is usually close to size, so this is a win:
			array = new SimplePackedArray<V>(newCapacity, array.length()[1]);
			MultiDimensionalArrays.copy(oldData, array);
		}
	}
}
