/**
 * 
 */
package mathaid.calculator.base.typeset;

public interface LinkedSegment extends Segment {

	int getSuperIndex();
	int getSubIndex();
	LinkedSegment getSibling();
	LinkedSegment[] getChildren();
	LinkedSegment setFocus(int index, boolean f) throws IndexOutOfBoundsException;
	LinkedSegment setError(int index, boolean e) throws IndexOutOfBoundsException;
	LinkedSegment setSibling(int index, LinkedSegment sibling) throws IndexOutOfBoundsException;
	LinkedSegment setChild(int childIndex, LinkedSegment childToSet) throws IndexOutOfBoundsException;
	LinkedSegment concat(LinkedSegment s) throws NullPointerException;

	default boolean hasSibling() {
		return getSibling() != null;
	}

	default int length() {
		return 1 + getSibling().length();
	}

	default LinkedSegment segmentAt(int index) throws IndexOutOfBoundsException {
		return subsegment(index, index + 1).setSibling(0, null);
	}

	/*
	 * May return an empty segment if start == 
	 * end and both are within bounds
	 */
	default LinkedSegment subsegment(int from, int to) throws IndexOutOfBoundsException {
		if(from > to)
			throw new IndexOutOfBoundsException("from > to");
		int length = length();
		if(from == to && (from >= 0 && from < length))
			return new Empty();
		if(from == 0) {
			if(to == length)
				return this;
			return setSibling(to - from - 1, null);
		} else if(to == length)
			return subsegment(from);
		return subsegment(from).setSibling(to - from - 1, null);
	}

	default LinkedSegment subsegment(int fromIndex) throws IndexOutOfBoundsException {
		if (fromIndex == 0)
			return this;
		else if (fromIndex > 0 && hasSibling())
			return getSibling().subsegment(fromIndex - 1);
		throw new IndexOutOfBoundsException();
	}

	/*default LinkedSegment delete(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return new Empty();
		return segmentAt(index - 1).setSibling(null);
	}
	default LinkedSegment delete(int from, int to) throws IndexOutOfBoundsException {
		if(from > to)
			throw new IndexOutOfBoundsException("from > to");
		int length = length();
		if(from == to && (from >= 0 && from < length) && (to >= 0 && to < length))
			return this;
		if(from == 0) {
			if(to == length)
				return new Empty();
			return segmentAt(to);
		} else if(to == length)
			return subsegment(from - 1, from);
		return segmentAt(from - 1).setSibling(to);
	}*/
}