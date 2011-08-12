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

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UnmodifiableMultiDimensionalArrayUnitTest {

	@Mock private MultiDimensionalArray<String> delegate;
	@Mock private MultiDimensionalArray<String> view;
	
	private MultiDimensionalArray<String> subject;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		subject = new UnmodifiableMultiDimensionalArray<String>(delegate);
	}
	
	@Test
	public void testCantSet() {
		try {
			subject.set("", 0, 0);
			fail("Should have thrown " + UnsupportedOperationException.class);
		} catch (UnsupportedOperationException e) {
		}
		
		verifyNoMoreInteractions(delegate);
	}
	
	@Test
	public void testSliceViewIsUnmodifiable() {
		when(delegate.slice(1, 2, 3)).thenReturn(view);
		
		MultiDimensionalArray<String> slice = subject.slice(1, 2, 3);
		
		try {
			slice.set("", 0, 0);
			fail("Should have thrown " + UnsupportedOperationException.class);
		} catch (UnsupportedOperationException e) {
		}
		
		verify(delegate).slice(1, 2, 3);
		verifyNoMoreInteractions(delegate);
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testSwapViewIsUnmodifiable() {
		when(delegate.swap(1, 2)).thenReturn(view);
		
		MultiDimensionalArray<String> swap = subject.swap(1, 2);
		
		try {
			swap.set("", 0, 0);
			fail("Should have thrown " + UnsupportedOperationException.class);
		} catch (UnsupportedOperationException e) {
		}
		
		verify(delegate).swap(1, 2);
		verifyNoMoreInteractions(delegate);
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testTranspositionViewIsUnmodifiable() {
		when(delegate.transpose()).thenReturn(view);
		
		MultiDimensionalArray<String> transposition = subject.transpose();
		
		try {
			transposition.set("", 0, 0);
			fail("Should have thrown " + UnsupportedOperationException.class);
		} catch (UnsupportedOperationException e) {
		}
		
		verify(delegate).transpose();
		verifyNoMoreInteractions(delegate);
		verifyNoMoreInteractions(view);
	}
}
