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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.Test;

import com.google.common.collect.testing.ListTestSuiteBuilder;
import com.google.common.collect.testing.MapTestSuiteBuilder;
import com.google.common.collect.testing.SampleElements;
import com.google.common.collect.testing.TestListGenerator;
import com.google.common.collect.testing.TestMapGenerator;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.ListFeature;
import com.google.common.collect.testing.features.MapFeature;
import com.google.common.collect.testing.testers.MapPutAllTester;
import com.google.common.collect.testing.testers.MapPutTester;

public class FixedSizeListMapUnitTest extends TestCase {
	public static TestSuite suite() throws Exception {
		TestSuite suite = new TestSuite(FixedSizeListMapUnitTest.class);
		suite.addTest(ListTestSuiteBuilder
				.using(new TestListMapGenerator())
				.named("new FixedSizeListMap[size, keys]")
				.withFeatures(
					ListFeature.SUPPORTS_SET,
					CollectionFeature.RESTRICTS_ELEMENTS,
					CollectionFeature.KNOWN_ORDER,
					CollectionSize.ONE,
					CollectionSize.SEVERAL)
				.createTestSuite()
			);
		
		suite.addTest(MapTestSuiteBuilder
				.using(new TestListMapMapGenerator())
				.named("FixedSizeListMap.get[]")
				.withFeatures(
					MapFeature.ALLOWS_NULL_VALUES,
					MapFeature.RESTRICTS_KEYS,
					MapFeature.SUPPORTS_PUT,
					MapFeature.SUPPORTS_PUT_ALL,
					CollectionSize.ONE,
					CollectionSize.SEVERAL)
				.suppressing(
					// these tests assumes that a new key can be stored ("not present", "some present") 
					MapPutTester.class.getMethod("testPut_supportedNotPresent"),
					MapPutAllTester.class.getMethod("testPutAll_supportedNonePresent"),
					MapPutAllTester.class.getMethod("testPutAll_supportedSomePresent"),
					
					// so sad that the null value test are based on new key
					// thus, they must stay off
					MapPutTester.class.getMethod("testPut_nullValueSupported"),
					MapPutAllTester.class.getMethod("testPutAll_nullValueSupported")
					)
				.createTestSuite()
			);
		
		return suite;
	}
	
	@Test
	public void testGeneralUsage() {
		FixedSizeListMap<String,String> listMap = new FixedSizeListMap<String, String>(4, "featureA", "featureB", "featureC");
		
		assertThat(listMap.size(), is(equalTo(4)));
		
		Map<String, String> map0 = listMap.get(0);
		Map<String, String> map1 = listMap.get(1);
		Map<String, String> map2 = listMap.get(2);
		Map<String, String> map3 = listMap.get(3);
		
		assertThat(map0, is(not(nullValue(Map.class))));
		assertThat(map1, is(not(nullValue(Map.class))));
		assertThat(map2, is(not(nullValue(Map.class))));
		assertThat(map3, is(not(nullValue(Map.class))));
		
		assertThat(map0.get("featureA"), is(nullValue(String.class)));
		assertThat(map0.get("featureB"), is(nullValue(String.class)));
		assertThat(map0.get("featureC"), is(nullValue(String.class)));

		assertThat(map1.get("featureA"), is(nullValue(String.class)));
		assertThat(map1.get("featureB"), is(nullValue(String.class)));
		assertThat(map1.get("featureC"), is(nullValue(String.class)));

		assertThat(map2.get("featureA"), is(nullValue(String.class)));
		assertThat(map2.get("featureB"), is(nullValue(String.class)));
		assertThat(map2.get("featureC"), is(nullValue(String.class)));

		assertThat(map3.get("featureA"), is(nullValue(String.class)));
		assertThat(map3.get("featureB"), is(nullValue(String.class)));
		assertThat(map3.get("featureC"), is(nullValue(String.class)));

		assertThat(map0.put("featureA", "0A"), is(nullValue(String.class)));
		assertThat(map0.put("featureB", "0B"), is(nullValue(String.class)));
		assertThat(map0.put("featureC", "0C"), is(nullValue(String.class)));
                                            
		assertThat(map1.put("featureA", "1A"), is(nullValue(String.class)));
		assertThat(map1.put("featureB", "1B"), is(nullValue(String.class)));
		assertThat(map1.put("featureC", "1C"), is(nullValue(String.class)));
                                            
		assertThat(map2.put("featureA", "2A"), is(nullValue(String.class)));
		assertThat(map2.put("featureB", "2B"), is(nullValue(String.class)));
		assertThat(map2.put("featureC", "2C"), is(nullValue(String.class)));
                                            
		assertThat(map3.put("featureA", "3A"), is(nullValue(String.class)));
		assertThat(map3.put("featureB", "3B"), is(nullValue(String.class)));
		assertThat(map3.put("featureC", "3C"), is(nullValue(String.class)));
		
		assertThat(map0.get("featureA"), is(equalTo("0A")));
		assertThat(map0.get("featureB"), is(equalTo("0B")));
		assertThat(map0.get("featureC"), is(equalTo("0C")));

		assertThat(map1.get("featureA"), is(equalTo("1A")));
		assertThat(map1.get("featureB"), is(equalTo("1B")));
		assertThat(map1.get("featureC"), is(equalTo("1C")));

		assertThat(map2.get("featureA"), is(equalTo("2A")));
		assertThat(map2.get("featureB"), is(equalTo("2B")));
		assertThat(map2.get("featureC"), is(equalTo("2C")));

		assertThat(map3.get("featureA"), is(equalTo("3A")));
		assertThat(map3.get("featureB"), is(equalTo("3B")));
		assertThat(map3.get("featureC"), is(equalTo("3C")));

		System.out.println(listMap.toString());
	}
	
