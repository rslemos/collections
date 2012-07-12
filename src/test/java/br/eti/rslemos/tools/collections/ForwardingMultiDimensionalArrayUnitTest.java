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
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ForwardingMultiDimensionalArrayUnitTest {
	
	@Mock private MultiDimensionalArray<String> delegate;
	private MultiDimensionalArray<String> subject;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		subject = wrap(delegate);
	}

	@Test
	public void testDimensions() {
		when(delegate.dimensions()).thenReturn(2);
		
		assertThat(subject.dimensions(), is(equalTo(2)));
		assertThat(subject.dimensions(), is(equalTo(2)));

		verify(delegate, times(2)).dimensions();
		verifyNoMoreInteractions(delegate);
	}
	
	@Test
	public void testLength() {
		int[] length = new int[] {1, 2, 3, 4, 5};
		when(delegate.length()).thenReturn(length);
		
		assertThat(subject.length(), is(sameInstance(length)));
		assertThat(subject.length(), is(sameInstance(length)));

		verify(delegate, times(2)).length();
		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void testGet() {
		int[] pos = new int[0];
		String value = "this";
		
		when(delegate.get(pos)).thenReturn(value);
		
		assertThat(subject.get(pos), is(sameInstance(value)));
		assertThat(subject.get(pos), is(sameInstance(value)));

		verify(delegate, times(2)).get(pos);
		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void testSet() {
		int[] pos = new int[0];
		String oldValue = "this";
		String newValue = "that";
		
		when(delegate.set(newValue, pos)).thenReturn(oldValue);
		when(delegate.set(oldValue, pos)).thenReturn(newValue);
		
		assertThat(subject.set(newValue, pos), is(sameInstance(oldValue)));
		assertThat(subject.set(oldValue, pos), is(sameInstance(newValue)));

		verify(delegate, times(1)).set(newValue, pos);
		verify(delegate, times(1)).set(oldValue, pos);
		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void testSlice() {
		MultiDimensionalArray<String> view = wrap(null);
		
		when(delegate.slice(10, 5, 3)).thenReturn(view);
		
		assertThat(subject.slice(10, 5, 3), is(sameInstance(view)));
		assertThat(subject.slice(10, 5, 3), is(sameInstance(view)));
		
		verify(delegate, times(2)).slice(10, 5, 3);
		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void testSwap() {
		MultiDimensionalArray<String> view = wrap(null);
		
		when(delegate.swap(10, 20)).thenReturn(view);
		
		assertThat(subject.swap(10, 20), is(sameInstance(view)));
		assertThat(subject.swap(10, 20), is(sameInstance(view)));
		
		verify(delegate, times(2)).swap(10, 20);
		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void testTranspose() {
		MultiDimensionalArray<String> view = wrap(null);
		
		when(delegate.transpose()).thenReturn(view);
		
		assertThat(subject.transpose(), is(sameInstance(view)));
		assertThat(subject.transpose(), is(sameInstance(view)));
		
		verify(delegate, times(2)).transpose();
		verifyNoMoreInteractions(delegate);
	}

	@Test
	@Ignore("mockito does not record equals()")
	public void testEquals() {
		MultiDimensionalArray<String> other = wrap(null);
		
		when(delegate.equals(other)).thenReturn(false);
		
		assertThat(subject.equals(other), is(false));
		assertThat(subject.equals(other), is(false));
		
		verify(delegate, times(2)).equals(other);
		verifyNoMoreInteractions(delegate);
	}

	@Test
	@Ignore("mockito does not record hashCode()")
	public void testHashCode() {
		when(delegate.hashCode()).thenReturn(100);
		
		assertThat(subject.hashCode(), is(equalTo(100)));
		assertThat(subject.hashCode(), is(equalTo(100)));
		
		verify(delegate, times(2)).hashCode();
		verifyNoMoreInteractions(delegate);
	}

	@Test
	@Ignore("mockito does not record toString()")
	public void testToString() {
		String tostring = "tostring";
		
		when(delegate.toString()).thenReturn(tostring);
		
		assertThat(subject.toString(), is(sameInstance(tostring)));
		assertThat(subject.toString(), is(sameInstance(tostring)));
		
		verify(delegate, times(2)).toString();
		verifyNoMoreInteractions(delegate);
	}

	private static <T> ForwardingMultiDimensionalArray<T> wrap(final MultiDimensionalArray<T> delegate) {
		return new ForwardingMultiDimensionalArray<T>() {
			@Override protected MultiDimensionalArray<T> delegate() { return delegate; }
		};
	}
}
