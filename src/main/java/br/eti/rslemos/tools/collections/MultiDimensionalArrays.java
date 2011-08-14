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
	
	public static <T> void copy(MultiDimensionalArray<T> src, MultiDimensionalArray<T> dst) {
		int[] srcPos = new int[src.dimensions()];
		int[] dstPos = new int[dst.dimensions()];
		
		copy(src, srcPos, dst, dstPos);
	}
	
	public static <T> void copy(MultiDimensionalArray<T> src, int[] srcPos, MultiDimensionalArray<T> dst, int[] dstPos) {
		int[] length = new int[min(src.dimensions(), srcPos.length, dst.dimensions(), dstPos.length)];
		
		for (int i = 0; i < length.length; i++) {
			length[i] = min(src.length()[i] - srcPos[i], dst.length()[i] - dstPos[i]);
		}
		
		copy(src, srcPos, dst, dstPos, length);
	}
	
	public static <T> void copy(MultiDimensionalArray<T> src, int[] srcFrom, MultiDimensionalArray<T> dst, int[] dstFrom, int[] lengths) {
		if (src.dimensions() != dst.dimensions())
			throw new IllegalArgumentException("Wrong number of dimensions (arrays)");
		
		if (src.dimensions() != srcFrom.length)
			throw new IllegalArgumentException("Wrong number of dimensions (source offset)");
			
		if (dst.dimensions() != dstFrom.length)
			throw new IllegalArgumentException("Wrong number of dimensions (destination offset)");
		
		if (srcFrom.length != lengths.length)
			throw new IllegalArgumentException("Wrong number of dimensions (lengths)");
		
		int[] srcTo = new int[srcFrom.length];
		int[] dstTo = new int[dstFrom.length];
		
		for (int i = 0; i < lengths.length; i++) {
			srcTo[i] = srcFrom[i] + lengths[i];
			dstTo[i] = dstFrom[i] + lengths[i];
		}
		
		// most general copy

		if (src == dst) {
			for (int i = 0; i < lengths.length; i++) {
				if (srcFrom[i] < dstFrom[i]) {
					int tmp;
					
					tmp = srcTo[i];
					srcTo[i] = srcFrom[i];
					srcFrom[i] = tmp;
					
					tmp = dstTo[i];
					dstTo[i] = dstFrom[i];
					dstFrom[i] = tmp;
				}
			}
		}
		
		Iterator<int[]> srcAddresses = allAddresses(srcFrom, srcTo);
		Iterator<int[]> dstAddresses = allAddresses(dstFrom, dstTo);
		
		while (srcAddresses.hasNext()) {
			int[] srcAddress = srcAddresses.next();
			int[] dstAddress = dstAddresses.next();
			
			dst.set(src.get(srcAddress), dstAddress);
		}
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
	
	public static Iterator<int[]> allAddresses(int[] from0, int[] to0) {
		
		if (from0.length != to0.length)
			throw new IllegalArgumentException("Wrong number of dimensions");
		
		final int[] from = from0.clone();
		final int[] to = to0.clone();
		
		return new MultiDimensionalArrays.AddressIterator() {
			private int[] incr;
			
			{
				incr = new int[from.length];
				
				try {
					if (to.length == 0)
						throw new NoSuchElementException();
					
					for (int i = 0; i < to.length; i++) {
						if (to[i] == from[i])
							throw new NoSuchElementException();
						
						if (to[i] < from[i]) {
							incr[i] = -1;
							from[i]--;
						} else {
							incr[i] = +1;
							to[i]--;
						}
						
					}
					
					next = from.clone();
				} catch (NoSuchElementException e) {
					next = null;
				}
			}
			
			protected void computeNextAddress() {
				for(int i = next.length - 1; i >= 0; i--) {
					next[i] += incr[i];
					
					if (next[i]*incr[i] <= to[i]*incr[i]) {
						return;
					} else {
						next[i] = from[i];
					}
				}
				
				next = null;
			}
		};
	}

	private static int min(int... n) {
		int result = n[0];
		
		for (int i = 1; i < n.length; i++) {
			if (n[i] < result)
				result = n[i];
		}
		
		return result;
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
