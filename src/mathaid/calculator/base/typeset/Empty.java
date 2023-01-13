/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.io.IOException;
import java.util.List;

/**
 * A placeholder {@code Segment} object
 */
public final class Empty implements LinkedSegment {
	public Empty() {
		this("");
	}
	
	public Empty(String placeHolder) {
		this("", false, false);
	}

	Empty(String placeHolderText, boolean focus, boolean error) {
		this.focus = focus;
		this.error = error;
		pht = placeHolderText;
	}

	@Override
	public int getType() {
		return Segment.Type.EMPTY;
	}

	@Override
	public boolean isFocused() {
		return focus;
	}

	@Override
	public boolean hasError() {
		return error;
	}

	@Override
	public int length() {
		return 0;
	}

	@Override
	public int getSuperIndex() {
		return -1;
	}

	@Override
	public int getSubIndex() {
		return -1;
	}

	@Override
	public LinkedSegment getSibling() {
		return null;
	}

	@Override
	public LinkedSegment[] getChildren() {
		return new LinkedSegment[] {};
	}
	
	@Override
	public LinkedSegment segmentAt(int index) throws IndexOutOfBoundsException {
		if(index != 0)
			throw new IndexOutOfBoundsException();
		return this;
	}

	@Override
	public LinkedSegment subsegment(int index) throws IndexOutOfBoundsException {
		return segmentAt(index);
	}

	@Override
	public LinkedSegment subsegment(int start, int end) throws IndexOutOfBoundsException {
		if(start > end)
			throw new IndexOutOfBoundsException("start > end");
		if(start == end && start == 0)
			return this;
		throw new IndexOutOfBoundsException("arguments out of bounds");
	}

	@Override
	public LinkedSegment setFocus(int index, boolean f) {
		if(index != 0)
			throw new IndexOutOfBoundsException("index out of bounds");
		return new Empty(pht, f, error);
	}

	@Override
	public LinkedSegment setError(int index, boolean e) {
		if(index != 0)
			throw new IndexOutOfBoundsException("index out of bounds");
		return new Empty(pht, focus, e);
	}

	@Override
	public LinkedSegment setSibling(int index, LinkedSegment sibling) throws IndexOutOfBoundsException {
		if (index != 0)
			throw new IndexOutOfBoundsException(index);
		/* This is the correct behaviour */
		return sibling == null ? this : sibling;
//		return Objects.requireNonNull(sibling, "Cannot insert a null segement");
	}

	@Override
	public LinkedSegment setChild(int index, LinkedSegment childToSet) throws IndexOutOfBoundsException {
		throw new IndexOutOfBoundsException("attempted to set a child segment in an empty Segment");
	}

	@Override
	public LinkedSegment concat(LinkedSegment s) {
		if(s == null)
			throw new NullPointerException();
		return s;
	}

	private final boolean focus;
	private final boolean error;
	private final String pht;
	
	//TODO: needs a body
	@Override
	public void format(Appendable a, Formatter f, List<Integer> p) throws IndexOutOfBoundsException {
		/* Update the current position of the cursor */
		p.set(p.size() - 1, p.get(p.size() - 1) + 1);

		try {
			a.append(f.format(this, pht, getType(), p));
		} catch (IOException e) {
		}

		if (hasSibling())
			getSibling().format(a, f, p);
	}

	//TODO: needs a body
	@Override
	public void toString(Appendable a, Log l, List<Integer> indexes) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		
	}
}