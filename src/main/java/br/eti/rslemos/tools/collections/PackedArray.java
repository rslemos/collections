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

public class PackedArray<T> implements MultiDimensionalArray<T> {
	protected final int[] sizes;
	private final T[] data;
	
	private final int[] strides;
	private final int[] offsets;

	public PackedArray(int... sizes) {
		this(sizes, computeStrides(sizes), computeOffsets(sizes));
	}

	private static int[] computeStrides(int[] sizes) {
		int[] strides = sizes.clone();
		
		System.arraycopy(sizes, 1, strides, 0, strides.length - 1);
		strides[strides.length - 1] = 1;
		
		for (int i = strides.length - 1; i > 0; i--) {
			strides[i-1] *= strides[i];
		}
		
		return strides;
	}

	private static int[] computeOffsets(int[] sizes) {
		int[] offsets = sizes.clone();
		Arrays.fill(offsets, 0);
		return offsets;
	}

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

	public T set(T element, int... pos) {
		T old = data[computeAddress(pos)];
		data[computeAddress(pos)] = element;
		return old;
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

	public PackedArray<T> swap(int dimensionA, int dimensionB) {
		if (dimensionA < 0 || dimensionA >= sizes.length)
			throw new IllegalArgumentException("Illegal dimension: " + dimensionB);

		if (dimensionB < 0 || dimensionB >= sizes.length)
			throw new IllegalArgumentException("Illegal dimension: " + dimensionB);
		
		int[] newSizes = sizes.clone();
		newSizes[dimensionA] = sizes[dimensionB];
		newSizes[dimensionB] = sizes[dimensionA];
		
		int[] newStrides = strides.clone();
		newStrides[dimensionA] = strides[dimensionB];
		newStrides[dimensionB] = strides[dimensionA];
		
		int[] newOffsets = offsets.clone();
		newOffsets[dimensionA] = newOffsets[dimensionB];
		newOffsets[dimensionB] = newOffsets[dimensionA];
		
		return new PackedArray<T>(this.data, newSizes, newStrides, newOffsets);
	}

	public PackedArray<T> transpose() {
		return new PackedArray<T>(this.data, 
				reverse(sizes.clone()), 
				reverse(strides.clone()), 
				reverse(offsets.clone()));
	}

	public int[] length() {
		return sizes.clone();
	}

	public int dimensions() {
		return sizes.length;
	}
	
	private static int[] reverse(int[] is) {
		for (int i = 0; i < is.length/2; i++) {
			is[i] ^= is[is.length - i - 1];
			is[is.length - i - 1] ^= is[i];
			is[i] ^= is[is.length - i - 1];
		}
		
		return is;
	}
}
