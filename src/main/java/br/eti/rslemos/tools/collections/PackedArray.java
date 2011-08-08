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

public abstract class PackedArray<T> {
	protected final int[] sizes;
	private T[] data;

	@SuppressWarnings("unchecked")
	public PackedArray(int... sizes) {
		this.sizes = sizes;
		data = (T[]) new Object[computeSize()];
	}
	
	private int computeSize() {
		int result = 1;
		
		for (int size : sizes) {
			result *= size;
		}
		
		return result;
	}
	
	protected abstract int computeAddress(int... pos);

	private void checkBoundaries(int[] pos) {
		if (pos.length != sizes.length)
			throw new IllegalArgumentException("Wrong number of dimensions: " + pos.length);
		
		for (int i = 0; i < pos.length; i++) {
			if (pos[i] < 0 || pos[i] >= sizes[i])
				throw new ArrayIndexOutOfBoundsException(pos[i]);
		}
	}

	public T get(int... pos) {
		checkBoundaries(pos);
		return data[computeAddress(pos)];
	}

	public void set(T element, int... pos) {
		checkBoundaries(pos);
		data[computeAddress(pos)] = element;
	}
}
