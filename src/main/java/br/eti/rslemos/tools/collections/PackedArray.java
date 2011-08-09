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

public class PackedArray<T> implements MultiDimensionalArray<T> {
	protected final int[] sizes;
	private final T[] data;
	
	private final int[] strides;
	private final int[] offsets;

	@SuppressWarnings("unchecked")
	protected PackedArray(int[] sizes, int[] strides, int[] offsets) {
		this((T[]) new Object[computeSize(sizes)], sizes, strides, offsets);
	}
	
	private PackedArray(T[] data, int[] sizes, int[] strides, int[] offsets) {
		this.data = data;
		this.sizes = sizes;
		this.strides = strides;
		this.offsets = offsets;
	}
	
	private static int computeSize(int... sizes) {
		int result = 1;
		
		for (int size : sizes) {
			result *= size;
		}
		
		return result;
	}
	
	int computeAddress(int... pos) {
		checkBoundaries(pos);
		
		int address = 0;
		for (int i = 0; i < pos.length; i++) {
			address += (pos[i] + offsets[i]) * strides[i];
		}
		
		return address;
	}

	private void checkBoundaries(int[] pos) {
		if (pos.length != sizes.length)
			throw new IllegalArgumentException("Wrong number of dimensions: " + pos.length);
		
		for (int i = 0; i < pos.length; i++) {
			if (pos[i] < 0 || pos[i] >= sizes[i])
				throw new ArrayIndexOutOfBoundsException(pos[i]);
		}
	}

	public T get(int... pos) {
		return data[computeAddress(pos)];
	}

	public void set(T element, int... pos) {
		data[computeAddress(pos)] = element;
	}
	
	public PackedArray<T> slice(int dimension, int from, int to) {
		if (dimension < 0 || dimension >= sizes.length)
			throw new IllegalArgumentException("Illegal dimension: " + dimension);
		
		if (from < 0 || from > sizes[dimension])
			throw new ArrayIndexOutOfBoundsException(from);
		
		if (to < 0 || to > sizes[dimension])
			throw new ArrayIndexOutOfBoundsException(to);
		
		if (to < from)
			throw new ArrayIndexOutOfBoundsException();
		
		int[] newSizes = sizes.clone();
		newSizes[dimension] = to - from;
		
		int[] newOffsets = offsets.clone();
		newOffsets[dimension] += from;
		
		return new PackedArray<T>(this.data, newSizes, strides, newOffsets);
	}

	public int[] length() {
		return sizes.clone();
	}

	public int dimensions() {
		return sizes.length;
	}
}
