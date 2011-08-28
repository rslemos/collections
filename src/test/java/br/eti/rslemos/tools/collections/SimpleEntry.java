/*
 * Copyright (c) 1997, 2007, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 * 
 * 
 * 
 * THIS CODE WAS COPIED FROM OPENJDK VERSION 6 as distributed by Debian under
 * package name openjdk-6-source version 6b23~pre7-1. This was preferred over
 * just depending on java6 because just two tests depend on it.
 * 
 * Note that under java 5, AbstractMap.SimpleEntry is not public, therefore
 * not usable.
 */
package br.eti.rslemos.tools.collections;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

/**
 * An Entry maintaining a key and a value. The value may be changed using the
 * <tt>setValue</tt> method. This class facilitates the process of building
 * custom map implementations. For example, it may be convenient to return
 * arrays of <tt>SimpleEntry</tt> instances in method
 * <tt>Map.entrySet().toArray</tt>.
 */
public class SimpleEntry<K, V> implements Entry<K, V>, Serializable {
	private static final long serialVersionUID = -8499721149061103585L;

	private final K key;
	private V value;

	/**
	 * Creates an entry representing a mapping from the specified key to the
	 * specified value.
	 * 
	 * @param key
	 *            the key represented by this entry
	 * @param value
	 *            the value represented by this entry
	 */
	public SimpleEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	/**
	 * Creates an entry representing the same mapping as the specified entry.
	 * 
	 * @param entry
	 *            the entry to copy
	 */
	public SimpleEntry(Entry<? extends K, ? extends V> entry) {
		this.key = entry.getKey();
		this.value = entry.getValue();
	}

	/**
	 * Returns the key corresponding to this entry.
	 * 
	 * @return the key corresponding to this entry
	 */
	public K getKey() {
		return key;
	}

	/**
	 * Returns the value corresponding to this entry.
	 * 
	 * @return the value corresponding to this entry
	 */
	public V getValue() {
		return value;
	}

	/**
	 * Replaces the value corresponding to this entry with the specified value.
	 * 
	 * @param value
	 *            new value to be stored in this entry
	 * @return the old value corresponding to the entry
	 */
	public V setValue(V value) {
		V oldValue = this.value;
		this.value = value;
		return oldValue;
	}

	/**
	 * Compares the specified object with this entry for equality. Returns
	 * {@code true} if the given object is also a map entry and the two entries
	 * represent the same mapping. More formally, two entries {@code e1} and
	 * {@code e2} represent the same mapping if
	 * 
	 * <pre>
	 * (e1.getKey() == null ? e2.getKey() == null : e1.getKey().equals(e2.getKey()))
	 * 		&amp;&amp; (e1.getValue() == null ? e2.getValue() == null : e1.getValue()
	 * 				.equals(e2.getValue()))
	 * </pre>
	 * 
	 * This ensures that the {@code equals} method works properly across
	 * different implementations of the {@code Map.Entry} interface.
	 * 
	 * @param o
	 *            object to be compared for equality with this map entry
	 * @return {@code true} if the specified object is equal to this map entry
	 * @see #hashCode
	 */
	public boolean equals(Object o) {
		if (!(o instanceof Map.Entry))
			return false;
		Entry<?, ?> e = (Entry<?, ?>) o;
		return eq(key, e.getKey()) && eq(value, e.getValue());
	}

	/**
	 * Returns the hash code value for this map entry. The hash code of a map
	 * entry {@code e} is defined to be:
	 * 
	 * <pre>
	 * (e.getKey() == null ? 0 : e.getKey().hashCode())
	 * 		&circ; (e.getValue() == null ? 0 : e.getValue().hashCode())
	 * </pre>
	 * 
	 * This ensures that {@code e1.equals(e2)} implies that
	 * {@code e1.hashCode()==e2.hashCode()} for any two Entries {@code e1} and
	 * {@code e2}, as required by the general contract of
	 * {@link Object#hashCode}.
	 * 
	 * @return the hash code value for this map entry
	 * @see #equals
	 */
	public int hashCode() {
		return (key == null ? 0 : key.hashCode())
				^ (value == null ? 0 : value.hashCode());
	}

	/**
	 * Returns a String representation of this map entry. This implementation
	 * returns the string representation of this entry's key followed by the
	 * equals character ("<tt>=</tt>") followed by the string representation of
	 * this entry's value.
	 * 
	 * @return a String representation of this map entry
	 */
	public String toString() {
		return key + "=" + value;
	}

	/**
	 * Utility method for SimpleEntry and SimpleImmutableEntry. Test for
	 * equality, checking for nulls.
	 */
	private static boolean eq(Object o1, Object o2) {
		return o1 == null ? o2 == null : o1.equals(o2);
	}
}
