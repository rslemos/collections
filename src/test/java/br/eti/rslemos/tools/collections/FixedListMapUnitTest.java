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

import java.util.Map;

import org.junit.Test;

public class FixedListMapUnitTest {
	@Test
	public void test() {
//		SimplePackedArray<String> array = new SimplePackedArray<String>(
//				new String[][] {
//					{ "0A", "0B", "0C" },
//					{ "1A", "1B", "1C" },
//					{ "2A", "2B", "2C" },
//					{ "3A", "3B", "3C" },
//				},
//				4, 3
//			);

		
		SimplePackedArray<String> array = new SimplePackedArray<String>(4, 3);
		FixedListMap<String,String> listMap = new FixedListMap<String, String>(array, "featureA", "featureB", "featureC");
		
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

		System.out.println(array);
	}
}
