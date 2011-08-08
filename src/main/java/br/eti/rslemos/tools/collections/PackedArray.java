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

public abstract class PackedArray<T> implements MultiDimensionalArray<T> {
	protected final int[] sizes;
	private T[] data;

	@SuppressWarnings("unchecked")
	public PackedArray(int... sizes) {
		this.sizes = sizes;
		data = (T[]) new Object[computeSize()];
	}
	
	private int computeSize() {
		int result = 1;
		
		for (int size : sizes) {
			result *= size;
		}
		
		return result;
	}
	
	protected abstract int computeAddress(int... pos);

	private void checkBoundaries(int[] pos) {
		if (pos.length != sizes.length)
			throw new IllegalArgumentException("Wrong number of dimensions: " + pos.length);
		
		for (int i = 0; i < pos.length; i++) {
			if (pos[i] < 0 || pos[i] >= sizes[i])
				throw new ArrayIndexOutOfBoundsException(pos[i]);
		}
	}

	public T get(int... pos) {
		checkBoundaries(pos);
		return data[computeAddress(pos)];
	}

	public void set(T element, int... pos) {
		checkBoundaries(pos);
		data[computeAddress(pos)] = element;
	}
	
	public int length(int dimension) {
		if (dimension < 0 || dimension >= sizes.length)
			throw new IllegalArgumentException("Illegal dimension: " + dimension);
		
		return sizes[dimension];
	}

	public int dimensions() {
		return sizes.length;
	}
}
