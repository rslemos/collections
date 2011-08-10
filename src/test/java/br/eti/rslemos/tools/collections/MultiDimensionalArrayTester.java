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
import static org.junit.Assume.assumeThat;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Ignore;

@Ignore // mixing junit 3 and junit 4; cool
public class MultiDimensionalArrayTester<V> extends TestCase {
	private MultiDimensionalArray<V> subject;
	private int[] sizes;
	private Object[] model;
	
	public MultiDimensionalArrayTester<V> init(MultiDimensionalArray<V> subject, int[] sizes, Object[] modelData) {
		this.subject = subject;
		this.sizes = sizes;
		this.model = modelData;
		
		return this;
	}
	
	public void testDimensions() {
		assertThat(subject.dimensions(), is(equalTo(sizes.length)));
	}
	
	public void testLength() {
		assertThat(subject.length(), is(equalTo(sizes)));
	}

	public void testLengthsAreSecurelyIsolated() {
		int[] lengths = subject.length();

		assumeThat(lengths.length, is(greaterThan(0)));
		
		lengths[0]++;
		assertThat(lengths, is(not(equalTo(sizes))));
		assertThat(subject.length(), is(equalTo(sizes)));
	}

	public void testGetEachElementAtValidAddress() {
		Iterator<int[]> addresses = allAddresses();
		
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			assertThat(subject.get(address), is(equalTo(getModelData(model, address))));
		}
	}

	public void testGetEachElementAlongBoundaryAddress() {
		Iterator<int[]> addresses = allBoundaryAddresses();
		
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			getAndExpect(ArrayIndexOutOfBoundsException.class, subject, address);
		}
	}

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
	
	public void testSetEachElementAlongBoundaryAddress() {
		Iterator<int[]> addresses = allBoundaryAddresses();
		
		while(addresses.hasNext()) {
			int[] address = addresses.next();
			setAndExpect(ArrayIndexOutOfBoundsException.class, subject, null, address);
		}
	}

	public void testSimpleStorage() {
		// try to store and get back <0, 0, ...>
		int[] pos = sizes.clone();
		Arrays.fill(pos, 0);
		
		V data = getModelData(model, pos);
		
		subject.set(data, pos);
		assertThat(subject.get(pos), is(sameInstance(data)));
	}
	
	@SuppressWarnings("unchecked")
	private V getModelData(Object[] model, int... pos) {
		Object[] last = model;
		for (int i = 0; i < pos.length - 1; i++) {
			last = (Object[]) model[pos[i]];
		}
		
		return (V) last[pos[pos.length - 1]];
	}

	private int[] invert(int[] address) {
		address = address.clone();
		
		for (int i = 0; i < address.length; i++) {
			address[i] = sizes[i] - address[i] - 1;
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

	public static <V> TestSuite createTestSuite(MultiDimensionalArray<V> subject, int[] sizes, Object[] model) {
		TestSuite suite = new TestSuite(MultiDimensionalArrayTester.class);
		Enumeration<Test> tests = suite.tests();
		while(tests.hasMoreElements()) {
			Test test = tests.nextElement();
			
			@SuppressWarnings("unchecked")
			MultiDimensionalArrayTester<V> specificTest = (MultiDimensionalArrayTester<V>) test;
			specificTest.init(subject, sizes, model);
		}
		
		return suite;
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
				return next;
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
