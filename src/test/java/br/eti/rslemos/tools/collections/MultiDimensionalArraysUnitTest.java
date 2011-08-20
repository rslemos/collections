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
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MultiDimensionalArraysUnitTest extends TestCase {
	
	public static TestSuite suite() {
		TestSuite suite = new TestSuite(MultiDimensionalArraysUnitTest.class);
		
		suite.addTestSuite(AddressIterator.class);
		suite.addTestSuite(Copy.class);
		
		return suite;
	}
	
	@Ignore public void testIgnore() {}

	public static class AddressIterator extends TestCase {
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
		
		@Test
		public void test_1_$_0() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {1}, new int[] {0}));
			assertThat(addresses.size(), is(equalTo(1)));
			assertThat(addresses, hasItem(new int[] {0}));
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
		
		@Test
		public void test_0_2_$_1_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {0, 2}, new int[] {1, 1}));
			assertThat(addresses.size(), is(equalTo(1)));
			assertThat(addresses.get(0), is(equalTo((new int[] {0, 1}))));
		}
		
		@Test
		public void test_2_0_$_1_1() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {2, 0}, new int[] {1, 1}));
			assertThat(addresses.size(), is(equalTo(1)));
			assertThat(addresses.get(0), is(equalTo((new int[] {1, 0}))));
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
		
		@Test
		public void test_3_$_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {3}, new int[] {2}));
			assertThat(addresses.size(), is(equalTo(1)));
			assertThat(addresses.get(0), is(equalTo((new int[] {2}))));
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
		
		@Test
		public void test_0_3_$_2_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {0, 3}, new int[] {2, 2}));
			assertThat(addresses.size(), is(equalTo(2)));
			assertThat(addresses.get(0), is(equalTo((new int[] {0, 2}))));
			assertThat(addresses.get(1), is(equalTo((new int[] {1, 2}))));
		}
		
		@Test
		public void test_3_0_$_2_2() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {3, 0}, new int[] {2, 2}));
			assertThat(addresses.size(), is(equalTo(2)));
			assertThat(addresses.get(0), is(equalTo((new int[] {2, 0}))));
			assertThat(addresses.get(1), is(equalTo((new int[] {2, 1}))));
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

		@Test
		public void test_2_3_4_$_0_0_0() {
			addresses = asList(MultiDimensionalArrays.allAddresses(new int[] {2, 3, 4}, new int[] {0, 0, 0}));
			assertThat(addresses.size(), is(equalTo(2*3*4)));
			assertThat(addresses.get( 0), is(equalTo((new int[] {1, 2, 3}))));
			assertThat(addresses.get( 1), is(equalTo((new int[] {1, 2, 2}))));
			assertThat(addresses.get( 2), is(equalTo((new int[] {1, 2, 1}))));
			assertThat(addresses.get( 3), is(equalTo((new int[] {1, 2, 0}))));
			assertThat(addresses.get( 4), is(equalTo((new int[] {1, 1, 3}))));
			assertThat(addresses.get( 5), is(equalTo((new int[] {1, 1, 2}))));
			assertThat(addresses.get( 6), is(equalTo((new int[] {1, 1, 1}))));
			assertThat(addresses.get( 7), is(equalTo((new int[] {1, 1, 0}))));
			assertThat(addresses.get( 8), is(equalTo((new int[] {1, 0, 3}))));
			assertThat(addresses.get( 9), is(equalTo((new int[] {1, 0, 2}))));
			assertThat(addresses.get(10), is(equalTo((new int[] {1, 0, 1}))));
			assertThat(addresses.get(11), is(equalTo((new int[] {1, 0, 0}))));
			assertThat(addresses.get(12), is(equalTo((new int[] {0, 2, 3}))));
			assertThat(addresses.get(13), is(equalTo((new int[] {0, 2, 2}))));
			assertThat(addresses.get(14), is(equalTo((new int[] {0, 2, 1}))));
			assertThat(addresses.get(15), is(equalTo((new int[] {0, 2, 0}))));
			assertThat(addresses.get(16), is(equalTo((new int[] {0, 1, 3}))));
			assertThat(addresses.get(17), is(equalTo((new int[] {0, 1, 2}))));
			assertThat(addresses.get(18), is(equalTo((new int[] {0, 1, 1}))));
			assertThat(addresses.get(19), is(equalTo((new int[] {0, 1, 0}))));
			assertThat(addresses.get(20), is(equalTo((new int[] {0, 0, 3}))));
			assertThat(addresses.get(21), is(equalTo((new int[] {0, 0, 2}))));
			assertThat(addresses.get(22), is(equalTo((new int[] {0, 0, 1}))));
			assertThat(addresses.get(23), is(equalTo((new int[] {0, 0, 0}))));
		}
	}
	
	public static class Copy extends TestCase {
		
		@Mock MultiDimensionalArray<Object> source;
		@Mock MultiDimensionalArray<Object> destination;
		
		@Before
		public void setUp() {
			MockitoAnnotations.initMocks(this);
		}
		
		@After
		public void tearDown() {
			verifyNoMoreInteractions(source);
			verifyNoMoreInteractions(destination);
		}
		
		@Test
		public void testZeroCopy() {
			setupLengths(source);
			setupLengths(destination);
			
			MultiDimensionalArrays.copy(source, destination);
			
			verify(source, atLeastOnce()).dimensions();
			verify(destination, atLeastOnce()).dimensions();
		}
	
		@Test
		public void testOneDimensionNoElements() {
			setupLengths(source, 0);
			setupLengths(destination, 0);
			
			MultiDimensionalArrays.copy(source, destination);
			
			verify(source, atLeastOnce()).dimensions();
			verify(destination, atLeastOnce()).dimensions();
	
			verify(source, atLeastOnce()).length();
			verify(destination, atLeastOnce()).length();
		}
		
		@Test
		public void testOneDimensionSingleElement() {
			setupLengths(source, 1);
			setupLengths(destination, 1);
			
			Object data = new Object();
			
			when(source.get(0)).thenReturn(data);
	
			MultiDimensionalArrays.copy(source, destination);
			
			verify(source, atLeastOnce()).dimensions();
			verify(destination, atLeastOnce()).dimensions();
	
			verify(source, atLeastOnce()).length();
			verify(destination, atLeastOnce()).length();
	
			verify(source).get(0);
			verify(destination).set(data, 0);
		}
	
		@Test
		public void testTwoDimensionsSingleElement() {
			setupLengths(source, 1, 1);
			setupLengths(destination, 1, 1);
			
			Object data = new Object();
			
			when(source.get(0, 0)).thenReturn(data);
	
			MultiDimensionalArrays.copy(source, destination);
			
			verify(source, atLeastOnce()).dimensions();
			verify(destination, atLeastOnce()).dimensions();
	
			verify(source, atLeastOnce()).length();
			verify(destination, atLeastOnce()).length();
	
			verify(source).get(0, 0);
			verify(destination).set(data, 0, 0);
		}
		
		@Test
		public void testOneDimensionTwoElements() {
			setupLengths(source, 2);
			setupLengths(destination, 2);
			
			Object data0 = new Object();
			Object data1 = new Object();
			
			when(source.get(0)).thenReturn(data0);
			when(source.get(1)).thenReturn(data1);
	
			MultiDimensionalArrays.copy(source, destination);
			
			verify(source, atLeastOnce()).dimensions();
			verify(destination, atLeastOnce()).dimensions();
	
			verify(source, atLeastOnce()).length();
			verify(destination, atLeastOnce()).length();
	
			verify(source).get(0);
			verify(source).get(1);
			verify(destination).set(data0, 0);
			verify(destination).set(data1, 1);
		}
	
		@Test
		public void testTwoDimensionsFourElements() {
			setupLengths(source, 2, 2);
			setupLengths(destination, 2, 2);
			
			Object data00 = new Object();
			Object data01 = new Object();
			Object data10 = new Object();
			Object data11 = new Object();
			
			when(source.get(0, 0)).thenReturn(data00);
			when(source.get(0, 1)).thenReturn(data01);
			when(source.get(1, 0)).thenReturn(data10);
			when(source.get(1, 1)).thenReturn(data11);
	
			MultiDimensionalArrays.copy(source, destination);
			
			verify(source, atLeastOnce()).dimensions();
			verify(destination, atLeastOnce()).dimensions();
	
			verify(source, atLeastOnce()).length();
			verify(destination, atLeastOnce()).length();
	
			verify(source).get(0, 0);
			verify(source).get(0, 1);
			verify(source).get(1, 0);
			verify(source).get(1, 1);
			verify(destination).set(data00, 0, 0);
			verify(destination).set(data01, 0, 1);
			verify(destination).set(data10, 1, 0);
			verify(destination).set(data11, 1, 1);
		}
		
		@Test
		public void testTwoDimensionsFourElementsWithOffsets() {
			setupLengths(source, 4, 4);
			setupLengths(destination, 4, 4);
			
			Object data22 = new Object();
			Object data23 = new Object();
			Object data32 = new Object();
			Object data33 = new Object();
			
			when(source.get(2, 2)).thenReturn(data22);
			when(source.get(2, 3)).thenReturn(data23);
			when(source.get(3, 2)).thenReturn(data32);
			when(source.get(3, 3)).thenReturn(data33);
	
			MultiDimensionalArrays.copy(source, new int[] {2, 2}, destination, new int[] {2, 2});
			
			verify(source, atLeastOnce()).dimensions();
			verify(destination, atLeastOnce()).dimensions();
	
			verify(source, atLeastOnce()).length();
			verify(destination, atLeastOnce()).length();
	
			verify(source).get(2, 2);
			verify(source).get(2, 3);
			verify(source).get(3, 2);
			verify(source).get(3, 3);
			verify(destination).set(data22, 2, 2);
			verify(destination).set(data23, 2, 3);
			verify(destination).set(data32, 3, 2);
			verify(destination).set(data33, 3, 3);
		}
		
		@Test
		public void testTwoDimensionsFourElementsWithOffsetsAndLengths() {
			setupLengths(source, 4, 4);
			setupLengths(destination, 4, 4);
			
			Object data22 = new Object();
			
			when(source.get(2, 2)).thenReturn(data22);
	
			MultiDimensionalArrays.copy(source, new int[] {2, 2}, destination, new int[] {2, 2}, new int[] {1, 1});
			
			verify(source, atLeastOnce()).dimensions();
			verify(destination, atLeastOnce()).dimensions();
	
			verify(source).get(2, 2);
			verify(destination).set(data22, 2, 2);
		}
		
		@Test
		public void testOneDimensionCopyFromItselfForward() {
			setupLengths(source, 4);
			
			Object data0 = new Object();
			Object data1 = new Object();
			Object data2 = new Object();
			
			when(source.get(0)).thenReturn(data0);
			when(source.get(1)).thenReturn(data1);
			when(source.get(2)).thenReturn(data2);
	
			MultiDimensionalArrays.copy(source, new int[] {0}, source, new int[] {1});
			
			verify(source, atLeastOnce()).dimensions();
			verify(source, atLeastOnce()).length();

			InOrder order = inOrder(source);
			order.verify(source).get(2);
			order.verify(source).set(data2, 3);
			order.verify(source).get(1);
			order.verify(source).set(data1, 2);
			order.verify(source).get(0);
			order.verify(source).set(data0, 1);
		}
		
		@Test
		public void testOneDimensionCopyFromItselfBackward() {
			setupLengths(source, 4);
			
			Object data1 = new Object();
			Object data2 = new Object();
			Object data3 = new Object();
			
			when(source.get(1)).thenReturn(data1);
			when(source.get(2)).thenReturn(data2);
			when(source.get(3)).thenReturn(data3);
	
			MultiDimensionalArrays.copy(source, new int[] {1}, source, new int[] {0});
			
			verify(source, atLeastOnce()).dimensions();
			verify(source, atLeastOnce()).length();

			InOrder order = inOrder(source);
			order.verify(source).get(1);
			order.verify(source).set(data1, 0);
			order.verify(source).get(2);
			order.verify(source).set(data2, 1);
			order.verify(source).get(3);
			order.verify(source).set(data3, 2);
		}
		
		@Test
		public void testTwoDimensionsCopyFromItselfForwardForward() {
			setupLengths(source, 4, 4);
			
			Object data00 = new Object();
			Object data01 = new Object();
			Object data02 = new Object();
			Object data10 = new Object();
			Object data11 = new Object();
			Object data12 = new Object();
			Object data20 = new Object();
			Object data21 = new Object();
			Object data22 = new Object();
			
			when(source.get(0, 0)).thenReturn(data00);
			when(source.get(0, 1)).thenReturn(data01);
			when(source.get(0, 2)).thenReturn(data02);
			when(source.get(1, 0)).thenReturn(data10);
			when(source.get(1, 1)).thenReturn(data11);
			when(source.get(1, 2)).thenReturn(data12);
			when(source.get(2, 0)).thenReturn(data20);
			when(source.get(2, 1)).thenReturn(data21);
			when(source.get(2, 2)).thenReturn(data22);
	
			MultiDimensionalArrays.copy(source, new int[] {0, 0}, source, new int[] {1, 1});
			
			verify(source, atLeastOnce()).dimensions();
			verify(source, atLeastOnce()).length();

			InOrder order = inOrder(source);
			order.verify(source).get(2, 2);
			order.verify(source).set(data22, 3, 3);
			order.verify(source).get(2, 1);
			order.verify(source).set(data21, 3, 2);
			order.verify(source).get(2, 0);
			order.verify(source).set(data20, 3, 1);
			order.verify(source).get(1, 2);
			order.verify(source).set(data12, 2, 3);
			order.verify(source).get(1, 1);
			order.verify(source).set(data11, 2, 2);
			order.verify(source).get(1, 0);
			order.verify(source).set(data10, 2, 1);
			order.verify(source).get(0, 2);
			order.verify(source).set(data02, 1, 3);
			order.verify(source).get(0, 1);
			order.verify(source).set(data01, 1, 2);
			order.verify(source).get(0, 0);
			order.verify(source).set(data00, 1, 1);
		}
		
		@Test
		public void testTwoDimensionsCopyFromItselfBackwardBackward() {
			setupLengths(source, 4, 4);
			
			Object data11 = new Object();
			Object data12 = new Object();
			Object data13 = new Object();
			Object data21 = new Object();
			Object data22 = new Object();
			Object data23 = new Object();
			Object data31 = new Object();
			Object data32 = new Object();
			Object data33 = new Object();
			
			when(source.get(1, 1)).thenReturn(data11);
			when(source.get(1, 2)).thenReturn(data12);
			when(source.get(1, 3)).thenReturn(data13);
			when(source.get(2, 1)).thenReturn(data21);
			when(source.get(2, 2)).thenReturn(data22);
			when(source.get(2, 3)).thenReturn(data23);
			when(source.get(3, 1)).thenReturn(data31);
			when(source.get(3, 2)).thenReturn(data32);
			when(source.get(3, 3)).thenReturn(data33);
	
			MultiDimensionalArrays.copy(source, new int[] {1, 1}, source, new int[] {0, 0});
			
			verify(source, atLeastOnce()).dimensions();
			verify(source, atLeastOnce()).length();

			InOrder order = inOrder(source);
			order.verify(source).get(1, 1);
			order.verify(source).set(data11, 0, 0);
			order.verify(source).get(1, 2);
			order.verify(source).set(data12, 0, 1);
			order.verify(source).get(1, 3);
			order.verify(source).set(data13, 0, 2);
			order.verify(source).get(2, 1);
			order.verify(source).set(data21, 1, 0);
			order.verify(source).get(2, 2);
			order.verify(source).set(data22, 1, 1);
			order.verify(source).get(2, 3);
			order.verify(source).set(data23, 1, 2);
			order.verify(source).get(3, 1);
			order.verify(source).set(data31, 2, 0);
			order.verify(source).get(3, 2);
			order.verify(source).set(data32, 2, 1);
			order.verify(source).get(3, 3);
			order.verify(source).set(data33, 2, 2);
		}
		
		@Test
		public void testTwoDimensionsCopyFromItselfForwardBackward() {
			setupLengths(source, 4, 4);
			
			Object data03 = new Object();
			Object data02 = new Object();
			Object data01 = new Object();
			Object data13 = new Object();
			Object data12 = new Object();
			Object data11 = new Object();
			Object data23 = new Object();
			Object data22 = new Object();
			Object data21 = new Object();
			
			when(source.get(0, 3)).thenReturn(data03);
			when(source.get(0, 2)).thenReturn(data02);
			when(source.get(0, 1)).thenReturn(data01);
			when(source.get(1, 3)).thenReturn(data13);
			when(source.get(1, 2)).thenReturn(data12);
			when(source.get(1, 1)).thenReturn(data11);
			when(source.get(2, 3)).thenReturn(data23);
			when(source.get(2, 2)).thenReturn(data22);
			when(source.get(2, 1)).thenReturn(data21);
	
			MultiDimensionalArrays.copy(source, new int[] {0, 1}, source, new int[] {1, 0});
			
			verify(source, atLeastOnce()).dimensions();
			verify(source, atLeastOnce()).length();

			InOrder order = inOrder(source);
			order.verify(source).get(2, 1);
			order.verify(source).set(data21, 3, 0);
			order.verify(source).get(2, 2);
			order.verify(source).set(data22, 3, 1);
			order.verify(source).get(2, 3);
			order.verify(source).set(data23, 3, 2);
			order.verify(source).get(1, 1);
			order.verify(source).set(data11, 2, 0);
			order.verify(source).get(1, 2);
			order.verify(source).set(data12, 2, 1);
			order.verify(source).get(1, 3);
			order.verify(source).set(data13, 2, 2);
			order.verify(source).get(0, 1);
			order.verify(source).set(data01, 1, 0);
			order.verify(source).get(0, 2);
			order.verify(source).set(data02, 1, 1);
			order.verify(source).get(0, 3);
			order.verify(source).set(data03, 1, 2);
		}
		
		@Test
		public void testTwoDimensionsCopyFromItselfBackwardForward() {
			setupLengths(source, 4, 4);
			
			Object data12 = new Object();
			Object data11 = new Object();
			Object data10 = new Object();
			Object data22 = new Object();
			Object data21 = new Object();
			Object data20 = new Object();
			Object data32 = new Object();
			Object data31 = new Object();
			Object data30 = new Object();
			
			when(source.get(1, 2)).thenReturn(data12);
			when(source.get(1, 1)).thenReturn(data11);
			when(source.get(1, 0)).thenReturn(data10);
			when(source.get(2, 2)).thenReturn(data22);
			when(source.get(2, 1)).thenReturn(data21);
			when(source.get(2, 0)).thenReturn(data20);
			when(source.get(3, 2)).thenReturn(data32);
			when(source.get(3, 1)).thenReturn(data31);
			when(source.get(3, 0)).thenReturn(data30);
	
			MultiDimensionalArrays.copy(source, new int[] {1, 0}, source, new int[] {0, 1});
			
			verify(source, atLeastOnce()).dimensions();
			verify(source, atLeastOnce()).length();

			InOrder order = inOrder(source);
			order.verify(source).get(1, 2);
			order.verify(source).set(data12, 0, 3);
			order.verify(source).get(1, 1);
			order.verify(source).set(data11, 0, 2);
			order.verify(source).get(1, 0);
			order.verify(source).set(data10, 0, 1);
			order.verify(source).get(2, 2);
			order.verify(source).set(data22, 1, 3);
			order.verify(source).get(2, 1);
			order.verify(source).set(data21, 1, 2);
			order.verify(source).get(2, 0);
			order.verify(source).set(data20, 1, 1);
			order.verify(source).get(3, 2);
			order.verify(source).set(data32, 2, 3);
			order.verify(source).get(3, 1);
			order.verify(source).set(data31, 2, 2);
			order.verify(source).get(3, 0);
			order.verify(source).set(data30, 2, 1);
		}
		
		private static <T> void setupLengths(MultiDimensionalArray<T> array, int... lengths) {
			when(array.dimensions()).thenReturn(lengths.length);
			when(array.length()).thenReturn(lengths);
		}
	}

	private static <T> List<T> asList(Iterator<T> it) {
		ArrayList<T> list = new ArrayList<T>();
		
		while(it.hasNext())
			list.add(it.next());
		
		return list;
	}

}
