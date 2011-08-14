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

public class ArrayByteTrie implements ByteTrie {
	private final ArrayByteTrie[] subTries = new ArrayByteTrie[256];
	private boolean is;
	
	public boolean has(byte... b) {
		if (b.length == 0)
			return is;
		else {
			ByteTrie next = subTries[b[0]];
			return next != null ? next.has(tail(b)) : false;
		}
	}

	public boolean add(byte... b) {
		if (b.length == 0)
			try {
				return !is;
			} finally {
				is = true;
			}
		else {
			ArrayByteTrie next = subTries[b[0]];
			if (next == null) {
				next = new ArrayByteTrie();
				subTries[b[0]] = next;
			}
			return next.add(tail(b));
		}
	}

	public ArrayByteTrie getSubTrie(byte... b) {
		if (b.length == 0)
			return this;
		else {
			ArrayByteTrie next = subTries[b[0]];
			return next != null ? next.getSubTrie(tail(b)) : null;
		}
	}

	private static byte[] tail(byte... b) {
		byte[] result = new byte[b.length - 1];
		System.arraycopy(b, 1, result, 0, result.length);
		return result;
	}
}
