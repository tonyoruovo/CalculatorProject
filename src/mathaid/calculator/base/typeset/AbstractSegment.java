/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import mathaid.calculator.base.util.Figurate;

/*
 * Date: 17 Nov 2023 -----------------------------------------------------------
 * Time created: 03:37:32 ---------------------------------------------------
 * Package: mathaid.calculator.base.typeset ------------------------------------------------
 * Project: CalculatorProject ------------------------------------------------
 * File: AbstractSegment.java ------------------------------------------------------
 * Class name: AbstractSegment ------------------------------------------------
 */
/**
 * An abstract and generic implementation of the {@code LinkedSegment}
 * interface. It provides basic implementation for getters.
 * 
 * @author Oruovo Anthony Etineakpopha
 * @email tonyoruovo@gmail.com
 */
public abstract class AbstractSegment implements LinkedSegment {

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 03:53:12 ---------------------------------------------------
	 */
	/**
	 * Provides a constructor for directly setting the type, focus, error, sibling
	 * and children which facilitates immutability within implementors.
	 * 
	 * @param type       the type of this segment.
	 * @param focus      the focus of this segment.
	 * @param error      the error of this segment.
	 * @param sibling    the sibling to this segment.
	 * @param children   the child nodes of this segment.
	 * @param superIndex the index of the superscript child. This value is a valid
	 *                   index within child nodes. It may be {@code -1} if none of
	 *                   the children fit the description of a 'superscript', or if
	 *                   there is no children at all.
	 * @param subIndex   the index of the subscript child. This value is a valid
	 *                   index within child nodes. It may be {@code -1} if none of
	 *                   the children fit the description of a 'subscript', or if
	 *                   there is no children at all.
	 */
	protected AbstractSegment(int type, boolean focus, boolean error, LinkedSegment sibling, LinkedSegment[] children,
			int superIndex, int subIndex) {
		this.type = type;
		this.focus = focus;
		this.error = error;
		this.sibling = sibling;
		this.children = Arrays.copyOfRange(children, 0, children.length);
		this.spIndex = superIndex;
		this.sbIndex = subIndex;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:04:49 ---------------------------------------------------
	 */
	/**
	 * Gets the type of this segment.
	 * 
	 * @return {@inheritDoc}
	 * @see Segment#getType()
	 */
	@Override
	public int getType() {
		return type;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:06:00 ---------------------------------------------------
	 */
	/**
	 * Returns the the focus property of this segment.
	 * 
	 * @return {@inheritDoc}
	 * @see Segment#isFocused()
	 */
	@Override
	public boolean isFocused() {
		return focus;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:09:39 ---------------------------------------------------
	 */
	/**
	 * Returns the error property of this segment.
	 * 
	 * @return {@inheritDoc}
	 * @see Segment#hasError()
	 */
	@Override
	public boolean hasError() {
		return error;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:11:05 ---------------------------------------------------
	 */
	/**
	 * Gets the index of the superscript child of this segment. Note that the value
	 * returned (if non-negative) is a valid index in {@link #getChildren()}, if
	 * negative, then this segment does not have a superscript child.
	 * 
	 * @return {@inheritDoc}
	 * @see Segment#getSuperIndex()
	 */
	@Override
	public int getSuperIndex() {
		return spIndex;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:21:12 ---------------------------------------------------
	 */
	/**
	 * Gets the index of the subscript child of this segment. Note that the value
	 * returned (if non-negative) is a valid index in {@link #getChildren()}, if
	 * negative, then this segment does not have a subscript child.
	 * 
	 * @return {@inheritDoc}
	 * @see Segment#getSubIndex()
	 */
	@Override
	public int getSubIndex() {
		return sbIndex;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:26:25 ---------------------------------------------------
	 */
	/**
	 * Gets the sibling of this segment or {@code null} if it has no sibling.
	 * 
	 * @return {@inheritDoc}
	 * @see Segment#getSibling()
	 */
	@Override
	public LinkedSegment getSibling() {
		return sibling;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:29:25 ---------------------------------------------------
	 */
	/**
	 * Gets all the child nodes of this segment in an ordered sequence represented
	 * as an array.
	 * 
	 * @return {@inheritDoc}
	 * @see LinkedSegment#getChildren()
	 */
	@Override
	public LinkedSegment[] getChildren() {
		return children;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:35:23 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param childIndex {@inheritDoc}
	 * @param child      {@inheritDoc}
	 * @return {@code this} as the head of the tree whereby the argument
	 *         {@code child} is now the child at the index specified by
	 *         {@code childIndex}. In reality, this will be called recursively and
	 *         unless {@code this} is the head of the tree that initiated the call,
	 *         it may not be directly returned.
	 * @throws IndexOutOfBoundsException if {@code childIndex} is not a valid index
	 *                                   for the child nodes of this object.
	 */
	@Override
	public final LinkedSegment setChild(int childIndex, LinkedSegment child) throws IndexOutOfBoundsException {
		if (child == null)
			throw new NullPointerException();
		try {
			children[childIndex] = child; // TODO: not ideal, too mutative
		} catch (IndexOutOfBoundsException e) {
			throw new IndexOutOfBoundsException(childIndex);
		}
		return this;
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:37:48 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param a        {@inheritDoc}
	 * @param f        {@inheritDoc}
	 * @param position {@inheritDoc}
	 * @throws IndexOutOfBoundsException if the {@code List} argument is presented
	 *                                   in such a way that may cause the traversal
	 *                                   to reference a {@code null} node.
	 */
	@Override
	public abstract void format(Appendable a, Formatter f, List<Integer> position) throws IndexOutOfBoundsException;

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:41:01 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param a        {@inheritDoc}
	 * @param l        {@inheritDoc}
	 * @param position {@inheritDoc}
	 * @throws IndexOutOfBoundsException if the {@code List} argument is presented
	 *                                   in such a way that may cause the traversal
	 *                                   to reference a {@code null} node.
	 */
	@Override
	public abstract void toString(Appendable a, Log l, List<Integer> position) throws IndexOutOfBoundsException;

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:41:37 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param index {@inheritDoc}
	 * @param focus {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException if the {@code int} argument is presented in
	 *                                   such a way that may cause the traversal to
	 *                                   reference a {@code null} node.
	 */
	@Override
	public abstract LinkedSegment setFocus(int index, boolean focus) throws IndexOutOfBoundsException;

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:42:50 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param index {@inheritDoc}
	 * @param error {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException if the {@code int} argument is presented in
	 *                                   such a way that may cause the traversal to
	 *                                   reference a {@code null} node.
	 */
	@Override
	public abstract LinkedSegment setError(int index, boolean error) throws IndexOutOfBoundsException;

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:43:17 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param index   {@inheritDoc}
	 * @param sibling {@inheritDoc}
	 * @return {@inheritDoc}
	 * @throws IndexOutOfBoundsException if the {@code int} argument is presented in
	 *                                   such a way that may cause the traversal to
	 *                                   reference a {@code null} node.
	 */
	@Override
	public abstract LinkedSegment setSibling(int index, LinkedSegment sibling) throws IndexOutOfBoundsException;

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:44:05 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @param s {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public abstract LinkedSegment concat(LinkedSegment s);

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:44:28 ---------------------------------------------------
	 */
	/**
	 * Gets the hash code of this segment, which is calculated from it's
	 * {@linkplain #getType type}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Figurate.triangular(BigInteger.valueOf(getType())).intValue();
	}

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:45:30 ---------------------------------------------------
	 */
	/**
	 * Checks if this segment is equal to the argument and returns {@code true} if
	 * it is, or else returns {@code false}. This must be implemented as
	 * {@code AbstractSegment} objects are immutable, hence must have consistent
	 * equality checks.
	 * 
	 * @param obj {@inheritDoc}
	 * @return {@inheritDoc}
	 */
	@Override
	public abstract boolean equals(Object obj);

	/*
	 * Date: 17 Nov 2023 -----------------------------------------------------------
	 * Time created: 04:48:10 ---------------------------------------------------
	 */
	/**
	 * Returns a {@code String} representation of this segment node. Every segment
	 * must have it's own way of stringifying itself, however, it must return in
	 * constant time.
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public abstract String toString();

	/**
	 * The implementation sibling of this segment.
	 */
	private final LinkedSegment sibling;
	/**
	 * The implementation of the error property of this segment.
	 */
	private final boolean error;
	/**
	 * The implementation of the {@link #getFocus focus} property of this segment.
	 * Note that only one node can be true in the segment tree, and the focus
	 * property does not extends to it's child nodes.
	 */
	private final boolean focus;
	/**
	 * The implementation of the {@linkplain #getType() type} of this segment.
	 */
	private final int type;
	/**
	 * The implementation of the {@linkplain #getChildren() children} of this
	 * segment.
	 */
	private final LinkedSegment[] children;
	/**
	 * The implementation of the {@linkplain #getSubIndex() subscript} of this
	 * segment.
	 * <p>
	 * If we increment this property, when accessing the index of this segment's
	 * children then we know we are going upwards in the traversal of the child
	 * index
	 */
	private final int sbIndex;
	/**
	 * The implementation of the {@linkplain #getSuperIndex() superscript} of this
	 * segment.
	 * <p>
	 * If we decrement this property, when accessing the index of this segment's
	 * children then we know we are going downwards in the traversal of the child
	 * index
	 */
	private final int spIndex;
}