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

import static br.eti.rslemos.tools.collections.MultiDimensionalArrayAbstractUnitTest.*;

import org.junit.Test;

public class JavaArrayMultiDimensionalArrayUnitTest {
	@Test
	public void testOneDimensionZeroLengthArray() {
		String[] javaArray = new String[0];
		MultiDimensionalArray<String> array = new JavaArrayMultiDimensionalArray<String>(javaArray, new int[] {0});
		
		assertThat(array.dimensions(), is(equalTo(1)));
		assertThat(array.length(), is(equalTo(new int[] {0})));
		getAndExpect(ArrayIndexOutOfBoundsException.class, array, -1);
		getAndExpect(ArrayIndexOutOfBoundsException.class, array,  0);
		setAndExpect(ArrayIndexOutOfBoundsException.class, array, "", -1);
		setAndExpect(ArrayIndexOutOfBoundsException.class, array, "",  0);

		getAndExpect(IllegalArgumentException.class, array);
		getAndExpect(IllegalArgumentException.class, array,  0, 0);
		setAndExpect(IllegalArgumentException.class, array, "");
		setAndExpect(IllegalArgumentException.class, array, "", 0, 0);
	}
	
}
