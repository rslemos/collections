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

import java.util.Iterator;

public class SimplePackedArray<T> extends AbstractMultiDimensionalArray<T> {
	final T[] data;

	protected SimplePackedArray(T[] data, int... sizes) {
		super(sizes);
		this.data = data;
	}
	
	@SuppressWarnings("unchecked")
	public SimplePackedArray(int... sizes) {
		this((T[]) new Object[computeSize(sizes)], sizes);
	}
	
	public SimplePackedArray(MultiDimensionalArray<T> init) {
		this(init, true);
	}
	
	@SuppressWarnings("unchecked")
	protected SimplePackedArray(MultiDimensionalArray<T> init, boolean computeAddress) {
		super(init instanceof SimplePackedArray ? ((SimplePackedArray<T>) init).sizes : init.length());
		
		if (init instanceof SimplePackedArray) {
			SimplePackedArray<T> packed = (SimplePackedArray<T>) init;
			this.data = packed.data.clone();
		} else {
			this.data = (T[]) new Object[computeSize(sizes)];
			
			if (computeAddress) {
				Iterator<int[]> addresses = MultiDimensionalArrays.allAddresses(sizes);
				while(addresses.hasNext()) {
					int[] address = addresses.next();
					data[computeAddress(address)] = init.get(address);
				}
			}
		}
	}

	public SimplePackedArray(Object init, int... sizes) {
		this(new JavaArrayMultiDimensionalArray<T>(init, sizes));
	}

	protected static int computeSize(int... sizes) {
		if (sizes.length > 0) {
			int result = 1;
			
			for (int size : sizes) {
				result *= size;
			}
			
			return result;
		} else
			return 0;
	}

	// storage methods

	public T get(int... pos) {
		checkBoundaries(pos);
		return data[computeAddress(pos)];
	}

	public T set(T element, int... pos) {
		checkBoundaries(pos);
		T old = data[computeAddress(pos)];
		data[computeAddress(pos)] = element;
		return old;
	}

	// view methods

	public SimplePackedArray<T> slice(int dimension, int from, int to) {
		throw new UnsupportedOperationException();
	}

	public SimplePackedArray<T> swap(int dimensionA, int dimensionB) {
		throw new UnsupportedOperationException();
	}

	public SimplePackedArray<T> transpose() {
		throw new UnsupportedOperationException();
	}

	// auxiliary methods

	protected int computeAddress(int... pos) {
		int address = 0;
		int stride = 1;
		
		for (int i = pos.length; i > 0; i--) {
			address += pos[i-1] * stride;
			stride *= sizes[i-1];
		}
		
		return address;
	}
}
