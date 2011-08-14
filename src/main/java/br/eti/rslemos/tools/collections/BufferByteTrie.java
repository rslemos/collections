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

import java.nio.ByteBuffer;

public class BufferByteTrie implements ByteTrie {
	
	private static final short YES = (short)0;
	private static final short NO = (short)-1;
	
	private ByteBuffer states;
	private ByteBuffer work;
	
	public BufferByteTrie() {
		states = ByteBuffer.allocateDirect(1048576);
		work = ByteBuffer.allocateDirect(1048576);
		
		// 0 is reserved for "not found"
		work.position(1);
	}
	
	public boolean has(byte... b) {
		State s = getState(0);
		
		int i;
		for (i = 0; i < b.length; i++) {
			State tmp = s.getNextState(b[i]);
			if (tmp == null)
				break;
			
			s = tmp;
		}
		
		if (i < b.length)
			return false;
		
		return s.getOutput().has();
	}

	private State getState(int index) {
		return new State(index);
	}

	public boolean add(byte... b) {
		State s = getState(0);
		
		int i;
		for (i = 0; i < b.length; i++) {
			State tmp = s.getNextState(b[i]);
			if (tmp == null)
				break;
			
			s = tmp;
		}

		if (i == b.length)
			return false;
		
		for (; i < b.length - 1; i++) {
			s = s.addTransition(b[i], YES);
		}
		
		s = s.addTransition(b[i], NO);
		
		return true;
	}

	public ByteTrie getSubTrie(byte... b) {
		return null;
	}

	// flyweights (the pseudo-context is the implicit outer-class)
	private class State {
		private static final int OFF_OUTPUTS = 0;
		private static final int OFF_TRANSITIONS = OFF_OUTPUTS + (Integer.SIZE / 8);
		private static final int SIZE = OFF_TRANSITIONS + (Integer.SIZE / 8);
		
		private final int index;
		
		public State(int index) {
			this.index = index;
		}
		
		public Output getOutput() {
			return new Output(states.getInt(index * SIZE + OFF_OUTPUTS));
		}

		private Transitions getTransitions() {
			int offset = states.getInt(index * SIZE + OFF_TRANSITIONS);
			
			return offset > 0 ? new Transitions(offset) : null;
		}

		private void setTransitions(int offset) {
			states.putInt(index * SIZE + OFF_TRANSITIONS, offset);
		}

		public State getNextState(byte b) {
			Transitions transitions = this.getTransitions();
			if (transitions != null) {
				Transition t = transitions.find(b);
				if (t != null)
					return getState(t.getTo());
				else
					return null;
			} else
				return null;
		}

		public State addTransition(byte b, short output) {
			Transitions t = getTransitions();
			t = growblock(t, t.size(), t.size() + Transition.SIZE);
			setTransitions(t.offset);
			
			return null;
		}

		private Transitions growblock(Transitions t, int oldsize, int newsize) {
			if (work.position() == t.offset + oldsize) {
				byte[] reserve = new byte[newsize - oldsize];
				work.put(reserve);
				return t;
			} else {
				byte[] reserve = new byte[newsize];
				int offset = work.position();
				work.put(reserve);
				
				return new Transitions(offset);
			}
		}

		public String toString() {
			return String.valueOf(index);
		}
	}
	
	// the offset is *THE* output (zero means false; otherwise true)
	private class Output {
		private final int offset;

		public Output(int offset) {
			this.offset = offset;
		}

		public boolean has() {
			return offset != 0;
		}
		
		public int size() {
			return 0;
		}
	}
	
	private class Transitions {
		private static final int OFF_LENGTH = 0;
		private static final int OFF_VECTOR_BASE = OFF_LENGTH + (Short.SIZE / 8);
		
		private final int offset;

		public Transitions(int offset) {
			this.offset = offset;
		}
		
		public short count() {
			return work.getShort(offset + OFF_LENGTH);
		}
		
		public Transition getTransition(short index) {
			if (index < 0 || index >= count())
				throw new ArrayIndexOutOfBoundsException(index);
			
			return new Transition(offset + OFF_VECTOR_BASE + index * Transition.SIZE);
		}
		
		public Transition find(byte b) {
			short count = count();
			
			for (short i = 0; i < count; i++) {
				Transition t = getTransition(i);
				if (t.getInput() == b)
					return t;
			}
			
			return null;
		}

		public int size() {
			return OFF_VECTOR_BASE + count() * Transition.SIZE;
		}
	}
	
	private class Transition {
		private static final int OFF_INPUT = 0;
		private static final int OFF_TO = OFF_INPUT + (Byte.SIZE / 8);
		private static final int SIZE = OFF_TO + (Integer.SIZE / 8);
		
		private final int offset;
		
		public Transition(int offset) {
			this.offset = offset;
		}
		
		public byte getInput() {
			return work.get(offset + OFF_INPUT);
		}
		
		public int getTo() {
			return work.getInt(offset + OFF_TO);
		}
	}
}
