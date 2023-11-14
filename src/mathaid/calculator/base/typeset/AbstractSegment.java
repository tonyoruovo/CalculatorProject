/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import mathaid.calculator.base.util.Figurate;

public abstract class AbstractSegment implements LinkedSegment {

	protected AbstractSegment(int type, boolean focus, boolean error, LinkedSegment sibling,
			LinkedSegment[] children, int superIndex, int subIndex) {
		this.type = type;
		this.focus = focus;
		this.error = error;
		this.sibling = sibling;
		this.children = Arrays.copyOfRange(children, 0, children.length);
		this.spIndex = superIndex;
		this.sbIndex = subIndex;
	}
	@Override
	public int getType() {
		return type;
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
	public int getSuperIndex() {
		return spIndex;
	}
	@Override
	public int getSubIndex() {
		return sbIndex;
	}
	@Override
	public LinkedSegment getSibling() {
		return sibling;
	}
	@Override
	public LinkedSegment[] getChildren() {
		return children;
	}
	@Override
	public final LinkedSegment setChild(int childIndex, LinkedSegment child) throws IndexOutOfBoundsException {
		if(child == null)
			throw new NullPointerException();
		try {
			children[childIndex] = child; // TODO: not ideal, too mutative
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(childIndex);
		}
		return this;
	}

	@Override
	public abstract void format(Appendable a, Formatter f, List<Integer> position);

	@Override
	public abstract void toString(Appendable a, Log l, List<Integer> position);

	@Override
	public abstract LinkedSegment setFocus(int index, boolean focus) throws IndexOutOfBoundsException;

	@Override
	public abstract LinkedSegment setError(int index, boolean error) throws IndexOutOfBoundsException;

	@Override
	public abstract LinkedSegment setSibling(int index, LinkedSegment sibling) throws IndexOutOfBoundsException;

	@Override
	public abstract LinkedSegment concat(LinkedSegment s);

	@Override
	public int hashCode() {
		return Figurate.triangular(BigInteger.valueOf(getType())).intValue();
	}

	@Override
	public abstract boolean equals(Object obj);

	@Override
	public abstract String toString();

	private final LinkedSegment sibling;
	private final boolean error;
	private final boolean focus;
	private final int type;
	private final LinkedSegment[] children;
	/*
	 * If we increment this property, when accessing the index
	 * of this segment's children then we know we are going upwards
	 * in the traversal of the child index
	 */
	private final int sbIndex;
	/*
	 * If we decrement this property, when accessing the index
	 * of this segment's children then we know we are going downwards
	 * in the traversal of the child index
	 */
	private final int spIndex;
}