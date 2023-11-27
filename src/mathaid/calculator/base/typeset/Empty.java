/**
 * 
 */
package mathaid.calculator.base.typeset;

import java.io.IOException;
import java.util.List;

/**
 * A placeholder {@code LinkedSegment} used instead of {@code null} within the
 * {@code LinkedSegment} tree. It is equipped with a placeholder for use in
 * rendering (the meaning of which is defined within the context it is
 * rendered). They may have their focus and error set, however note the
 * documentation of each method as some cannot be called without throwing an
 * {@code Exception}.
 * <p>
 * An {@code Empty} segment node has no children, and when an attempt is made if
 * concatenate a node to it, it will be discarded, replaced by the new node
 * instead.
 * <p>
 * {@code LinkedSegment} is to this class as {@code String} is to empty string
 * {@code ""}.
 */
public final class Empty implements LinkedSegment {

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:20:49 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Empty} segment using the empty string {@code ""} as it's
	 * placeholder.
	 */
	public Empty() {
		this("");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:18:37 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Empty} segment by specifying it's placeholder. The role of
	 * the placeholder is to render an image for the viewer to see as a symbol of a
	 * {@code null}/invalid node.
	 * 
	 * @param placeHolder the value to be rendered instead
	 */
	public Empty(String placeHolder) {
		this(placeHolder, false, false);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:11:40 ---------------------------------------------------
	 */
	/**
	 * Creates an {@code Empty} segment. Used internally for implementing
	 * immutability.
	 * 
	 * @param placeHolderText the placeholder to display during render operations.
	 * @param focus           the focus to be set
	 * @param error           the error to be set
	 */
	Empty(String placeHolderText, boolean focus, boolean error) {
		this.focus = focus;
		this.error = error;
		pht = placeHolderText;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:21:32 ---------------------------------------------------
	 */
	/**
	 * Gets the value specified by {@code LinkedSegment.Type#EMPTY}.
	 * 
	 * @return {@code LinkedSegment.Type#EMPTY}
	 */
	@Override
	public int getType() {
		return Segment.Type.EMPTY;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:22:28 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean isFocused() {
		return focus;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:22:52 ---------------------------------------------------
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean hasError() {
		return error;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:23:11 ---------------------------------------------------
	 */
	/**
	 * Always returns {@code 0}.
	 * 
	 * @return {@code 0}.
	 */
	@Override
	public int length() {
		return 0;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:23:11 ---------------------------------------------------
	 */
	/**
	 * Always returns {@code -1}.
	 * 
	 * @return {@code -1}.
	 */
	@Override
	public int getSuperIndex() {
		return -1;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:23:11 ---------------------------------------------------
	 */
	/**
	 * Always returns {@code -1}.
	 * 
	 * @return {@code -1}.
	 */
	@Override
	public int getSubIndex() {
		return -1;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:23:11 ---------------------------------------------------
	 */
	/**
	 * Always returns {@code null}.
	 * 
	 * @return {@code null}.
	 */
	@Override
	public LinkedSegment getSibling() {
		return null;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:23:11 ---------------------------------------------------
	 */
	/**
	 * Always returns an array of length {@code 0}.
	 * 
	 * @return a {@code 0} length array.
	 */
	@Override
	public LinkedSegment[] getChildren() {
		return new LinkedSegment[] {};
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:27:40 ---------------------------------------------------
	 */
	/**
	 * Returns {@code this} if (and only if) the argument is equal to {@code 0}
	 * 
	 * @param index the index which must be {@code 0} or an exception will be
	 *              thrown.
	 * @return {@code this} if the argument is {@code 0} else throws an exception.
	 * @throws IndexOutOfBoundsException if the {@code index != 0}.
	 */
	@Override
	public LinkedSegment segmentAt(int index) throws IndexOutOfBoundsException {
		if (index != 0)
			throw new IndexOutOfBoundsException();
		return this;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:32:34 ---------------------------------------------------
	 */
	/**
	 * Returns {@code this} if the argument is {@code 0} else throws an
	 * {@code IndexOutOfBoundsException}.
	 * 
	 * @param index the index which must be {@code 0} or an exception will be
	 *              thrown.
	 * @return {@code this} if the argument is {@code 0} else throws an exception.
	 * @throws IndexOutOfBoundsException if the {@code index != 0}.
	 */
	@Override
	public LinkedSegment subsegment(int index) throws IndexOutOfBoundsException {
		return segmentAt(index);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:32:58 ---------------------------------------------------
	 */
	/**
	 * Returns {@code this} if {@code start == 0 && end == 0} else throws an
	 * {@code IndexOutOfBoundsException}.
	 * 
	 * @param start the beginning index which must be {@code 0} or an exception will
	 *              be thrown.
	 * @param end   the ending index which must be {@code 0} or an exception will be
	 *              thrown.
	 * @return {@code this} if {@code start == 0 && end == 0} else throws an
	 *         {@code IndexOutOfBoundsException}.
	 * @throws IndexOutOfBoundsException if {@code start != 0 || end != 0}.
	 */
	@Override
	public LinkedSegment subsegment(int start, int end) throws IndexOutOfBoundsException {
		if (start > end)
			throw new IndexOutOfBoundsException("start > end");
		if (start == end && start == 0)
			return this;
		throw new IndexOutOfBoundsException("arguments out of bounds");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:37:13 ---------------------------------------------------
	 */
	/**
	 * Sets the focus property of {@code this} to the {@code boolean} argument only
	 * if {@code index == 0}.
	 * 
	 * @param index the index which must be {@code 0} or an exception will be
	 *              thrown.
	 * @param f     the focus to be set.
	 * @return {@code this} after setting the focus if {@code index == 0} else
	 *         throws an {@code IndexOutOfBoundsException}.
	 * @throws IndexOutOfBoundsException if {@code index != 0}.
	 */
	@Override
	public LinkedSegment setFocus(int index, boolean f) {
		if (index != 0)
			throw new IndexOutOfBoundsException("index out of bounds");
		return new Empty(pht, f, error);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:37:13 ---------------------------------------------------
	 */
	/**
	 * Sets the error property of {@code this} to the {@code boolean} argument only
	 * if {@code index == 0}.
	 * 
	 * @param index the index which must be {@code 0} or an exception will be
	 *              thrown.
	 * @param e     the error to be set.
	 * @return {@code this} after setting the error if {@code index == 0} else
	 *         throws an {@code IndexOutOfBoundsException}.
	 * @throws IndexOutOfBoundsException if {@code index != 0}.
	 */
	@Override
	public LinkedSegment setError(int index, boolean e) {
		if (index != 0)
			throw new IndexOutOfBoundsException("index out of bounds");
		return new Empty(pht, focus, e);
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:37:13 ---------------------------------------------------
	 */
	/**
	 * Returns the {@code LinkedSegment} argument (or {@code this} if it is
	 * {@code null}) only if {@code index == 0}.
	 * 
	 * @param index   the index which must be {@code 0} or an exception will be
	 *                thrown.
	 * @param sibling the sibling to be set.
	 * @return the {@code LinkedSegment} argument (or {@code this} if it is
	 *         {@code null}) if {@code index == 0} else throws an
	 *         {@code IndexOutOfBoundsException}.
	 * @throws IndexOutOfBoundsException if {@code index != 0}.
	 */
	@Override
	public LinkedSegment setSibling(int index, LinkedSegment sibling) throws IndexOutOfBoundsException {
		if (index != 0)
			throw new IndexOutOfBoundsException(index);
		/* This is the correct behaviour */
		return sibling == null ? this : sibling;
//		return Objects.requireNonNull(sibling, "Cannot insert a null segment");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:45:49 ---------------------------------------------------
	 */
	/**
	 * Immediately throws an {@code IndexOutOfBoundsException} when invoked.
	 * 
	 * @param index      unused
	 * @param childToSet unused
	 * @return nothing, does not return.
	 * @throws IndexOutOfBoundsException when called.
	 */
	@Override
	public LinkedSegment setChild(int index, LinkedSegment childToSet) throws IndexOutOfBoundsException {
		throw new IndexOutOfBoundsException("attempted to set a child segment in an empty Segment");
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:49:44 ---------------------------------------------------
	 */
	/**
	 * Returns the argument if non-null else throw a {@code NullPointerException}.
	 * 
	 * @param s the non-null value to be returned.
	 * @return the non-null argument.
	 * @throws NullPointerException if the argument is <code>null</code>.
	 */
	@Override
	public LinkedSegment concat(LinkedSegment s) {
		if (s == null)
			throw new NullPointerException();
		return s;
	}

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:51:25 ---------------------------------------------------
	 */
	/**
	 * Gets the value that is rendered during
	 * {@link #format(Appendable, Formatter, List)}.
	 * 
	 * @return the placeholder text.
	 */
	public String getPlaceHolderText() {
		return pht;
	}

	/**
	 * The focus property.
	 */
	private final boolean focus;
	/**
	 * The error property
	 */
	private final boolean error;
	/**
	 * The <em>P</em>lace<em>H</em>older <em>T</em>ext.
	 */
	private final String pht;

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:52:09 ---------------------------------------------------
	 */
	/**
	 * Renders this {@code Empty} segment by displaying
	 * {@link #getPlaceHolderText()}.
	 * 
	 * @param a {@inheritDoc}
	 * @param f {@inheritDoc}
	 * @param p {@inheritDoc}
	 * @throws IndexOutOfBoundsException particularly if the given indexes argument
	 *                                   is not in the expected order,which may
	 *                                   cause the internal code to call
	 *                                   non-existing Segments.
	 */
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

	/*
	 * Date: 18 Nov 2023 -----------------------------------------------------------
	 * Time created: 06:55:37 ---------------------------------------------------
	 */
	/**
	 * Has no effect when called.
	 * 
	 * @param a       can be {@code null}
	 * @param l       can be {@code null}
	 * @param indexes can be {@code null}
	 */
	@Override
	public void toString(Appendable a, Log l, List<Integer> indexes) {
	}
}