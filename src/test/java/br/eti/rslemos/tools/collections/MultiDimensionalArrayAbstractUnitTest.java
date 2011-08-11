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

import static br.eti.rslemos.tools.collections.MultiDimensionalArrays.allAddresses;
import static br.eti.rslemos.tools.collections.MultiDimensionalArrays.elementWiseEquals;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public abstract class MultiDimensionalArrayAbstractUnitTest<V> {
	private MultiDimensionalArray<V> subject;
	private int[] sizes;
	private JavaArrayMultiDimensionalArray<V> model;

	@Before
	public void setUp() {
		subject = createArray();
		sizes = createLengths();
		model = new JavaArrayMultiDimensionalArray<V>(createModel(), sizes);
	}
	
	protected abstract MultiDimensionalArray<V> createArray();

	protected abstract int[] createLengths();

	protected abstract Object createModel();

	protected abstract V createSample();
	
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
		Iterator<int[]> addresses = allAddresses(sizes);
		
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			assertThat(subject.get(address), is(equalTo(model.get(address))));
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
		Iterator<int[]> addresses = allAddresses(sizes);
		
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			V modelData = model.get(address);
			
			V newData = createSample();
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
		Iterator<int[]> targetAddresses = allAddresses(sizes);
		
		while(targetAddresses.hasNext()) {
			int[] targetAddress = targetAddresses.next();
			
			V sample = createSample();
			V old = subject.set(sample, targetAddress);
			
			try {
				Iterator<int[]> addresses = allAddresses(sizes);
				while(addresses.hasNext()) {
					int[] address = addresses.next();
					
					// skip targetAddress
					if (!Arrays.equals(address, targetAddress)) {
						assertThat(subject.get(address), is(not(sameInstance(sample))));
					}
				}
			} finally {
				subject.set(old, targetAddress);
			}
		}
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testConstructorFromItself() {
		@SuppressWarnings("rawtypes")
		Class<? extends MultiDimensionalArray> clazz = subject.getClass();
		
		@SuppressWarnings("rawtypes")
		Constructor<? extends MultiDimensionalArray> ctor = null;
		try {
			ctor = clazz.getConstructor(MultiDimensionalArray.class);
		} catch (SecurityException e) {
			fail("Constructor(MultiDimensionalArray) must be accessible");
		} catch (NoSuchMethodException e) {
			fail("Constructor(MultiDimensionalArray) must exist");
		}
		
		MultiDimensionalArray<V> array;
		try {
			array = ctor.newInstance(subject);
		} catch (Exception e) {
			throw (AssertionError)(new AssertionError("Constructor(MultiDimensionalArray) failed").initCause(e));
		}
		
		assertThat(elementWiseEquals(subject, array), is(equalTo(true)));
		subject = array;
		
		testDimensions();
		testLength();
		testLengthsAreSecurelyIsolated();
		testCellIndependency(); // should be the first to change data, since it is very picky about contents
		testGetEachElementAtValidAddress();
		testGetEachElementAlongBoundaryAddress();
		testSetEachElementAtValidAddress();
		testSetEachElementAlongBoundaryAddress();
	}
	
	private Iterator<int[]> allBoundaryAddresses() {
		return new MultiDimensionalArrays.AddressIterator() {
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

	public static <V, E extends RuntimeException> void getAndExpect(Class<E> clazz, MultiDimensionalArray<V> array, int... pos) {
		try {
			array.get(pos);
			fail("Should have thrown " + clazz);
		} catch (RuntimeException expected) {
			if (!clazz.isInstance(expected))
				throw expected;
		}
	}

	public static <V, E extends RuntimeException> void setAndExpect(Class<E> clazz, MultiDimensionalArray<V> array, V v, int... pos) {
		try {
			array.set(v, pos);
			fail("Should have thrown " + clazz);
		} catch (RuntimeException expected) {
			if (!clazz.isInstance(expected))
				throw expected;
		}
	}
}
