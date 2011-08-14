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
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MultiDimensionalArraysUnitTest {

	public static class AddressIterator {
		private static final List<int[]> NO_ADDRESSES = Collections.emptyList();
		
		private List<int[]> addresses;
		
		// with "to" only (from = 0...)
		@Test
		public void test_() {
			addresses = asList(MultiDimensionalArrays.allAddresses());
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test
		public void test_0() {
			addresses = asList(MultiDimensionalArrays.allAddresses(0));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test
		public void test_0_0() {
			addresses = asList(MultiDimensionalArrays.allAddresses(0, 0));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test
		public void test_1_0() {
			addresses = asList(MultiDimensionalArrays.allAddresses(1, 0));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test
		public void test_0_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(0, 1));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test
		public void test_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(1));
			assertThat(addresses.size(), is(equalTo(1)));
			assertThat(addresses, hasItem(new int[] {0}));
		}
		
		@Test
		public void test_1_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(1, 1));
			assertThat(addresses.size(), is(equalTo(1)));
			assertThat(addresses, hasItem(new int[] {0, 0}));
		}
		
		@Test
		public void test_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(2));
			assertThat(addresses.size(), is(equalTo(2)));
			assertThat(addresses.get(0), is(equalTo((new int[] {0}))));
			assertThat(addresses.get(1), is(equalTo((new int[] {1}))));
		}
		
		@Test
		public void test_2_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(2, 2));
			assertThat(addresses.size(), is(equalTo(4)));
			assertThat(addresses.get(0), is(equalTo((new int[] {0, 0}))));
			assertThat(addresses.get(1), is(equalTo((new int[] {0, 1}))));
			assertThat(addresses.get(2), is(equalTo((new int[] {1, 0}))));
			assertThat(addresses.get(3), is(equalTo((new int[] {1, 1}))));
		}
		
		@Test
		public void test_2_3_4() {
			addresses = asList(MultiDimensionalArrays.allAddresses(2, 3, 4));
			assertThat(addresses.size(), is(equalTo(2*3*4)));
			assertThat(addresses.get( 0), is(equalTo((new int[] {0, 0, 0}))));
			assertThat(addresses.get( 1), is(equalTo((new int[] {0, 0, 1}))));
			assertThat(addresses.get( 2), is(equalTo((new int[] {0, 0, 2}))));
			assertThat(addresses.get( 3), is(equalTo((new int[] {0, 0, 3}))));
			assertThat(addresses.get( 4), is(equalTo((new int[] {0, 1, 0}))));
			assertThat(addresses.get( 5), is(equalTo((new int[] {0, 1, 1}))));
			assertThat(addresses.get( 6), is(equalTo((new int[] {0, 1, 2}))));
			assertThat(addresses.get( 7), is(equalTo((new int[] {0, 1, 3}))));
			assertThat(addresses.get( 8), is(equalTo((new int[] {0, 2, 0}))));
			assertThat(addresses.get( 9), is(equalTo((new int[] {0, 2, 1}))));
			assertThat(addresses.get(10), is(equalTo((new int[] {0, 2, 2}))));
			assertThat(addresses.get(11), is(equalTo((new int[] {0, 2, 3}))));
			assertThat(addresses.get(12), is(equalTo((new int[] {1, 0, 0}))));
			assertThat(addresses.get(13), is(equalTo((new int[] {1, 0, 1}))));
			assertThat(addresses.get(14), is(equalTo((new int[] {1, 0, 2}))));
			assertThat(addresses.get(15), is(equalTo((new int[] {1, 0, 3}))));
			assertThat(addresses.get(16), is(equalTo((new int[] {1, 1, 0}))));
			assertThat(addresses.get(17), is(equalTo((new int[] {1, 1, 1}))));
			assertThat(addresses.get(18), is(equalTo((new int[] {1, 1, 2}))));
			assertThat(addresses.get(19), is(equalTo((new int[] {1, 1, 3}))));
			assertThat(addresses.get(20), is(equalTo((new int[] {1, 2, 0}))));
			assertThat(addresses.get(21), is(equalTo((new int[] {1, 2, 1}))));
			assertThat(addresses.get(22), is(equalTo((new int[] {1, 2, 2}))));
			assertThat(addresses.get(23), is(equalTo((new int[] {1, 2, 3}))));
		}

		// with "from" and to
		@Test
		public void test_0_$_0() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {0}, new int[] {0}));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void test_1_$_0() {
			asList(MultiDimensionalArrays.allAddresses(new int[] {1}, new int[] {0}));
		}
		
		@Test
		public void test_1_$_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {1}, new int[] {1}));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test
		public void test_0_0_$_1_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {0, 0}, new int[] {1, 1}));
			assertThat(addresses.size(), is(equalTo(1)));
			assertThat(addresses, hasItem(new int[] {0, 0}));
		}
		
		@Test
		public void test_0_1_$_1_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {0, 1}, new int[] {1, 1}));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test
		public void test_1_0_$_1_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {1, 0}, new int[] {1, 1}));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test
		public void test_1_1_$_1_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {1, 1}, new int[] {1, 1}));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void test_0_2_$_1_1() {
			asList(MultiDimensionalArrays.allAddresses(new int[] {0, 2}, new int[] {1, 1}));
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void test_2_0_$_1_1() {
			asList(MultiDimensionalArrays.allAddresses(new int[] {2, 0}, new int[] {1, 1}));
		}
		
		@Test
		public void test_0_$_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {0}, new int[] {2}));
			assertThat(addresses.size(), is(equalTo(2)));
			assertThat(addresses.get(0), is(equalTo((new int[] {0}))));
			assertThat(addresses.get(1), is(equalTo((new int[] {1}))));
		}
		
		@Test
		public void test_1_$_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {1}, new int[] {2}));
			assertThat(addresses.size(), is(equalTo(1)));
			assertThat(addresses.get(0), is(equalTo((new int[] {1}))));
		}
		
		@Test
		public void test_2_$_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {2}, new int[] {2}));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void test_3_$_2() {
			asList(MultiDimensionalArrays.allAddresses(new int[] {3}, new int[] {2}));
		}
		
		@Test
		public void test_0_0_$_2_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {0, 0}, new int[] {2, 2}));
			assertThat(addresses.size(), is(equalTo(4)));
			assertThat(addresses.get(0), is(equalTo((new int[] {0, 0}))));
			assertThat(addresses.get(1), is(equalTo((new int[] {0, 1}))));
			assertThat(addresses.get(2), is(equalTo((new int[] {1, 0}))));
			assertThat(addresses.get(3), is(equalTo((new int[] {1, 1}))));
		}
		
		@Test
		public void test_0_1_$_2_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {0, 1}, new int[] {2, 2}));
			assertThat(addresses.size(), is(equalTo(2)));
			assertThat(addresses.get(0), is(equalTo((new int[] {0, 1}))));
			assertThat(addresses.get(1), is(equalTo((new int[] {1, 1}))));
		}
		
		@Test
		public void test_1_0_$_2_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {1, 0}, new int[] {2, 2}));
			assertThat(addresses.size(), is(equalTo(2)));
			assertThat(addresses.get(0), is(equalTo((new int[] {1, 0}))));
			assertThat(addresses.get(1), is(equalTo((new int[] {1, 1}))));
		}
		
		@Test
		public void test_0_2_$_2_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {0, 2}, new int[] {2, 2}));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test
		public void test_2_0_$_2_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {2, 0}, new int[] {2, 2}));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void test_0_3_$_2_2() {
			asList(MultiDimensionalArrays.allAddresses(new int[] {0, 3}, new int[] {2, 2}));
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void test_3_0_$_2_2() {
			asList(MultiDimensionalArrays.allAddresses(new int[] {3, 0}, new int[] {2, 2}));
		}
		
		@Test
		public void test_1_1_1_$_2_3_4() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {1, 1, 1}, new int[] {2, 3, 4}));
			assertThat(addresses.size(), is(equalTo(1*2*3)));
			assertThat(addresses.get(0), is(equalTo((new int[] {1, 1, 1}))));
			assertThat(addresses.get(1), is(equalTo((new int[] {1, 1, 2}))));
			assertThat(addresses.get(2), is(equalTo((new int[] {1, 1, 3}))));
			assertThat(addresses.get(3), is(equalTo((new int[] {1, 2, 1}))));
			assertThat(addresses.get(4), is(equalTo((new int[] {1, 2, 2}))));
			assertThat(addresses.get(5), is(equalTo((new int[] {1, 2, 3}))));
		}

		@Test
		public void test_1_2_3_$_2_3_4() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {1, 2, 3}, new int[] {2, 3, 4}));
			assertThat(addresses.size(), is(equalTo(1*1*1)));
			assertThat(addresses.get(0), is(equalTo((new int[] {1, 2, 3}))));
		}

		@Test
		public void test_1_3_2_$_2_3_4() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {1, 3, 2}, new int[] {2, 3, 4}));
			assertThat(addresses, is(equalTo(NO_ADDRESSES)));
		}
	}

	private static <T> List<T> asList(Iterator<T> it) {
		ArrayList<T> list = new ArrayList<T>();
		
		while(it.hasNext())
			list.add(it.next());
		
		return list;
	}

}
