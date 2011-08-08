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

import java.util.Arrays;

public class OrderedMultiDimensionalMap<K, V> implements MultiDimensionalMap<K, V> {

	private final MultiDimensionalArray<V> array;
	private K[][] index;
	private int[] length;

	@SuppressWarnings("unchecked")
	public OrderedMultiDimensionalMap(MultiDimensionalArray<V> array) {
		this.array = array;
		index = (K[][]) new Object[array.dimensions()][];
		length = new int[array.dimensions()];
		for (int i = 0; i < index.length; i++) {
			index[i] = (K[]) new Object[array.length()[i]];
		}
	}

	private int[] computeAddress(K... key) {
		if (key.length != index.length)
			throw new IllegalArgumentException("Wrong number of dimensions: " + key.length);
		
		int[] address = new int[key.length];
		Arrays.fill(address, -1);
		
		for (int i = 0; i < key.length; i++) {
			K[] index = this.index[i];
			if (key[i] != null) {
				for (int j = 0; j < index.length; j++) {
					if (key[i].equals(index[j])) {
						address[i] = j;
					}
				}
			} else {
				for (int j = 0; j < index.length; j++) {
					if (index[j] == null) {
						address[i] = j;
					}
				}
			}
		}
		
		for (int i = 0; i < address.length; i++) {
			if (address[i] < 0) {
				allocAddress(i, key[i], address);
			}
		}
		
		return address;
	}

	private void allocAddress(int dimension, K key, int[] address) {
		address[dimension] = length[dimension]++;
		index[dimension][address[dimension]] = key;
	}

	public V get(K... key) {
		return array.get(computeAddress(key));
	}

	public void put(V element, K... key) {
		array.set(element, computeAddress(key));
	}

	public int length(int dimension) {
		if (dimension < 0 || dimension >= length.length)
			throw new IllegalArgumentException("Illegal dimension: " + dimension);
		
		return length[dimension];
	}

	public int dimensions() {
		return array.dimensions();
	}

}
