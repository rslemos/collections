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

public abstract class SimplePackedArrayUnitTest extends MultiDimensionalArrayAbstractUnitTest<String> {
	
	// how about that? almost anything should throw IllegalArgumentException or AIOOB
	public static class ZeroDimension extends SimplePackedArrayUnitTest {
		SimplePackedArray<String> array;
		int[] sizes;
		String[] model;

		{
			sizes = new int[] {};
			array = new SimplePackedArray<String>(sizes);
			model = null;
		}

		@Override
		protected SimplePackedArray<String> createArray() {
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
		
		public static class ConstructorFromItself extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromItself<String> {}
		public static class ConstructorFromKnownGoodImplementation extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromKnownGoodImplementation<String> {}
		public static class ConstructorFromRawModel extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromRawModel<String> {}
	}

	public static class OneDimension extends SimplePackedArrayUnitTest {
		SimplePackedArray<String> array;
		int[] sizes;
		String[] model;

		{
			sizes = new int[] {6};
			
			array = new SimplePackedArray<String>(sizes);
			array.set("[0]", 0);
			array.set("[1]", 1);
			array.set("[2]", 2);
			array.set("[3]", 3);
			array.set("[4]", 4);
			array.set("[5]", 5);
			
			model = new String[] {"[0]", "[1]", "[2]", "[3]", "[4]", "[5]"};
		}

		@Override
		protected SimplePackedArray<String> createArray() {
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
		
		@Test
		public void testElementLayout() {
			SimplePackedArray<Object> oneDimArray = new SimplePackedArray<Object>(5);
			assertThat(oneDimArray.computeAddress(0), is(equalTo(0)));
			assertThat(oneDimArray.computeAddress(1), is(equalTo(1)));
			assertThat(oneDimArray.computeAddress(2), is(equalTo(2)));
			assertThat(oneDimArray.computeAddress(3), is(equalTo(3)));
			assertThat(oneDimArray.computeAddress(4), is(equalTo(4)));
		}
		
		public static class ConstructorFromItself extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromItself<String> {}
		public static class ConstructorFromKnownGoodImplementation extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromKnownGoodImplementation<String> {}
		public static class ConstructorFromRawModel extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromRawModel<String> {}
	}
	
	public static class TwoDimensions extends SimplePackedArrayUnitTest {
		SimplePackedArray<String> array;
		int[] sizes;
		String[][] model;

		{
			sizes = new int[] {3, 4};
			
			array = new SimplePackedArray<String>(sizes);
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
		protected SimplePackedArray<String> createArray() {
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

		@Test
		public void testElementLayout() {
			SimplePackedArray<Object> rowMajor = new SimplePackedArray<Object>(3, 3);
			assertThat(rowMajor.computeAddress(0, 0), is(equalTo(0)));
			assertThat(rowMajor.computeAddress(0, 1), is(equalTo(1)));
			assertThat(rowMajor.computeAddress(0, 2), is(equalTo(2)));
			assertThat(rowMajor.computeAddress(1, 0), is(equalTo(3)));
			assertThat(rowMajor.computeAddress(1, 1), is(equalTo(4)));
			assertThat(rowMajor.computeAddress(1, 2), is(equalTo(5)));
			assertThat(rowMajor.computeAddress(2, 0), is(equalTo(6)));
			assertThat(rowMajor.computeAddress(2, 1), is(equalTo(7)));
			assertThat(rowMajor.computeAddress(2, 2), is(equalTo(8)));
		}
		
		public static class ConstructorFromItself extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromItself<String> {}
		public static class ConstructorFromKnownGoodImplementation extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromKnownGoodImplementation<String> {}
		public static class ConstructorFromRawModel extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromRawModel<String> {}
	}

	public static class ThreeDimensions extends SimplePackedArrayUnitTest {
		SimplePackedArray<String> array;
		int[] sizes;
		String[][][] model;
		
		{
			sizes = new int[] {4, 2, 3};
			
			array = new SimplePackedArray<String>(sizes);
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
		protected SimplePackedArray<String> createArray() {
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
		
		@Test
		public void testElementLayout() {
			SimplePackedArray<Object> threeDimArray = new SimplePackedArray<Object>(2, 2, 2);
			assertThat(threeDimArray.computeAddress(0, 0, 0), is(equalTo(0)));
			assertThat(threeDimArray.computeAddress(0, 0, 1), is(equalTo(1)));
			assertThat(threeDimArray.computeAddress(0, 1, 0), is(equalTo(2)));
			assertThat(threeDimArray.computeAddress(0, 1, 1), is(equalTo(3)));
			assertThat(threeDimArray.computeAddress(1, 0, 0), is(equalTo(4)));
			assertThat(threeDimArray.computeAddress(1, 0, 1), is(equalTo(5)));
			assertThat(threeDimArray.computeAddress(1, 1, 0), is(equalTo(6)));
			assertThat(threeDimArray.computeAddress(1, 1, 1), is(equalTo(7)));
		}
		
		public static class ConstructorFromItself extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromItself<String> {}
		public static class ConstructorFromKnownGoodImplementation extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromKnownGoodImplementation<String> {}
		public static class ConstructorFromRawModel extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromRawModel<String> {}
	}

	public static class FourDimensions extends SimplePackedArrayUnitTest {
		SimplePackedArray<String> array;
		int[] sizes;
		String[][][][] model;
		
		{
			sizes = new int[] {3, 5, 7, 9};
			
			array = new SimplePackedArray<String>(sizes);
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
		protected SimplePackedArray<String> createArray() {
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
		
		public static class ConstructorFromItself extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromItself<String> {}
		public static class ConstructorFromKnownGoodImplementation extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromKnownGoodImplementation<String> {}
		public static class ConstructorFromRawModel extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromRawModel<String> {}
	}

	public static class FiveDimensions extends SimplePackedArrayUnitTest {
		SimplePackedArray<String> array;
		int[] sizes;
		String[][][][][] model;
		
		{
			sizes = new int[] {1, 2, 3, 4, 5};
			array = new SimplePackedArray<String>(sizes);
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
		protected SimplePackedArray<String> createArray() {
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
		
		public static class ConstructorFromItself extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromItself<String> {}
		public static class ConstructorFromKnownGoodImplementation extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromKnownGoodImplementation<String> {}
		public static class ConstructorFromRawModel extends MultiDimensionalArrayAbstractUnitTest.ConstructorFromRawModel<String> {}
	}

	protected String createSample() {
		return "sam" + "ple";
	}
}
