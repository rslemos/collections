package br.eti.rslemos.tools.collections;

import java.util.Arrays;

public abstract class AbstractMultiDimensionalArray<T> implements MultiDimensionalArray<T> {

	protected final int[] sizes;

	protected AbstractMultiDimensionalArray(int... sizes) {
		this.sizes = sizes;
	}

	// informational methods

	public int dimensions() {
		return sizes.length;
	}

	public int[] length() {
		return sizes.clone();
	}

	// java.lang.Object methods

	@Override
	public int hashCode() {
		// code adapted from java.lang.Arrays.deepHashCode()
		
		if (sizes.length == 0)
			return 0;
		
	    // will not iterate through bare data since
	    // offsets and ordering imposed by strides may still apply
		// besides, hashCode() is reset for each component
	
	    return hashCode0();
	}

	private int hashCode0(int... pos) {
		// code adapted from java.lang.Arrays.deepHashCode()
		
		if (pos.length == sizes.length) {
			// leaf case
	    	T element = get(pos);
	    	
	    	int elementHash = 0;
	        if (element instanceof Object[])
	            elementHash = Arrays.deepHashCode((Object[]) element);
	        else if (element instanceof byte[])
	            elementHash = Arrays.hashCode((byte[]) element);
	        else if (element instanceof short[])
	            elementHash = Arrays.hashCode((short[]) element);
	        else if (element instanceof int[])
	            elementHash = Arrays.hashCode((int[]) element);
	        else if (element instanceof long[])
	            elementHash = Arrays.hashCode((long[]) element);
	        else if (element instanceof char[])
	            elementHash = Arrays.hashCode((char[]) element);
	        else if (element instanceof float[])
	            elementHash = Arrays.hashCode((float[]) element);
	        else if (element instanceof double[])
	            elementHash = Arrays.hashCode((double[]) element);
	        else if (element instanceof boolean[])
	            elementHash = Arrays.hashCode((boolean[]) element);
	        else if (element != null)
	            elementHash = element.hashCode();
	        
	        return elementHash;
		} else {
			// inner branch case
			
			int result = 1;
	
			pos = appendDimension(pos);
			
			for (int i = 0; i < sizes[pos.length - 1]; i++) {
				pos[pos.length - 1] = i;
	            result = 31 * result + hashCode0(pos);
			}
	
	        return result;
		}
	}

	@Override
	public String toString() {
		if (sizes.length == 0)
			return "null"; // for consistency with java.util.Arrays.deepToString()
		
		StringBuilder buf = new StringBuilder();
		toString0(buf);
		return buf.toString();
	}

	private void toString0(StringBuilder buf, int... pos) {
		if (pos.length == sizes.length) {
			// leaf case
	    	T element = get(pos);
	    	
	        if (element instanceof Object[])
	        	buf.append(Arrays.deepToString((Object[]) element));
	        else if (element instanceof byte[])
	        	buf.append(Arrays.toString((byte[]) element));
	        else if (element instanceof short[])
	            buf.append(Arrays.toString((short[]) element));
	        else if (element instanceof int[])
	            buf.append(Arrays.toString((int[]) element));
	        else if (element instanceof long[])
	            buf.append(Arrays.toString((long[]) element));
	        else if (element instanceof char[])
	            buf.append(Arrays.toString((char[]) element));
	        else if (element instanceof float[])
	            buf.append(Arrays.toString((float[]) element));
	        else if (element instanceof double[])
	            buf.append(Arrays.toString((double[]) element));
	        else if (element instanceof boolean[])
	            buf.append(Arrays.toString((boolean[]) element));
	        else 
	        	buf.append(String.valueOf(element));
		} else {
			// inner branch case
			buf.append("[");
			pos = appendDimension(pos);
			
			for (int i = 0; i < sizes[pos.length - 1]; i++) {
				pos[pos.length - 1] = i;
				toString0(buf, pos);
				
				if (i == sizes[pos.length - 1] - 1)
					break;
				
				buf.append(", ");
			}
			buf.append("]");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (obj == this)
			return true;
	
		if (!(obj instanceof MultiDimensionalArray))
			return false;
		
		MultiDimensionalArray<?> other = (MultiDimensionalArray<?>) obj;
		
		return MultiDimensionalArrays.elementWiseEquals(this, other);
	}

	// auxiliary methods

	protected void checkBoundaries(int[] pos) {
		if (pos.length != sizes.length)
			throw new IllegalArgumentException("Wrong number of dimensions: " + pos.length);
		
		if (sizes.length > 0) {
			for (int i = 0; i < pos.length; i++) {
				if (pos[i] < 0 || pos[i] >= sizes[i])
					throw new ArrayIndexOutOfBoundsException(pos[i]);
			}
		} else
			throw new ArrayIndexOutOfBoundsException();
	}

	private static int[] appendDimension(int... pos) {
		int newPos[] = new int[pos.length + 1];
		System.arraycopy(pos, 0, newPos, 0, pos.length);
		return newPos;
	}

}