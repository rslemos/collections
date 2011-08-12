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
import java.util.Iterator;

public class PackedArray<T> extends SimplePackedArray<T> {
	private final int[] strides;
	private final int[] offsets;

	public PackedArray(int... sizes) {
		this(sizes, computeStrides(sizes), computeOffsets(sizes));
	}

	public PackedArray(MultiDimensionalArray<T> init) {
		super(init, false);
		
		if (init instanceof PackedArray) {
			PackedArray<T> packed = (PackedArray<T>) init;
			this.strides = packed.strides;
			this.offsets = packed.offsets;
		} else {
			this.strides = computeStrides(sizes);
			this.offsets = computeOffsets(sizes);
			
			Iterator<int[]> addresses = MultiDimensionalArrays.allAddresses(sizes);
			while(addresses.hasNext()) {
				int[] address = addresses.next();
				data[computeAddress(address)] = init.get(address);
			}
		}
	}

	public PackedArray(Object init, int... sizes) {
		this(new JavaArrayMultiDimensionalArray<T>(init, sizes));
	}
	
	private static int[] computeStrides(int[] sizes) {
		int[] strides = sizes.clone();
		
		if (strides.length > 0) {
			System.arraycopy(sizes, 1, strides, 0, strides.length - 1);
			strides[strides.length - 1] = 1;
			
			for (int i = strides.length - 1; i > 0; i--) {
				strides[i-1] *= strides[i];
			}
		}
		
		return strides;
	}

	private static int[] computeOffsets(int[] sizes) {
		int[] offsets = sizes.clone();
		Arrays.fill(offsets, 0);
		return offsets;
	}

	protected PackedArray(int[] sizes, int[] strides, int[] offsets) {
		super(sizes);
		this.strides = strides;
		this.offsets = offsets;
	}
	
	private PackedArray(T[] data, int[] sizes, int[] strides, int[] offsets) {
		super(data, sizes);
		this.strides = strides;
		this.offsets = offsets;
	}

	@Override
	protected int computeAddress(int... pos) {
		int address = 0;
		for (int i = 0; i < pos.length; i++) {
			address += (pos[i] + offsets[i]) * strides[i];
		}
		
		return address;
	}

	@Override
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

	@Override
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

	private static int[] reverse(int[] is) {
		for (int i = 0; i < is.length/2; i++) {
			is[i] ^= is[is.length - i - 1];
			is[is.length - i - 1] ^= is[i];
			is[i] ^= is[is.length - i - 1];
		}
		
		return is;
	}
}
