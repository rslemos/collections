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

import java.util.Arrays;

public class BigEndianPackedArray<T> extends PackedArray<T> {

	public BigEndianPackedArray(int... sizes) {
		super(sizes, computeStrides(sizes), computeOffsets(sizes));
	}

	private static int[] computeStrides(int[] sizes) {
		int[] strides = sizes.clone();
		
		System.arraycopy(sizes, 1, strides, 0, strides.length - 1);
		strides[strides.length - 1] = 1;
		
		for (int i = strides.length - 1; i > 0; i--) {
			strides[i-1] *= strides[i];
		}
		
		return strides;
	}

	private static int[] computeOffsets(int[] sizes) {
		int[] offsets = sizes.clone();
		Arrays.fill(offsets, 0);
		return offsets;
	}
}
