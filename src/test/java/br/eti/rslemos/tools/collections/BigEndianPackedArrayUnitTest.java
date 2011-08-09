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

public class BigEndianPackedArrayUnitTest extends PackedArrayUnitTest {
	
	@Override
	protected PackedArray<String> createStringArray(int... sizes) {
		return new BigEndianPackedArray<String>(sizes);
	}
	
	@Test
	public void testElementLayout() {
		PackedArray<Object> oneDimArray = new BigEndianPackedArray<Object>(5);
		assertThat(oneDimArray.computeAddress(0), is(equalTo(0)));
		assertThat(oneDimArray.computeAddress(1), is(equalTo(1)));
		assertThat(oneDimArray.computeAddress(2), is(equalTo(2)));
		assertThat(oneDimArray.computeAddress(3), is(equalTo(3)));
		assertThat(oneDimArray.computeAddress(4), is(equalTo(4)));

		PackedArray<Object> twoDimArrayBE = new BigEndianPackedArray<Object>(3, 3);
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
		
		PackedArray<Object> threeDimArray = new BigEndianPackedArray<Object>(2, 2, 2);
		assertThat(threeDimArray.computeAddress(0, 0, 0), is(equalTo(0)));
		assertThat(threeDimArray.computeAddress(0, 0, 1), is(equalTo(1)));
		assertThat(threeDimArray.computeAddress(0, 1, 0), is(equalTo(2)));
		assertThat(threeDimArray.computeAddress(0, 1, 1), is(equalTo(3)));
		assertThat(threeDimArray.computeAddress(1, 0, 0), is(equalTo(4)));
		assertThat(threeDimArray.computeAddress(1, 0, 1), is(equalTo(5)));
		assertThat(threeDimArray.computeAddress(1, 1, 0), is(equalTo(6)));
		assertThat(threeDimArray.computeAddress(1, 1, 1), is(equalTo(7)));
	}
}
