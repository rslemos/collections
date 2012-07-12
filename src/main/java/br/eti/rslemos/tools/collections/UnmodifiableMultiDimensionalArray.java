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

public class UnmodifiableMultiDimensionalArray<T> extends ForwardingMultiDimensionalArray<T> {

	private final MultiDimensionalArray<T> delegate;

	public UnmodifiableMultiDimensionalArray(MultiDimensionalArray<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected MultiDimensionalArray<T> delegate() {
		return delegate;
	}

	// storage methods

	@Override
	public T set(T element, int... pos) {
		throw new UnsupportedOperationException();
	}

	// view methods

	@Override
	public MultiDimensionalArray<T> slice(int dimension, int from, int to) {
		return new UnmodifiableMultiDimensionalArray<T>(super.slice(dimension, from, to));
	}

	@Override
	public MultiDimensionalArray<T> swap(int dimensionA, int dimensionB) {
		return new UnmodifiableMultiDimensionalArray<T>(super.swap(dimensionA, dimensionB));
	}

	@Override
	public MultiDimensionalArray<T> transpose() {
		return new UnmodifiableMultiDimensionalArray<T>(super.transpose());
	}
}
