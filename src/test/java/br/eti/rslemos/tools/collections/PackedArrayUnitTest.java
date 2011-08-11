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
import static org.junit.Assert.assertThat;

import org.junit.Test;

public abstract class PackedArrayUnitTest extends MultiDimensionalArrayAbstractUnitTest<String> {
	
	public static class OneDimension extends PackedArrayUnitTest {
		PackedArray<String> array;
		int[] sizes;
		String[] model;

		{
			sizes = new int[] {6};
			
			array = new PackedArray<String>(sizes);
			array.set("[0]", 0);
			array.set("[1]", 1);
			array.set("[2]", 2);
			array.set("[3]", 3);
			array.set("[4]", 4);
			array.set("[5]", 5);
			
			model = new String[] {"[0]", "[1]", "[2]", "[3]", "[4]", "[5]"};
		}

		@Override
		protected PackedArray<String> createArray() {
			return array;
		}

		@Override
		protected int[] createLengths() {
			return sizes;
		}

		@Override
		protected String[] createModel() {
			return model;
		}
	}
	
	public static class TwoDimensions extends PackedArrayUnitTest {
		PackedArray<String> array;
		int[] sizes;
		String[][] model;

		{
			sizes = new int[] {3, 4};
			
			array = new PackedArray<String>(sizes);
			array.set("[0, 0]", 0, 0);
			array.set("[0, 1]", 0, 1);
			array.set("[0, 2]", 0, 2);
			array.set("[0, 3]", 0, 3);
			array.set("[1, 0]", 1, 0);
			array.set("[1, 1]", 1, 1);
			array.set("[1, 2]", 1, 2);
			array.set("[1, 3]", 1, 3);
			array.set("[2, 0]", 2, 0);
			array.set("[2, 1]", 2, 1);
			array.set("[2, 2]", 2, 2);
			array.set("[2, 3]", 2, 3);
			
			model = new String[][] {
					{ "[0, 0]", "[0, 1]", "[0, 2]", "[0, 3]" },
					{ "[1, 0]", "[1, 1]", "[1, 2]", "[1, 3]" },
					{ "[2, 0]", "[2, 1]", "[2, 2]", "[2, 3]" },
				};
		}

		@Override
		protected PackedArray<String> createArray() {
			return array;
		}

		@Override
		protected int[] createLengths() {
			return sizes;
		}

		@Override
		protected String[][] createModel() {
			return model;
		}

	}

	public static class ThreeDimensions extends PackedArrayUnitTest {
		PackedArray<String> array;
		int[] sizes;
		String[][][] model;
		
		{
			sizes = new int[] {4, 2, 3};
			
			array = new PackedArray<String>(sizes);
			model = new String[4][2][3];
			
			for(int i = 0; i < sizes[0]; i++) {
				for(int j = 0; j < sizes[1]; j++) {
					for(int k = 0; k < sizes[2]; k++) {
						String data = "[" + i + ", " + j + ", " + k + "]"; 
						
						model[i][j][k] = data;
						array.set(data, i, j, k);
					}
				}
			}
		}

		@Override
		protected PackedArray<String> createArray() {
			return array;
		}

		@Override
		protected int[] createLengths() {
			return sizes;
		}

		@Override
		protected String[][][] createModel() {
			return model;
		}
	}

	public static class FourDimensions extends PackedArrayUnitTest {
		PackedArray<String> array;
		int[] sizes;
		String[][][][] model;
		
		{
			sizes = new int[] {3, 5, 7, 9};
			
			array = new PackedArray<String>(sizes);
			model = new String[3][5][7][9];
			
			for(int i = 0; i < sizes[0]; i++) {
				for(int j = 0; j < sizes[1]; j++) {
					for(int k = 0; k < sizes[2]; k++) {
						for(int l = 0; l < sizes[3]; l++) {
							String data = "[" + i + ", " + j + ", " + k + ", " + l + "]"; 
							
							model[i][j][k][l] = data;
							array.set(data, i, j, k, l);
						}
					}
				}
			}
		}

		@Override
		protected PackedArray<String> createArray() {
			return array;
		}

		@Override
		protected int[] createLengths() {
			return sizes;
		}

		@Override
		protected String[][][][] createModel() {
			return model;
		}
	}

	public static class FiveDimensions extends PackedArrayUnitTest {
		PackedArray<String> array;
		int[] sizes;
		String[][][][][] model;
		
		{
			sizes = new int[] {1, 2, 3, 4, 5};
			array = new PackedArray<String>(sizes);
			model = new String[1][2][3][4][5];
			
			for(int i = 0; i < sizes[0]; i++) {
				for(int j = 0; j < sizes[1]; j++) {
					for(int k = 0; k < sizes[2]; k++) {
						for(int l = 0; l < sizes[3]; l++) {
							for(int m = 0; m < sizes[4]; m++) {
								String data = "[" + i + ", " + j + ", " + k + ", " + l + ", " + m + "]"; 
								
								model[i][j][k][l][m] = data;
								array.set(data, i, j, k, l, m);
							}
						}
					}
				}
			}
		}

		@Override
		protected PackedArray<String> createArray() {
			return array;
		}

		@Override
		protected int[] createLengths() {
			return sizes;
		}

		@Override
		protected String[][][][][] createModel() {
			return model;
		}
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
		int[] sizes = { 6 };
		MultiDimensionalArray<String> array = new PackedArray<String>(sizes);
		
		array.set("[0]", 0);
		array.set("[1]", 1);
		array.set("[2]", 2);
		array.set("[3]", 3);
		array.set("[4]", 4);
		array.set("[5]", 5);
		
		// views
		MultiDimensionalArray<String> slice = array.slice(0, 2, 5);
		assertThat(slice.length(), is(equalTo(new int[] {3})));
		assertThat(slice.get(0), is(equalTo("[2]")));
		assertThat(slice.get(1), is(equalTo("[3]")));
		assertThat(slice.get(2), is(equalTo("[4]")));
	}
	
	@Test
	public void testTwoDimensionalArray() {
		int[] sizes = { 3, 3 };
		MultiDimensionalArray<String> array = new PackedArray<String>(sizes);

		array.set("[0, 0]", 0, 0);
		array.set("[0, 1]", 0, 1);
		array.set("[0, 2]", 0, 2);
		array.set("[1, 0]", 1, 0);
		array.set("[1, 1]", 1, 1);
		array.set("[1, 2]", 1, 2);
		array.set("[2, 0]", 2, 0);
		array.set("[2, 1]", 2, 1);
		array.set("[2, 2]", 2, 2);
		
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
	}

	@Test
	public void testThreeDimensionalArray() {
		int[] sizes = { 2, 2, 2 };
		MultiDimensionalArray<String> array = new PackedArray<String>(sizes);

		// transposition
		MultiDimensionalArray<String> transposition = array.transpose();
		MultiDimensionalArray<String> swap02 = array.swap(0, 2);
		assertThatTheyAreEquals(transposition, swap02);
	}

	private static void assertThatTheyAreEquals(MultiDimensionalArray<String> a, MultiDimensionalArray<String> b) {
		assertThat(a.length(), is(equalTo(b.length())));
	}
}
