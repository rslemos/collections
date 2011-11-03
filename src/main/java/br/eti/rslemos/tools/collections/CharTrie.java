package br.eti.rslemos.tools.collections;

public interface CharTrie extends Iterable<char[]> {

	boolean has(char... b);

	boolean add(char... b);

	CharTrie getSubTrie(char... b);
	
}