	private static final class TestListMapGenerator implements TestListGenerator<Map<String, String>> {
		public List<Map<String, String>> create(Map<String, String>... elements) {
			List<Map<String, String>> fixedSizeListMap = new FixedSizeListMap<String, String>(elements.length, "A", "B", "C", "D");
			for (int i = 0; i < elements.length; i++) {
				Map<String, String> map0 = elements[i];
				Map<String, String> map1 = fixedSizeListMap.get(i);
				for (Entry<String, String> entry : map0.entrySet()) {
					map1.put(entry.getKey(), entry.getValue());
				}
			}
			
			return fixedSizeListMap;
		}

		public SampleElements<Map<String, String>> samples() {
			Map<String, String> e0 = newMap(null, null, null, null);
			Map<String, String> e1 = newMap("value for A", null, null, null);
			Map<String, String> e2 = newMap(null, "value for B", null, null);
			Map<String, String> e3 = newMap(null, null, "value for C", null);;
			Map<String, String> e4 = newMap("value for A (multi)", null, "value for C (multi)", "value for D (multi)");
			
			return new SampleElements<Map<String, String>>(e0, e1, e2, e3, e4);
		}

		private Map<String, String> newMap(String a, String b, String c, String d) {
			TreeMap<String, String> map = new TreeMap<String, String>();
			map.put("A", a);
			map.put("B", b);
			map.put("C", c);
			map.put("D", d);
			return map;
		}

		public Iterable<Map<String, String>> order(List<Map<String, String>> insertionOrder) {
			return insertionOrder;
		}

		@SuppressWarnings("unchecked")
		public Map<String, String>[] createArray(int length) {
			return new Map[length];
		}

		@SuppressWarnings("unchecked")
		public List<Map<String, String>> create(Object... elements) {
			// adapted from com.google.common.collect.testing.TestStringListGenerator.create(Object...)
			Map<String, String>[] array = new Map[elements.length];
		    int i = 0;
		    for (Object e : elements) {
		      array[i++] = (Map<String, String>) e;
		    }
		    return create(array);
		}
	}

	private static final class TestListMapMapGenerator implements TestMapGenerator<String, String> {
		public Map<String, String> create(Entry<String, String>... entries) {
			Set<String> keys = new HashSet<String>();
			
			for (Entry<String, String> entry : entries) {
				keys.add(entry.getKey());
			}
			
			FixedSizeListMap<String, String> list = new FixedSizeListMap<String, String>(1, keys.toArray(new String[keys.size()]));
			Map<String, String> map = list.get(0);
			
			for (Entry<String, String> entry : entries) {
				map.put(entry.getKey(), entry.getValue());
			}
			
			
			return map;
		}
		
		public SampleElements<Entry<String, String>> samples() {
			Entry<String, String> e0 = new AbstractMap.SimpleEntry<String, String>("A", "value for A");
			Entry<String, String> e1 = new AbstractMap.SimpleEntry<String, String>("B", "value for B");
			Entry<String, String> e2 = new AbstractMap.SimpleEntry<String, String>("C", "value for C");
			Entry<String, String> e3 = new AbstractMap.SimpleEntry<String, String>("D", "value for D");
			Entry<String, String> e4 = new AbstractMap.SimpleEntry<String, String>("E", "value for E");;
			return new SampleElements<Entry<String,String>>(e0, e1, e2, e3, e4);
		}

		@SuppressWarnings("unchecked")
		public Entry<String, String>[] createArray(int length) {
			return new Entry[length];
		}

		public Iterable<Entry<String, String>> order(List<Entry<String, String>> insertionOrder) {
			Collections.sort(insertionOrder, new Comparator<Entry<String, String>>() {
				public int compare(Entry<String, String> o1, Entry<String, String> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			});
			
			return insertionOrder;
		}

		public String[] createKeyArray(int length) {
			return new String[length];
		}

		public String[] createValueArray(int length) {
			return new String[length];
		}
		
		public Map<String, String> create(Object... entries) {
			// copied from com.google.common.collect.testing.TestStringMapGenerator.create(Object...)
		    @SuppressWarnings("unchecked")
		    Entry<String, String>[] array = new Entry[entries.length];
		    int i = 0;
		    for (Object o : entries) {
		      @SuppressWarnings("unchecked")
		      Entry<String, String> e = (Entry<String, String>) o;
		      array[i++] = e;
		    }
		    return create(array);
		}

	}
}
