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

public class JavaArrayMultiDimensionalArray<T> implements MultiDimensionalArray<T> {

	private final Object javaArray;
	private final int[] lengths;

	public JavaArrayMultiDimensionalArray(Object javaArray, int... lengths) {
		this.javaArray = javaArray;
		this.lengths = lengths;
	}

	private void checkBoundaries(int... pos) {
		if (pos.length != lengths.length)
			throw new IllegalArgumentException("Wrong number of dimensions: " + pos.length);
		
		if (lengths.length > 0) {
			for (int i = 0; i < lengths.length; i++) {
				if (pos[i] < 0 || pos[i] >= lengths[i])
					throw new ArrayIndexOutOfBoundsException(pos[i]);
			}
		} else
			throw new ArrayIndexOutOfBoundsException();
	}

	@SuppressWarnings("unchecked")
	public T get(int... pos) {
		checkBoundaries(pos);
		
		try {
			Object[] last = (Object[]) javaArray;
			int i;
			for (i = 0; i < pos.length - 1; i++) {
				last = (Object[]) last[pos[i]];
			}
			
			return (T) last[pos[i]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public T set(T element, int... pos) {
		checkBoundaries(pos);
		
		try {
			Object[] last = (Object[]) javaArray;
			int i;
			for (i = 0; i < pos.length - 1; i++) {
				last = (Object[]) last[pos[i]];
			}
			
			T old = (T) last[pos[i]];
			last[pos[i]] = element;
			return old;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayStoreException();
		}
	}

	public int[] length() {
		return lengths.clone();
	}

	public int dimensions() {
		return lengths.length;
	}

	public MultiDimensionalArray<T> slice(int dimension, int from, int to) {
		throw new UnsupportedOperationException();
	}

	public MultiDimensionalArray<T> swap(int dimensionA, int dimensionB) {
		throw new UnsupportedOperationException();
	}

	public MultiDimensionalArray<T> transpose() {
		throw new UnsupportedOperationException();
	}

}
