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

public class RowMajorPackedArray<T> extends PackedArray<T> {

	public RowMajorPackedArray(int... sizes) {
		super(sizes);
	}

	@Override
	protected int computeAddress(int... pos) {
		int address = 0;
		int stride = 1;
		
		for (int i = pos.length; i > 0; i--) {
			address += pos[i-1] * stride;
			stride *= sizes[i-1];
		}
		
		return address;
	}

}
