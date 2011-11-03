package br.eti.rslemos.tools.collections;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Iterator;

public class ByteCharTrie implements CharTrie {
	private final ByteTrie trie;
	private final CharsetDecoder decoder;
	private final CharsetEncoder encoder;
	
	public ByteCharTrie(ByteTrie trie, Charset charset) {
		this(trie, charset.newDecoder(), charset.newEncoder());
	}

	private ByteCharTrie(ByteTrie trie, CharsetDecoder decoder, CharsetEncoder encoder) {
		this.trie = trie;
		this.decoder = decoder;
		this.encoder = encoder;
	}

	public Iterator<char[]> iterator() {
		final Iterator<byte[]> iterator = trie.iterator();
		return new Iterator<char[]>() {

			public boolean hasNext() {
				return iterator.hasNext();
			}

			public char[] next() {
				return toCharArray(iterator.next());
			}

			public void remove() {
				iterator.remove();
			}
			
		};
	}

	public boolean has(char... c) {
		return trie.has(toByteArray(c));
	}

	public boolean add(char... c) {
		return trie.add(toByteArray(c));
	}

	public CharTrie getSubTrie(char... c) {
		ByteTrie subTrie = trie.getSubTrie(toByteArray(c));
		
		return subTrie != null ? new ByteCharTrie(subTrie, decoder, encoder) : null;
	}

	private byte[] toByteArray(char... c) {
		try {
			return encoder.encode(CharBuffer.wrap(c)).array();
		} catch (CharacterCodingException e) {
			throw new RuntimeException(e);
		}
	}

	private char[] toCharArray(byte... b) {
		try {
			return decoder.decode(ByteBuffer.wrap(b)).array();
		} catch (CharacterCodingException e) {
			throw new RuntimeException(e);
		}
	}

}
