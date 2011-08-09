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

import static junit.framework.Assert.fail;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class OrderedMultiDimensionalMapUnitTest {
	@Test
	public void testOneDimensionalMap() {
		MultiDimensionalMap<String, Integer> map = new OrderedMultiDimensionalMap<String, Integer>(new PackedArray<Integer>(10));
		
		// info
		assertThat(map.dimensions(), is(equalTo(1)));
		assertThat(map.length(0), is(equalTo(0)));
		
		// storage
		map.put( 1, " 1");
		map.put( 2, " 2");
		map.put( 3, " 3");
		map.put( 4, " 4");
		map.put( 5, " 5");
		map.put( 6, " 6");
		map.put( 7, " 7");
		map.put( 8, " 8");
		map.put( 9, " 9");
		map.put(10, "10");
		
		assertThat(map.length(0), is(equalTo(10)));

		assertThat(map.get(" 1"), is(equalTo( 1)));
		assertThat(map.get(" 2"), is(equalTo( 2)));
		assertThat(map.get(" 3"), is(equalTo( 3)));
		assertThat(map.get(" 4"), is(equalTo( 4)));
		assertThat(map.get(" 5"), is(equalTo( 5)));
		assertThat(map.get(" 6"), is(equalTo( 6)));
		assertThat(map.get(" 7"), is(equalTo( 7)));
		assertThat(map.get(" 8"), is(equalTo( 8)));
		assertThat(map.get(" 9"), is(equalTo( 9)));
		assertThat(map.get("10"), is(equalTo(10)));

		// boundary check
		lengthAndExpectException(IllegalArgumentException.class, map, -1);
		lengthAndExpectException(IllegalArgumentException.class, map,  1);
		
		accessAndExpectException(IllegalArgumentException.class, map);
		accessAndExpectException(IllegalArgumentException.class, map, "key1", "key2");
		accessAndExpectException(IllegalArgumentException.class, map, "key1", "key2", "key3");
	}

	private static <K, V, E extends RuntimeException> void accessAndExpectException(Class<E> clazz, MultiDimensionalMap<K, V> map, K... key) {
		getAndExpectException(clazz, map, key);
		putAndExpectException(clazz, map, key);
	}

	private static <K, V, E extends RuntimeException> void putAndExpectException(Class<E> clazz, MultiDimensionalMap<K, V> map, K... key) {
		try {
			map.put(null, key);
			fail("Should have thrown " + clazz);
		} catch (RuntimeException expected) {
			if (!clazz.isInstance(expected))
				throw expected;
		}
	}

	private static <K, V, E extends RuntimeException> void getAndExpectException(Class<E> clazz, MultiDimensionalMap<K, V> map, K... key) {
		try {
			map.get(key);
			fail("Should have thrown " + clazz);
		} catch (RuntimeException expected) {
			if (!clazz.isInstance(expected))
				throw expected;
		}
	}

	private static <K, V, E extends RuntimeException> void lengthAndExpectException(Class<E> clazz, MultiDimensionalMap<K, V> map, int dimension) {
		try {
			map.length(dimension);
			fail("Should have thrown " + clazz);
		} catch (RuntimeException expected) {
			if (!clazz.isInstance(expected))
				throw expected;
		}
	}
}
