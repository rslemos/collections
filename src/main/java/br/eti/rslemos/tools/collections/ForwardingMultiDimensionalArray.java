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

import com.google.common.collect.ForwardingObject;

public abstract class ForwardingMultiDimensionalArray<T> extends ForwardingObject implements MultiDimensionalArray<T> {

	protected abstract MultiDimensionalArray<T> delegate();

	// informational methods
	
	public int dimensions() {
		return delegate().dimensions();
	}

	public int[] length() {
		return delegate().length();
	}

	// storage methods

	public T get(int... pos) {
		return delegate().get(pos);
	}

	public T set(T element, int... pos) {
		return delegate().set(element, pos);
	}

	// view methods

	public MultiDimensionalArray<T> slice(int dimension, int from, int to) {
		return delegate().slice(dimension, from, to);
	}

	public MultiDimensionalArray<T> swap(int dimensionA, int dimensionB) {
		return delegate().swap(dimensionA, dimensionB);
	}

	public MultiDimensionalArray<T> transpose() {
		return delegate().transpose();
	}

	// java.lang.Object methods

	@Override
	public boolean equals(Object obj) {
		return delegate().equals(obj);
	}

	@Override
	public int hashCode() {
		return delegate().hashCode();
	}
}
