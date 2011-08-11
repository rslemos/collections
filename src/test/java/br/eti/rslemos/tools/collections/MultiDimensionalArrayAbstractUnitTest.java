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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public abstract class MultiDimensionalArrayAbstractUnitTest<V> {
	private MultiDimensionalArray<V> subject;
	private int[] sizes;
	private Object model;

	@Before
	public void setUp() {
		subject = createArray();
		sizes = createLengths();
		model = createModel();
	}
	
	protected abstract MultiDimensionalArray<V> createArray();

	protected abstract int[] createLengths();

	protected abstract Object createModel();

	@Test
	public void testDimensions() {
		assertThat(subject.dimensions(), is(equalTo(sizes.length)));
	}
	
	@Test
	public void testLength() {
		assertThat(subject.length(), is(equalTo(sizes)));
	}

	@Test
	public void testLengthsAreSecurelyIsolated() {
		int[] lengths = subject.length();

		assumeThat(lengths.length, is(greaterThan(0)));
		
		lengths[0]++;
		assertThat(lengths, is(not(equalTo(sizes))));
		assertThat(subject.length(), is(equalTo(sizes)));
	}

	@Test
	public void testGetEachElementAtValidAddress() {
		Iterator<int[]> addresses = allAddresses();
		
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			assertThat(subject.get(address), is(equalTo(getModelData(model, address))));
		}
	}

	@Test
	public void testGetEachElementAlongBoundaryAddress() {
		Iterator<int[]> addresses = allBoundaryAddresses();
		
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			getAndExpect(ArrayIndexOutOfBoundsException.class, subject, address);
		}
	}

	@Test
	public void testSetEachElementAtValidAddress() {
		Iterator<int[]> addresses = allAddresses();
		
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			V modelData = getModelData(model, address);
			
			V newData = getModelData(model, invert(address));
			V oldData = subject.get(address);
			
			assertThat(newData, is(not(equalTo(modelData))));
			assertThat(oldData, is(equalTo(modelData)));
			
			assertThat(subject.set(newData, address), is(sameInstance(oldData)));
			assertThat(subject.get(address), is(sameInstance(newData)));
		}
	}
	
	@Test
	public void testSetEachElementAlongBoundaryAddress() {
		Iterator<int[]> addresses = allBoundaryAddresses();
		
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			setAndExpect(ArrayIndexOutOfBoundsException.class, subject, null, address);
		}
	}

	@Test
	public void testCellIndependency() {
		Iterator<int[]> targetAddresses = allAddresses();
		
		while(targetAddresses.hasNext()) {
			int[] targetAddress = targetAddresses.next();
			
			// swap targetAddress and invert(targetAddress);
			V data0 = subject.get(invert(targetAddress));
			V data1 = subject.set(data0, targetAddress);
			subject.set(data1, invert(targetAddress));
			
			try {
				Iterator<int[]> addresses = allAddresses();
				while(addresses.hasNext()) {
					int[] address = addresses.next();
					
					// skip targetAddress
					if (!Arrays.equals(address, targetAddress) && !Arrays.equals(invert(address), targetAddress)) {
						assertThat(subject.get(address), is(not(sameInstance(data0))));
					}
				}
			} finally {
				subject.set(data1, targetAddress);
				subject.set(data0, invert(targetAddress));
			}
		}
	}
	
	@Test
	public void testSimpleStorage() {
		// try to store and get back <0, 0, ...>
		int[] pos = sizes.clone();
		Arrays.fill(pos, 0);
		
		V data = getModelData(model, pos);
		
		subject.set(data, pos);
		assertThat(subject.get(pos), is(sameInstance(data)));
	}
	
	@SuppressWarnings("unchecked")
	private V getModelData(Object model, int... pos) {
		Object[] last = (Object[]) model;
		int i;
		for (i = 0; i < pos.length - 1; i++) {
			last = (Object[]) last[pos[i]];
		}
		
		return (V) last[pos[i]];
	}

	private int[] invert(int[] address0) {
		int[] address = address0.clone();
		
		for (int i = 0; i < address.length; i++) {
			address[i] = sizes[i] - address[i] - 1;
		}
		
		if (Arrays.equals(address, address0)) {
			// If the dimensions are all odd, then there will exist
			// a central cell whose address and inverted address will
			// be the same. Since we must test *ALL* cells, let's
			// change it for the zero address. Of course, this will
			// still fail if all dimensions are of length 1.
			Arrays.fill(address, 0);
		}
		return address;
	}

	private Iterator<int[]> allAddresses() {
		return new AddressIterator() {
			{
				next = sizes.clone();
				Arrays.fill(next, 0);
			}
			
			protected void computeNextAddress() {
				for(int i = next.length - 1; i >= 0; i--) {
					if (++next[i] < sizes[i]) {
						return;
					} else {
						next[i] = 0;
					}
				}
				
				next = null;
			}
		};
	}

	private Iterator<int[]> allBoundaryAddresses() {
		return new AddressIterator() {
			{
				next = sizes.clone();
				Arrays.fill(next, -1);
			}
			
			protected void computeNextAddress() {
				for(int i = next.length - 1; i >= 0; i--) {
					if (++next[i] <= sizes[i]) {
						if (isValidAddress(next)) {
							computeNextAddress();
						}
						return;
					} else {
						next[i] = -1;
					}
				}
				
				next = null;
			}

			private boolean isValidAddress(int[] address) {
				for (int i = 0; i < address.length; i++) {
					if (address[i] < 0 || address[i] >= sizes[i])
						return false;
				}
				return true;
			}
		};
	}

	private static <V, E extends RuntimeException> void getAndExpect(Class<E> clazz, MultiDimensionalArray<V> array, int... pos) {
		try {
			array.get(pos);
			fail("Should have thrown " + clazz);
		} catch (RuntimeException expected) {
			if (!clazz.isInstance(expected))
				throw expected;
		}
	}

	private static <V, E extends RuntimeException> void setAndExpect(Class<E> clazz, MultiDimensionalArray<V> array, V v, int... pos) {
		try {
			array.set(v, pos);
			fail("Should have thrown " + clazz);
		} catch (RuntimeException expected) {
			if (!clazz.isInstance(expected))
				throw expected;
		}
	}

	private static abstract class AddressIterator implements Iterator<int[]> {
		protected int[] next;

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
