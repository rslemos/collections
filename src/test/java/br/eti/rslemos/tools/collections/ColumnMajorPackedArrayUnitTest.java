/*******************************************************************************
 * BEGIN COPYRIGHT NOTICE
 * 
 * This file is part of program "Collections"
 * Copyright 2011  Rodrigo Lemos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * END COPYRIGHT NOTICE
 ******************************************************************************/
package br.eti.rslemos.tools.collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ColumnMajorPackedArrayUnitTest extends PackedArrayUnitTest {
	
	@Override
	protected PackedArray<String> createStringArray(int... sizes) {
		return new ColumnMajorPackedArray<String>(sizes);
	}
	
	@Test
	public void testElementLayout() {
		ColumnMajorPackedArray<Object> oneDimArray = new ColumnMajorPackedArray<Object>(5);
		assertThat(oneDimArray.computeAddress(0), is(equalTo(0)));
		assertThat(oneDimArray.computeAddress(1), is(equalTo(1)));
		assertThat(oneDimArray.computeAddress(2), is(equalTo(2)));
		assertThat(oneDimArray.computeAddress(3), is(equalTo(3)));
		assertThat(oneDimArray.computeAddress(4), is(equalTo(4)));

		ColumnMajorPackedArray<Object> twoDimArray = new ColumnMajorPackedArray<Object>(3, 3);
		assertThat(twoDimArray.computeAddress(0, 0), is(equalTo(0)));
		assertThat(twoDimArray.computeAddress(1, 0), is(equalTo(1)));
		assertThat(twoDimArray.computeAddress(2, 0), is(equalTo(2)));
		assertThat(twoDimArray.computeAddress(0, 1), is(equalTo(3)));
		assertThat(twoDimArray.computeAddress(1, 1), is(equalTo(4)));
		assertThat(twoDimArray.computeAddress(2, 1), is(equalTo(5)));
		assertThat(twoDimArray.computeAddress(0, 2), is(equalTo(6)));
		assertThat(twoDimArray.computeAddress(1, 2), is(equalTo(7)));
		assertThat(twoDimArray.computeAddress(2, 2), is(equalTo(8)));

		ColumnMajorPackedArray<Object> threeDimArray = new ColumnMajorPackedArray<Object>(2, 2, 2);
		assertThat(threeDimArray.computeAddress(0, 0, 0), is(equalTo(0)));
		assertThat(threeDimArray.computeAddress(1, 0, 0), is(equalTo(1)));
		assertThat(threeDimArray.computeAddress(0, 1, 0), is(equalTo(2)));
		assertThat(threeDimArray.computeAddress(1, 1, 0), is(equalTo(3)));
		assertThat(threeDimArray.computeAddress(0, 0, 1), is(equalTo(4)));
		assertThat(threeDimArray.computeAddress(1, 0, 1), is(equalTo(5)));
		assertThat(threeDimArray.computeAddress(0, 1, 1), is(equalTo(6)));
		assertThat(threeDimArray.computeAddress(1, 1, 1), is(equalTo(7)));
	}
}
