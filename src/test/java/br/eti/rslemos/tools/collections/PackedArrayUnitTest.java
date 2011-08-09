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

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PackedArrayUnitTest {

	protected PackedArray<String> createStringArray(int... sizes) {
		return new PackedArray<String>(sizes);
	}
	
	@Test
	public void testElementLayout() {
		PackedArray<Object> oneDimArray = new PackedArray<Object>(5);
		assertThat(oneDimArray.computeAddress(0), is(equalTo(0)));
		assertThat(oneDimArray.computeAddress(1), is(equalTo(1)));
		assertThat(oneDimArray.computeAddress(2), is(equalTo(2)));
		assertThat(oneDimArray.computeAddress(3), is(equalTo(3)));
		assertThat(oneDimArray.computeAddress(4), is(equalTo(4)));

		PackedArray<Object> twoDimArrayBE = new PackedArray<Object>(3, 3);
		assertThat(twoDimArrayBE.computeAddress(0, 0), is(equalTo(0)));
		assertThat(twoDimArrayBE.computeAddress(0, 1), is(equalTo(1)));
		assertThat(twoDimArrayBE.computeAddress(0, 2), is(equalTo(2)));
		assertThat(twoDimArrayBE.computeAddress(1, 0), is(equalTo(3)));
		assertThat(twoDimArrayBE.computeAddress(1, 1), is(equalTo(4)));
		assertThat(twoDimArrayBE.computeAddress(1, 2), is(equalTo(5)));
		assertThat(twoDimArrayBE.computeAddress(2, 0), is(equalTo(6)));
		assertThat(twoDimArrayBE.computeAddress(2, 1), is(equalTo(7)));
		assertThat(twoDimArrayBE.computeAddress(2, 2), is(equalTo(8)));
		
		// little-endian
		PackedArray<Object> twoDimArrayLE = twoDimArrayBE.transpose();
		assertThat(twoDimArrayLE.computeAddress(0, 0), is(equalTo(0)));
		assertThat(twoDimArrayLE.computeAddress(1, 0), is(equalTo(1)));
		assertThat(twoDimArrayLE.computeAddress(2, 0), is(equalTo(2)));
		assertThat(twoDimArrayLE.computeAddress(0, 1), is(equalTo(3)));
		assertThat(twoDimArrayLE.computeAddress(1, 1), is(equalTo(4)));
		assertThat(twoDimArrayLE.computeAddress(2, 1), is(equalTo(5)));
		assertThat(twoDimArrayLE.computeAddress(0, 2), is(equalTo(6)));
		assertThat(twoDimArrayLE.computeAddress(1, 2), is(equalTo(7)));
		assertThat(twoDimArrayLE.computeAddress(2, 2), is(equalTo(8)));
		
		PackedArray<Object> threeDimArray = new PackedArray<Object>(2, 2, 2);
		assertThat(threeDimArray.computeAddress(0, 0, 0), is(equalTo(0)));
		assertThat(threeDimArray.computeAddress(0, 0, 1), is(equalTo(1)));
		assertThat(threeDimArray.computeAddress(0, 1, 0), is(equalTo(2)));
		assertThat(threeDimArray.computeAddress(0, 1, 1), is(equalTo(3)));
		assertThat(threeDimArray.computeAddress(1, 0, 0), is(equalTo(4)));
		assertThat(threeDimArray.computeAddress(1, 0, 1), is(equalTo(5)));
		assertThat(threeDimArray.computeAddress(1, 1, 0), is(equalTo(6)));
		assertThat(threeDimArray.computeAddress(1, 1, 1), is(equalTo(7)));
	}

	@Test
	public void testOneDimensionalArray() {
		MultiDimensionalArray<String> array = createStringArray(6);
		
		// info
		assertThat(array.dimensions(), is(equalTo(1)));
		assertThat(array.length(), is(equalTo(new int[] {6})));
		
		// storage
		array.set("[0]", 0);
		array.set("[1]", 1);
		array.set("[2]", 2);
		array.set("[3]", 3);
		array.set("[4]", 4);
		array.set("[5]", 5);
		
		assertThat(array.get(0), is(equalTo("[0]")));
		assertThat(array.get(1), is(equalTo("[1]")));
		assertThat(array.get(2), is(equalTo("[2]")));
		assertThat(array.get(3), is(equalTo("[3]")));
		assertThat(array.get(4), is(equalTo("[4]")));
		assertThat(array.get(5), is(equalTo("[5]")));
		
		// views
		MultiDimensionalArray<String> slice = array.slice(0, 2, 5);
		assertThat(slice.length(), is(equalTo(new int[] {3})));
		assertThat(slice.get(0), is(equalTo("[2]")));
		assertThat(slice.get(1), is(equalTo("[3]")));
		assertThat(slice.get(2), is(equalTo("[4]")));
		
		// boundary check
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array, -1);
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array,  6);
		
		accessAndExpectException(IllegalArgumentException.class, array);
		accessAndExpectException(IllegalArgumentException.class, array, 0, 0);
		accessAndExpectException(IllegalArgumentException.class, array, 0, 0, 0);
	}
	
	@Test
	public void testTwoDimensionalArray() {
		MultiDimensionalArray<String> array = createStringArray(3, 3);

		// info
		assertThat(array.dimensions(), is(equalTo(2)));
		assertThat(array.length(), is(equalTo(new int[] {3, 3})));
		
		// storage
		array.set("[0, 0]", 0, 0);
		array.set("[0, 1]", 0, 1);
		array.set("[0, 2]", 0, 2);
		array.set("[1, 0]", 1, 0);
		array.set("[1, 1]", 1, 1);
		array.set("[1, 2]", 1, 2);
		array.set("[2, 0]", 2, 0);
		array.set("[2, 1]", 2, 1);
		array.set("[2, 2]", 2, 2);
		
		assertThat(array.get(0, 0), is(equalTo("[0, 0]")));
		assertThat(array.get(0, 1), is(equalTo("[0, 1]")));
		assertThat(array.get(0, 2), is(equalTo("[0, 2]")));
		assertThat(array.get(1, 0), is(equalTo("[1, 0]")));
		assertThat(array.get(1, 1), is(equalTo("[1, 1]")));
		assertThat(array.get(1, 2), is(equalTo("[1, 2]")));
		assertThat(array.get(2, 0), is(equalTo("[2, 0]")));
		assertThat(array.get(2, 1), is(equalTo("[2, 1]")));
		assertThat(array.get(2, 2), is(equalTo("[2, 2]")));
		
		// views
		// 0th
		MultiDimensionalArray<String> slice0 = array.slice(0, 1, 3);
		assertThat(slice0.length(), is(equalTo(new int[] {2, 3})));
		assertThat(slice0.get(0, 0), is(equalTo("[1, 0]")));
		assertThat(slice0.get(0, 1), is(equalTo("[1, 1]")));
		assertThat(slice0.get(0, 2), is(equalTo("[1, 2]")));
		assertThat(slice0.get(1, 0), is(equalTo("[2, 0]")));
		assertThat(slice0.get(1, 1), is(equalTo("[2, 1]")));
		assertThat(slice0.get(1, 2), is(equalTo("[2, 2]")));

		// 1st
		MultiDimensionalArray<String> slice1 = array.slice(1, 1, 2);
		assertThat(slice1.length(), is(equalTo(new int[] {3, 1})));
		assertThat(slice1.get(0, 0), is(equalTo("[0, 1]")));
		assertThat(slice1.get(1, 0), is(equalTo("[1, 1]")));
		assertThat(slice1.get(2, 0), is(equalTo("[2, 1]")));
		
		// compose 0th + 1st
		MultiDimensionalArray<String> slice01 = array.slice(0, 1, 3).slice(1, 1, 2);
		assertThat(slice01.length(), is(equalTo(new int[] {2, 1})));
		assertThat(slice01.get(0, 0), is(equalTo("[1, 1]")));
		assertThat(slice01.get(1, 0), is(equalTo("[2, 1]")));
		
		// compose 1st + 0th
		MultiDimensionalArray<String> slice10 = array.slice(1, 0, 1).slice(0, 1, 3);
		assertThat(slice10.length(), is(equalTo(new int[] {2, 1})));
		assertThat(slice10.get(0, 0), is(equalTo("[1, 0]")));
		assertThat(slice10.get(1, 0), is(equalTo("[2, 0]")));

		MultiDimensionalArray<String> swap01 = array.swap(0, 1);
		assertThat(swap01.get(0, 0), is(equalTo("[0, 0]")));
		assertThat(swap01.get(0, 1), is(equalTo("[1, 0]")));
		assertThat(swap01.get(0, 2), is(equalTo("[2, 0]")));
		assertThat(swap01.get(1, 0), is(equalTo("[0, 1]")));
		assertThat(swap01.get(1, 1), is(equalTo("[1, 1]")));
		assertThat(swap01.get(1, 2), is(equalTo("[2, 1]")));
		assertThat(swap01.get(2, 0), is(equalTo("[0, 2]")));
		assertThat(swap01.get(2, 1), is(equalTo("[1, 2]")));
		assertThat(swap01.get(2, 2), is(equalTo("[2, 2]")));
		
		// boundary check
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array,  0, -1);
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array, -1,  0);
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array,  0,  3);
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array,  3,  0);
		
		accessAndExpectException(IllegalArgumentException.class, array);
		accessAndExpectException(IllegalArgumentException.class, array, 0);
		accessAndExpectException(IllegalArgumentException.class, array, 0, 0, 0);
	}

	@Test
	public void testThreeDimensionalArray() {
		MultiDimensionalArray<String> array = createStringArray(2, 2, 2);

		// info
		assertThat(array.dimensions(), is(equalTo(3)));
		assertThat(array.length(), is(equalTo(new int[] {2, 2, 2})));

		// storage
		array.set("[0, 0, 0]", 0, 0, 0);
		array.set("[0, 0, 1]", 0, 0, 1);
		array.set("[0, 1, 0]", 0, 1, 0);
		array.set("[0, 1, 1]", 0, 1, 1);
		array.set("[1, 0, 0]", 1, 0, 0);
		array.set("[1, 0, 1]", 1, 0, 1);
		array.set("[1, 1, 0]", 1, 1, 0);
		array.set("[1, 1, 1]", 1, 1, 1);
		
		assertThat(array.get(0, 0, 0), is(equalTo("[0, 0, 0]")));
		assertThat(array.get(0, 0, 1), is(equalTo("[0, 0, 1]")));
		assertThat(array.get(0, 1, 0), is(equalTo("[0, 1, 0]")));
		assertThat(array.get(0, 1, 1), is(equalTo("[0, 1, 1]")));
		assertThat(array.get(1, 0, 0), is(equalTo("[1, 0, 0]")));
		assertThat(array.get(1, 0, 1), is(equalTo("[1, 0, 1]")));
		assertThat(array.get(1, 1, 0), is(equalTo("[1, 1, 0]")));
		assertThat(array.get(1, 1, 1), is(equalTo("[1, 1, 1]")));
		
		// transposition
		MultiDimensionalArray<String> transposition = array.transpose();
		MultiDimensionalArray<String> swap02 = array.swap(0, 2);
		assertThatTheyAreEquals(transposition, swap02);
		
		// boundary check
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array,  0,  0, -1);
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array,  0, -1,  0);
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array, -1,  0,  0);
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array,  0,  0,  3);
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array,  0,  3,  0);
		accessAndExpectException(ArrayIndexOutOfBoundsException.class, array,  3,  0,  0);
		
		accessAndExpectException(IllegalArgumentException.class, array);
		accessAndExpectException(IllegalArgumentException.class, array, 0);
		accessAndExpectException(IllegalArgumentException.class, array, 0, 0);
	}

	private static void assertThatTheyAreEquals(MultiDimensionalArray<String> a, MultiDimensionalArray<String> b) {
		assertThat(a.length(), is(equalTo(b.length())));
	}

	private static <E extends RuntimeException> void accessAndExpectException(Class<E> clazz, MultiDimensionalArray<String> array, int... pos) {
		getAndExpectException(clazz, array, pos);
		setAndExpectException(clazz, array, pos);
	}

	private static <E extends RuntimeException> void setAndExpectException(Class<E> clazz, MultiDimensionalArray<String> array, int... pos) {
		try {
			array.set(null, pos);
			fail("Should have thrown " + clazz);
		} catch (RuntimeException expected) {
			if (!clazz.isInstance(expected))
				throw expected;
		}
	}

	private static <E extends RuntimeException> void getAndExpectException(Class<E> clazz, MultiDimensionalArray<String> array, int... pos) {
		try {
			array.get(pos);
			fail("Should have thrown " + clazz);
		} catch (RuntimeException expected) {
			if (!clazz.isInstance(expected))
				throw expected;
		}
	}
}
