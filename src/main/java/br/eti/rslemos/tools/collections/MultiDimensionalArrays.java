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
import java.util.NoSuchElementException;

public class MultiDimensionalArrays {
	
	private MultiDimensionalArrays() {
		throw new UnsupportedOperationException(MultiDimensionalArrays.class + " not instantiable");
	}
	
	public static <T> boolean equals(MultiDimensionalArray<T> a, MultiDimensionalArray<T> b) {
		return elementWiseEquals(a, b);
	}

	static boolean elementWiseEquals(MultiDimensionalArray<?> a, MultiDimensionalArray<?> b) {
		if (a.dimensions() != b.dimensions())
			return false;
		
		if (!Arrays.equals(a.length(), b.length()))
			return false;
		
		Iterator<int[]> addresses = allAddresses(a.length());
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			Object elementA = a.get(address);
			Object elementB = b.get(address);
			
			if (elementA != null ? !elementA.equals(elementB) : elementB != null)
				return false;
		}
		
		return true;
	}

	public static Iterator<int[]> allAddresses(int... sizes) {
		int[] offsets = new int[sizes.length];
		return allAddresses(offsets, sizes);
	}
	
	public static Iterator<int[]> allAddresses(final int[] from, final int[] to) {
		if (from.length != to.length)
			throw new IllegalArgumentException("Wrong number of dimensions");
		
		
		return new MultiDimensionalArrays.AddressIterator() {
			{
				try {
					if (to.length == 0)
						throw new NoSuchElementException();
					
					for (int i = 0; i < to.length; i++) {
						if (to[i] < from[i])
							throw new IllegalArgumentException();
						
						if (to[i] == from[i])
							throw new NoSuchElementException();
					}
					
					next = from.clone();
				} catch (NoSuchElementException e) {
					next = null;
				}
			}
			
			protected void computeNextAddress() {
				for(int i = next.length - 1; i >= 0; i--) {
					if (++next[i] < to[i]) {
						return;
					} else {
						next[i] = from[i];
					}
				}
				
				next = null;
			}
		};
	}
	
	static abstract class AddressIterator implements Iterator<int[]> {
		public int[] next;
	
		public boolean hasNext() {
			return next != null;
		}
	
		public int[] next() {
			if (!hasNext())
				throw new NoSuchElementException();
			
			try {
				return next.clone();
			} finally {
				computeNextAddress();
			}
		}
	
		protected abstract void computeNextAddress();
	
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

}
