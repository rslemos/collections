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

import org.junit.Test;

public abstract class ByteTrieUnitTest {

	private static final byte b1 = (byte)1;
	private static final byte b2 = (byte)2;
	private static final byte b3 = (byte)3;
	private static final byte b4 = (byte)4;
	protected ByteTrie trie;

	@Test
	public void testStartEmpty() {
		assertThat(trie.has(), is(equalTo(false)));
		assertThat(trie.has(b1), is(equalTo(false)));
		assertThat(trie.has(b2, b3), is(equalTo(false)));
	}

	@Test
	public void testAddZeroLength() {
		assertThat(trie.has(), is(equalTo(false)));
		assertThat(trie.add(), is(equalTo(true)));
		assertThat(trie.has(), is(equalTo(true)));
	}

	@Test
	public void testAddOneLength() {
		assertThat(trie.has(b1), is(equalTo(false)));
		assertThat(trie.add(b1), is(equalTo(true)));
		assertThat(trie.has(b1), is(equalTo(true)));
	}

	@Test
	public void testRepeatAddOneLength() {
		assertThat(trie.has(b1), is(equalTo(false)));
		assertThat(trie.add(b1), is(equalTo(true)));
		assertThat(trie.has(b1), is(equalTo(true)));
		assertThat(trie.add(b1), is(equalTo(false)));
		assertThat(trie.has(b1), is(equalTo(true)));
	}

	@Test
	public void test2AddsOneLength() {
		assertThat(trie.has(b1), is(equalTo(false)));
		assertThat(trie.has(b2), is(equalTo(false)));
		assertThat(trie.add(b1), is(equalTo(true)));
		assertThat(trie.add(b2), is(equalTo(true)));
		assertThat(trie.has(b1), is(equalTo(true)));
		assertThat(trie.has(b2), is(equalTo(true)));
	}

	@Test
	public void testAddTwoLength() {
		assertThat(trie.has(b1, b2), is(equalTo(false)));
		assertThat(trie.add(b1, b2), is(equalTo(true)));
		assertThat(trie.has(b1, b2), is(equalTo(true)));
	}

	@Test
	public void testRepeatedAddTwoLength() {
		assertThat(trie.has(b1, b2), is(equalTo(false)));
		assertThat(trie.add(b1, b2), is(equalTo(true)));
		assertThat(trie.has(b1, b2), is(equalTo(true)));
		assertThat(trie.add(b1, b2), is(equalTo(false)));
		assertThat(trie.has(b1, b2), is(equalTo(true)));
	}

	@Test
	public void test2AddsTwoLengthNoCommonPrefix() {
		assertThat(trie.has(b1, b2), is(equalTo(false)));
		assertThat(trie.has(b3, b4), is(equalTo(false)));
		assertThat(trie.add(b1, b2), is(equalTo(true)));
		assertThat(trie.add(b3, b4), is(equalTo(true)));
		assertThat(trie.has(b1, b2), is(equalTo(true)));
		assertThat(trie.has(b3, b4), is(equalTo(true)));
	}

	@Test
	public void test2AddsTwoLengthWithCommonPrefix() {
		assertThat(trie.has(b1, b2), is(equalTo(false)));
		assertThat(trie.has(b1, b4), is(equalTo(false)));
		assertThat(trie.add(b1, b2), is(equalTo(true)));
		assertThat(trie.add(b1, b4), is(equalTo(true)));
		assertThat(trie.has(b1, b2), is(equalTo(true)));
		assertThat(trie.has(b1, b4), is(equalTo(true)));
	}

	@Test
	public void testAddAfterAddPrefix() {
		assertThat(trie.has(b1), is(equalTo(false)));
		assertThat(trie.has(b1, b4), is(equalTo(false)));
		assertThat(trie.add(b1), is(equalTo(true)));
		assertThat(trie.add(b1, b4), is(equalTo(true)));
		assertThat(trie.has(b1), is(equalTo(true)));
		assertThat(trie.has(b1, b4), is(equalTo(true)));
	}

	@Test
	public void testAddBeforeAddPrefix() {
		assertThat(trie.has(b1), is(equalTo(false)));
		assertThat(trie.has(b1, b4), is(equalTo(false)));
		assertThat(trie.add(b1, b4), is(equalTo(true)));
		assertThat(trie.add(b1), is(equalTo(true)));
		assertThat(trie.has(b1), is(equalTo(true)));
		assertThat(trie.has(b1, b4), is(equalTo(true)));
	}

	@Test
	public void testGetSubTriePrefixNotFound() {
		assertThat(trie.getSubTrie(b1, b2), is(nullValue(ByteTrie.class)));
	}

	@Test
	public void testGetSubTrieExactFound() {
		trie.add(b1);
		ByteTrie subTrie = trie.getSubTrie(b1);
		
		assertThat(subTrie, is(not(nullValue(ByteTrie.class))));
		assertThat(subTrie.has(), is(equalTo(true)));
		assertThat(subTrie.has(b2), is(equalTo(false)));
	}

	@Test
	public void testGetSubTriePrefixFound() {
		trie.add(b1, b2);
		ByteTrie subTrie = trie.getSubTrie(b1);
		
		assertThat(subTrie, is(not(nullValue(ByteTrie.class))));
		assertThat(subTrie.has(), is(equalTo(false)));
		assertThat(subTrie.has(b2), is(equalTo(true)));
	}

	@Test
	public void testGetSubTriePrefixAndExactFound() {
		trie.add(b1, b2);
		trie.add(b1);
		ByteTrie subTrie = trie.getSubTrie(b1);
		
		assertThat(subTrie, is(not(nullValue(ByteTrie.class))));
		assertThat(subTrie.has(), is(equalTo(true)));
		assertThat(subTrie.has(b2), is(equalTo(true)));
	}

}